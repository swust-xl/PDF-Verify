package swust.PdfVerify.controller.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.zxing.WriterException;

import swust.PdfVerify.pojo.vo.ErrorResp;

/**
 * 
 * 全局异常处理
 * </p>
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ RuntimeException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResp userExceptionHandler(Exception e, HttpServletRequest request) {
        ErrorResp resp = new ErrorResp();
        resp.setTimestamp(System.currentTimeMillis());
        resp.setStatus(HttpStatus.BAD_REQUEST.value());
        resp.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        resp.setMessage(e.getMessage());
        resp.setPath(request.getRequestURI());
        return resp;
    }

    @ExceptionHandler({ IOException.class, GeneralSecurityException.class, WriterException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResp exceptionHandler(Exception e, HttpServletRequest request) {
        ErrorResp resp = new ErrorResp();
        resp.setTimestamp(System.currentTimeMillis());
        resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        resp.setMessage(e.getMessage());
        resp.setPath(request.getRequestURI());
        return resp;
    }

}
