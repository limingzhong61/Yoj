package com.yoj.custom.handle;

import com.yoj.custom.enums.ExceptionEnum;
import com.yoj.web.pojo.util.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Msg handle(Exception e) {
        e.printStackTrace();
        log.error("handle exception{}" + e.toString());
//        if (e instanceof GirlException) {
//            GirlException girlException = (GirlException) e;
//            return ResultUtil.error(girlException.getCode(), girlException.getMessage());
//        }else {
//            logger.error("【系统异常】{}", e);
//            return ResultUtil.error(-1, "未知错误");
//        }
        Msg msg = Msg.fail(ExceptionEnum.UNKNOWN_ERROR);
        return msg;
    }

}

