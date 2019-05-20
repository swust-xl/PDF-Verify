package swust.PdfVerify.pojo.vo;

import swust.PdfVerify.pojo.bo.OfflineVerifyResp;
import swust.PdfVerify.util.enums.UserCode;

/**
 * 
 * 查询PDF响应
 * </p>
 *
 * @see swust.PdfVerify.pojo.vo.CommonResp
 * @author xuLiang
 * @since 1.0.0
 */
public class QueryPdfResp extends CommonResp {

    private OfflineVerifyResp data;

    public QueryPdfResp() {
    }

    public QueryPdfResp(UserCode userCode) {
        super(userCode);
    }

    public OfflineVerifyResp getData() {
        return data;
    }

    public void setData(OfflineVerifyResp data) {
        this.data = data;
    }

}
