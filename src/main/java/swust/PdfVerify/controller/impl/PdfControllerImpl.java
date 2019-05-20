package swust.PdfVerify.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import swust.PdfVerify.config.RestJsonPath;
import swust.PdfVerify.config.RestParam;
import swust.PdfVerify.controller.PdfController;
import swust.PdfVerify.pojo.bo.SignVerifyResp;
import swust.PdfVerify.pojo.vo.CommonResp;
import swust.PdfVerify.pojo.vo.QrcodeResp;
import swust.PdfVerify.pojo.vo.QueryPdfResp;
import swust.PdfVerify.pojo.vo.SignVerifyResultResp;
import swust.PdfVerify.service.PdfService;
import swust.PdfVerify.util.enums.UserCode;

/**
 * 
 * pdf相关操作控制器实现类
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@RestController
public class PdfControllerImpl implements PdfController {

    @Autowired
    private PdfService pdfService;

    /**
     * 
     * 上传一个PDF文件
     *
     * @param file
     *            PDF文件
     * @return 统一响应
     * @throws Exception
     *             IOException most probably??
     * @author xuLiang
     * @since 1.0.0
     */
    @PostMapping(RestJsonPath.PDFS)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public CommonResp uploadPdf(@RequestPart MultipartFile file) throws Exception {
        Assert.notNull(file, "上传的文件不能为空");
        if (!file.getOriginalFilename()
                .endsWith("pdf")) {
            throw new RuntimeException("文件必须是PDF格式");
        }
        SignVerifyResultResp resp = new SignVerifyResultResp(UserCode.SUCCESS);
        List<SignVerifyResp> list = pdfService.checkPdfSignature(file.getBytes());
        resp.setData(list);
        // 没有签名的文档不进行保存
        if (list.isEmpty()) {
            resp.setPdfId(null);
        } else {
            resp.setPdfId(pdfService.savePdfTemp(file.getBytes()));
        }
        return resp;
    }

    /**
     * 
     * 根据pdfId生成二维码
     *
     * @param pdfId
     *            PDF文件的id
     * @return 生成的二维码base64字符串
     * @author xuLiang
     * @since 1.0.0
     */
    @GetMapping(RestJsonPath.QRCODE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public CommonResp getQrcode(@RequestParam(RestParam.QV_PDF_ID) String pdfId) {
        Assert.notNull(pdfId, "pdfId不能为空");
        QrcodeResp resp = new QrcodeResp(UserCode.SUCCESS);
        resp.setQrcodeBase64(pdfService.generateQrcode(pdfId));
        return resp;
    }

    /**
     * 
     * pdf离线验签,根据PDF文件id查找到指定文件进行验签并返回验签结果和PDF文件的url
     *
     * @param pdfId
     * @return 统一响应
     * @throws Exception
     *             异常
     * @author xuLiang
     * @since 1.0.0
     */
    @GetMapping(RestJsonPath.PDF)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Override
    public CommonResp queryPdf(@PathVariable(RestParam.PV_PDF_ID) String pdfId) throws Exception {
        Assert.notNull(pdfId, "pdfId不能为空");
        QueryPdfResp resp = new QueryPdfResp(UserCode.SUCCESS);
        resp.setData(pdfService.offlineVerify(pdfId));
        return resp;
    }

}
