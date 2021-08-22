package com.kdwu.lightninggo.exception;

import com.kdwu.lightninggo.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public CommonResult exception(RuntimeException re){
        re.printStackTrace();
        log.error("系統運行時發生異常--->()", re.getMessage());
        return CommonResult.failed(re.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public CommonResult exception(AccessDeniedException ade){
        log.error("權限不足--->()", ade.getMessage());
        return CommonResult.forbidden("權限不足，請通知管理員！");
    }
}
