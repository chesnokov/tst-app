package com.rntgroup.boot.tstapp.proxy;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.text.MessageFormat;

@Slf4j
public class PerformanceMonitorProxyFactoryBeanMethodInterceptor implements MethodInterceptor {

	private final Object baseObject;

	public PerformanceMonitorProxyFactoryBeanMethodInterceptor(Object baseObject) {
		this.baseObject=baseObject;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
			return invocation.getMethod().invoke(baseObject, invocation.getArguments());
		} finally {
			long workTime = System.currentTimeMillis() - startTime;
			log.info(MessageFormat.format("{0}.{1} worked {2} ms",
					baseObject.getClass().getName(), invocation.getMethod().getName(), workTime));
		}
	}
}
