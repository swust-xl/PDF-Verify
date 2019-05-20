package swust.PdfVerify.pojo.vo;

import swust.PdfVerify.util.enums.UserCode;

/**
 * 
 * 获取二维码响应体
 * </p>
 *
 * @see swust.PdfVerify.pojo.vo.CommonResp
 * @author xuLiang
 * @since 1.0.0
 */
public class QrcodeResp extends CommonResp {

    private String qrcodeBase64;

    public QrcodeResp() {

    }

    public QrcodeResp(UserCode userCode) {
        super(userCode);
    }

    public String getQrcodeBase64() {
        return qrcodeBase64;
    }

    public void setQrcodeBase64(String qrcodeBase64) {
        this.qrcodeBase64 = qrcodeBase64;
    }
}
