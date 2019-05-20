package swust.PdfVerify.pojo.bo;

/**
 * 
 * 签名验证响应
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class SignVerifyResp {

    // 签名字段名
    private String signatureName;
    // 签名是否覆盖整个文档
    private Boolean isSignatureCoversWholeDocument;
    // 签名日期
    private Long signDate;
    // 证书使用者
    private String certificateUser;
    // 证书发行者
    private String certificateIssuer;
    // 证书起始期
    private Long certificateStartDate;
    // 证书是否过期
    private Boolean isCertificateExpired;
    // 证书结束期
    private Long certificateCancelDate;
    // 签名验证有效性
    private Boolean isSignatureAvailable;
    // 是否嵌入时间戳
    private Boolean timeStampEmbedded;
    // 时间戳时间
    private Long timestampDate;
    // 时间戳验证是否有效
    private Boolean isTimestampAvailable;

    public Boolean getTimeStampEmbedded() {
        return timeStampEmbedded;
    }

    public void setTimeStampEmbedded(Boolean timeStampEmbedded) {
        this.timeStampEmbedded = timeStampEmbedded;
    }

    public Long getTimestampDate() {
        return timestampDate;
    }

    public void setTimestampDate(Long timestampDate) {
        this.timestampDate = timestampDate;
    }

    public Boolean getIsTimestampAvailable() {
        return isTimestampAvailable;
    }

    public void setIsTimestampAvailable(Boolean isTimestampAvailable) {
        this.isTimestampAvailable = isTimestampAvailable;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public Boolean getIsSignatureCoversWholeDocument() {
        return isSignatureCoversWholeDocument;
    }

    public void setIsSignatureCoversWholeDocument(Boolean isSignatureCoversWholeDocument) {
        this.isSignatureCoversWholeDocument = isSignatureCoversWholeDocument;
    }

    public Long getSignDate() {
        return signDate;
    }

    public void setSignDate(Long signDate) {
        this.signDate = signDate;
    }

    public String getCertificateUser() {
        return certificateUser;
    }

    public void setCertificateUser(String certificateUser) {
        this.certificateUser = certificateUser;
    }

    public String getCertificateIssuer() {
        return certificateIssuer;
    }

    public void setCertificateIssuer(String certificateIssuer) {
        this.certificateIssuer = certificateIssuer;
    }

    public Long getCertificateStartDate() {
        return certificateStartDate;
    }

    public void setCertificateStartDate(Long certificateStartDate) {
        this.certificateStartDate = certificateStartDate;
    }

    public Long getCertificateCancelDate() {
        return certificateCancelDate;
    }

    public void setCertificateCancelDate(Long certificateCancelDate) {
        this.certificateCancelDate = certificateCancelDate;
    }

    public Boolean getIsSignatureAvailable() {
        return isSignatureAvailable;
    }

    public void setIsSignatureAvailable(Boolean isSignatureAvailable) {
        this.isSignatureAvailable = isSignatureAvailable;
    }

    public Boolean getIsCertificateExpired() {
        return isCertificateExpired;
    }

    public void setIsCertificateExpired(Boolean isCertificateExpired) {
        this.isCertificateExpired = isCertificateExpired;
    }

}
