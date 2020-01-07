package com.yoj.custom.handle;

import com.yoj.custom.enums.ExceptionEnum;
import com.yoj.web.pojo.util.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Msg handle(Exception e) {
        e.printStackTrace();
        log.error("handle exception{}" + e.toString());
        // catch access denied exception
        if(e instanceof AccessDeniedException){
            return Msg.fail(ExceptionEnum.NOT_ACCESS);
        }else if(e instanceof HttpRequestMethodNotSupportedException){
            return Msg.fail(ExceptionEnum.HttpRequestMethodNotSupportedException);
        }else if(e instanceof MethodArgumentNotValidException){
            return Msg.fail(ExceptionEnum.MethodArgumentTypeMismatchException);
        }
        log.error("【系统异常】{}", e);
        Msg msg = Msg.fail(ExceptionEnum.UNKNOWN_ERROR);
        return msg;
    }

}

