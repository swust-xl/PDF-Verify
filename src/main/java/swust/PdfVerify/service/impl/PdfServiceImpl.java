package swust.PdfVerify.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;

import com.itextpdf.text.pdf.security.PdfPKCS7;

import sun.misc.BASE64Encoder;
import swust.PdfVerify.dao.PdfMapper;
import swust.PdfVerify.pojo.bo.OfflineVerifyResp;
import swust.PdfVerify.pojo.bo.SignVerifyResp;
import swust.PdfVerify.pojo.po.mysql.tables.pojos.Doc;
import swust.PdfVerify.service.PdfService;

@Service
public class PdfServiceImpl implements PdfService {

    // 文件本地路径前缀
    @Value("${pdf.local.path.prefix}")
    private String pdfLocalPathPrefix;
    // 文件web路径前缀
    @Value("${pdf.web.path.prefix}")
    private String pdfWebPathPrefix;
    // PDF文件后缀名
    private static final String PDF_FILE_SUFFIX = ".pdf";
    // 生成的二维码的宽和高
    private static final int QRCODE_WEIGHT = 200;
    private static final int QRCODE_HEIGHT = 200;

    private static final Random RANDOM = new Random();
    private static final BASE64Encoder BASE64ENCODER = new BASE64Encoder();
    private static final Logger LOG = LoggerFactory.getLogger(PdfServiceImpl.class);

    @Autowired
    private PdfMapper pdfMapper;

    // 提供算法库
    static {
        BouncyCastleProvider bcp = new BouncyCastleProvider();
        Security.insertProviderAt(bcp, 1);
        LOG.info("加载算法库");
    }

    /**
     * 
     * 把用户上传的pdf保存到本地并在数据库中记录
     *
     * @param bytes
     *            pdf文件的byte形式
     * @return pdf文件的id
     * @author xuLiang
     * @throws IOException
     *             IO异常
     * @since 1.0.0
     */
    @Transactional
    @Override
    public Long savePdfTemp(byte[] bytes) throws IOException {
        Long pdfId = Long.valueOf(RANDOM.nextInt(9) + Math.abs(bytes.hashCode()));
        String pdfWebPath = pdfWebPathPrefix + pdfId + PDF_FILE_SUFFIX;
        Path pdfLocalPath = Paths.get(pdfLocalPathPrefix + pdfId + PDF_FILE_SUFFIX);
        LOG.info("将ID为[{}]PDF文件保存到本地", pdfId);
        Files.write(pdfLocalPath, bytes);
        pdfMapper.insertDoc(new Doc(null, pdfWebPath, pdfId, pdfLocalPath.toString()));
        return pdfId;
    }

    /**
     * 
     * pdf校验
     *
     * @param bytes
     * @return 校验结果
     * @author xuLiang
     * @throws Exception
     *             IOException|GeneralSecurityException
     * @since 1.0.0
     */
    @Override
    public List<SignVerifyResp> checkPdfSignature(byte[] bytes) throws Exception {
        PdfReader reader = new PdfReader(bytes);
        AcroFields acroFields = reader.getAcroFields();
        List<String> signNames = acroFields.getSignatureNames();
        if (signNames.isEmpty()) {
            return Collections.emptyList();
        }
        List<SignVerifyResp> list = new ArrayList<>();
        for (String name : signNames) {
            SignVerifyResp resp = new SignVerifyResp();
            PdfPKCS7 sign = acroFields.verifySignature(name);
            resp.setSignatureName(name);
            resp.setIsSignatureCoversWholeDocument(acroFields.signatureCoversWholeDocument(name));
            resp.setSignDate(sign.getSignDate()
                    .getTime()
                    .getTime());
            resp.setCertificateUser(sign.getSigningCertificate()
                    .getSubjectDN()
                    .toString());
            resp.setCertificateIssuer(sign.getSigningCertificate()
                    .getIssuerDN()
                    .toString());
            resp.setCertificateStartDate(sign.getSigningCertificate()
                    .getNotBefore()
                    .getTime());
            resp.setCertificateCancelDate(sign.getSigningCertificate()
                    .getNotAfter()
                    .getTime());
            resp.setIsSignatureAvailable(sign.verify());
            try {
                sign.getSigningCertificate()
                        .checkValidity();
            } catch (CertificateExpiredException e) {
                resp.setIsCertificateExpired(true);
            } catch (CertificateNotYetValidException e) {
                resp.setIsCertificateExpired(false);
            }
            boolean hasTimeStampToken = sign.getTimeStampToken() == null ? false : true;
            resp.setTimeStampEmbedded(hasTimeStampToken);
            if (hasTimeStampToken) {
                resp.setTimestampDate(sign.getTimeStampToken()
                        .getTimeStampInfo()
                        .getGenTime()
                        .getTime());
                resp.setIsTimestampAvailable(sign.verifyTimestampImprint());
            } else {
                resp.setTimestampDate(null);
                resp.setIsTimestampAvailable(null);
            }
            list.add(resp);
        }
        return list;
    }

    /**
     * 
     * 生成pdf文件对应的二维码(com.google.zxing)
     *
     * @param pdfId
     *            pdf的id
     * @return 二维码图片的base64字符串
     * @author xuLiang
     * @since 1.0.0
     */
    @Override
    public String generateQrcode(String pdfId) {
        Assert.notNull(pdfId, "pdfId不能为空");
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 字符集
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 纠错级别
        hints.put(EncodeHintType.MARGIN, 2);// 空白
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(pdfId, BarcodeFormat.QR_CODE, QRCODE_WEIGHT,
                    QRCODE_HEIGHT, hints);
            return imageToBase64(MatrixToImageWriter.toBufferedImage(bitMatrix));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * 离线验证返回对应文档的验证结果和文档的url
     *
     * @param pdfId
     * @return 验证结果
     * @author xuLiang
     * @throws Exception
     *             IOException|GeneralSecurityException
     * @since 1.0.0
     */
    public OfflineVerifyResp offlineVerify(String pdfId) throws Exception {
        Assert.notNull(pdfId, "pdfId不能为空");
        Doc doc = pdfMapper.queryDoc(Long.valueOf(pdfId));
        Path path = Paths.get(doc.getDocLocalPath());
        OfflineVerifyResp resp = new OfflineVerifyResp();
        resp.setPdfUrl(doc.getDocWebPath());
        resp.setVerifyResult(checkPdfSignature(Files.readAllBytes(path)));
        return resp;

    }

    /**
     * 图片转Base64
     * 
     * @param image
     * @return Base64
     * @throws IOException
     *             IO异常
     * @author xuLiang
     * @since 1.0.0
     */
    private String imageToBase64(BufferedImage image) throws IOException {
        Assert.notNull(image, "待转换的图片不能为空");
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bao);
        byte[] imagedata = bao.toByteArray();
        String base64Image = BASE64ENCODER.encodeBuffer(imagedata)
                .trim()
                .replaceAll("\n", "")
                .replaceAll("\r", "");
        return base64Image;
    }

}
