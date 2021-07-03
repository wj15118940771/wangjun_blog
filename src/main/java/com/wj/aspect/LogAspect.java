package com.wj.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.wj.controller.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinpoint){
        logger.info("----------doBefore---------");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestLog requestLog = new RequestLog();
        requestLog.setUrl(request.getRequestURL().toString());
        requestLog.setIp(request.getRemoteAddr());
        requestLog.setClassMethod(joinpoint.getSignature().getDeclaringTypeName()+"."+joinpoint.getSignature().getName());
        requestLog.setArgs(joinpoint.getArgs());

        logger.info("Request : {}",requestLog);
    }

    @After("log()")
    public void doAfter(){
        logger.info("----------doAfter---------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}",result);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
