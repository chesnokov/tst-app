package com.rntgroup.boot.tstapp.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.text.MessageFormat;

@Slf4j
public class PerformanceMonitorCglibMethodInterceptor implements MethodInterceptor {

	private final Object baseObject;

	public PerformanceMonitorCglibMethodInterceptor() {
		this.baseObject=null;
	}

	public PerformanceMonitorCglibMethodInterceptor(Object baseObject) {
		this.baseObject = baseObject;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		long startTime = System.currentTimeMillis();
		try {
			if(baseObject == null) {
				return proxy.invokeSuper(obj, args);
			} else {
				Method originalMethod = baseObject.getClass().getMethod(method.getName(),
					method.getParameterTypes());
				return originalMethod.invoke(baseObject, args);
			}
		} finally {
			long workTime = System.currentTimeMillis() - startTime;
			log.info(MessageFormat.format("{0}.{1} worked {2} ms",
					obj.getClass().getSuperclass().getName(), method.getName(), workTime));
		}
	}
}
