package com.rntgroup.boot.tstapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@Aspect
@Slf4j
public class PerformanceLoggingAspect {

	@Pointcut("@annotation(com.rntgroup.boot.tstapp.annotation.AspectJBenchmark)")
	public void perfMonPointcut() {}

	@Around("perfMonPointcut()")
	public Object logMethodCallsAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
			return joinPoint.proceed();
		} finally {
			long workTime = System.currentTimeMillis() - startTime;
			log.info(MessageFormat.format("{0}.{1} worked {2} ms",joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), workTime));
		}
	}
}
