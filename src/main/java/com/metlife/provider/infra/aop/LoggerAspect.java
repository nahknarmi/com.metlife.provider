package com.metlife.provider.infra.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
@Component
public class LoggerAspect {
    private Logger logger;

    public LoggerAspect() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {
    }

    @Pointcut("@annotation(com.metlife.provider.infra.aop.Profiled)")
    public void profiled() {
    }

    @Around("requestMapping() || profiled()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        String name = className + "." + methodName;
        logger.info("call " + name);
        try {
            sw.start();
            return pjp.proceed();
        } finally {
            sw.stop();
            logger.info("exit " + name + " [" + sw.getTotalTimeMillis() + "ms]");
        }
    }
}
