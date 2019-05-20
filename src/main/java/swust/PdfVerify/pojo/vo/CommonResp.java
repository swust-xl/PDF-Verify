package swust.PdfVerify.pojo.vo;

import swust.PdfVerify.util.enums.UserCode;

/**
 * 
 * 统一响应体
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class CommonResp {

    protected Integer code;
    protected String message;

    public CommonResp() {
    }

    public CommonResp(UserCode userCode) {
        this.code = userCode.getCode();
        this.message = userCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
