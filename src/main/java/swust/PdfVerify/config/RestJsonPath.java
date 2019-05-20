package swust.PdfVerify.config;

/**
 * 
 * REST风格请求路径
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class RestJsonPath {

    public static final String PDFS = "/pdfs";

    public static final String QRCODE = "/qrcode";

    public static final String PDF = PDFS + "/{" + RestParam.PV_PDF_ID + "}";

}
