package swust.PdfVerify.util.enums;

/**
 * 
 * 用户操作响应枚举
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public enum UserCode {

    SUCCESS(1, "操作成功"), FAILURE(0, "操作失败"), ERROR(-1, "系统异常");

    private Integer code;
    private String message;

    private UserCode(Integer code, String message) {
        this.code = code;
        this.message = message;
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
