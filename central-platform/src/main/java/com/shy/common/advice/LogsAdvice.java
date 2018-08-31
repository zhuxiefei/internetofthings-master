package com.shy.common.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 切面记录日志
 * @author 过家伟
 * 2018/8/29
 */
@Component
@Aspect
public class LogsAdvice {

    //设置切点
    @Pointcut("execution(* com.shy.iot..*.*(..))")
    public void executeMethond(){}

    //使用环绕通知记录日志
    @Around(value = "executeMethond()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        //获取目标类
        Object target = proceedingJoinPoint.getTarget();
        //将要执行的方法参数
        Object[] args = proceedingJoinPoint.getArgs();
        //将要执行的方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        //日志对象
        Logger LOG = LoggerFactory.getLogger(target.getClass());

        try{
            LOG.info("---------"+target.getClass()+"/"+methodName+"---------start-parameter:"+Arrays.toString(args));
            Object result = proceedingJoinPoint.proceed();
            LOG.info("---------"+target.getClass()+"/"+methodName+"---------end-result:"+result);
            return result;
        }catch (Throwable e){
            LOG.info("---------"+target.getClass()+"/"+methodName+"---------exception-:"+e.getMessage());
        }
        return null;
    }

}
