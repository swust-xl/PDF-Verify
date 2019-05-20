package swust.PdfVerify.pojo.bo;

import java.util.List;

/**
 * 
 * 离线验证响应
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class OfflineVerifyResp {

    private String PdfUrl;
    private List<SignVerifyResp> verifyResult;

    public String getPdfUrl() {
        return PdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        PdfUrl = pdfUrl;
    }

    public List<SignVerifyResp> getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(List<SignVerifyResp> verifyResult) {
        this.verifyResult = verifyResult;
    }

}
