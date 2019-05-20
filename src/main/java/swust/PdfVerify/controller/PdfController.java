package swust.PdfVerify.controller;

import org.springframework.web.multipart.MultipartFile;

import swust.PdfVerify.pojo.vo.CommonResp;

/**
 * 
 * pdf相关操作控制器接口
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public interface PdfController {
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
    CommonResp uploadPdf(MultipartFile file) throws Exception;

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
    CommonResp getQrcode(String pdfId);

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
    CommonResp queryPdf(String pdfId) throws Exception;
}
