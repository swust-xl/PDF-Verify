package swust.PdfVerify.pojo.vo;

import java.util.List;

import swust.PdfVerify.pojo.bo.SignVerifyResp;
import swust.PdfVerify.util.enums.UserCode;

/**
 * 
 * 验签结果响应
 * </p>
 *
 * @author xuLiang
 * @since 1.0.0
 */
public class SignVerifyResultResp extends CommonResp {

    private Long pdfId;
    private List<SignVerifyResp> data;

    public SignVerifyResultResp() {
    }

    public SignVerifyResultResp(UserCode userCode) {
        super(userCode);
    }

    public List<SignVerifyResp> getData() {
        return data;
    }

    public void setData(List<SignVerifyResp> data) {
        this.data = data;
    }

    public Long getPdfId() {
        return pdfId;
    }

    public void setPdfId(Long pdfId) {
        this.pdfId = pdfId;
    }

}
