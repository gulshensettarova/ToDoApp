package com.company.toDoApp.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggingAspect.class);
    @Pointcut("execution(* com.company.toDoApp.service.v1.impl.task.TaskService.*(..))")
    public void taskServiceMethods() {
    }

    @Before("taskServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Başladı: " + joinPoint.getSignature().getName());
    }
    @After("taskServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Bitdi: " + joinPoint.getSignature().getName());
    }

//      @AfterThrowing(pointcut = "taskServiceMethods()", throwing = "exception")
//    public void logException(JoinPoint joinPoint, Throwable exception) {
//        logger.error("Xəta metodu: " + joinPoint.getSignature().getName(), exception);
//    }


}
