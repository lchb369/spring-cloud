package com.springcloud.lab.feedservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lchb on 2017-7-10.
 * AOP
 * 相关资料:
 * execution切入点表达式:http://blog.csdn.net/u012887385/article/details/54600706
 */
@Aspect
@Component
public class ControllerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(ControllerInterceptor.class);

    @Pointcut("execution(* com.springcloud.lab.feedservice.controller.*.*(..)) && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut(){}

    @Around("controllerMethodPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String method = proceedingJoinPoint.getSignature().toShortString();
        logger.info("doAround Request:[" + request.getMethod() + "]URL:[" + request.getRequestURL() + "]QueryString:[" + request.getQueryString() + "]Function:"+method );

        Object object=proceedingJoinPoint.proceed();


        long end = System.currentTimeMillis();
        logger.info("Cost time : " + (end - start) + "ms======"+request.getRequestURL() );
        return object;
    }
}

