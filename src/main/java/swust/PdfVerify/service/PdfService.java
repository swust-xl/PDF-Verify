package swust.PdfVerify.service;

import java.io.IOException;
import java.util.List;

import swust.PdfVerify.pojo.bo.OfflineVerifyResp;
import swust.PdfVerify.pojo.bo.SignVerifyResp;

public interface PdfService {
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
    List<SignVerifyResp> checkPdfSignature(byte[] bytes) throws Exception;

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
    Long savePdfTemp(byte[] bytes) throws IOException;

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
    String generateQrcode(String pdfId);

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
    OfflineVerifyResp offlineVerify(String PdfId) throws Exception;

}
