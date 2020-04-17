package com.zuo.model.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

    @Around("execution(* com.zuo.model.controller.UserController.*(..))")
    public Object loginSession(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        Object[] args=thisJoinPoint.getArgs();//拿到方法的参数
        for(Object arg:args){
            System.out.println("arg is"+arg);
        }
        Object object=thisJoinPoint.proceed();
        return null;
    }
}
