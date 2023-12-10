package com.rntgroup.boot.tstapp.aop;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.MessageFormat;

@Component
@Slf4j
public class CglibPerformanceAspect implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Annotation annotation = bean.getClass().getAnnotation(BPPBenchmark.class);
		if(annotation != null) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(bean.getClass());
			enhancer.setCallback((MethodInterceptor)(obj, method, args, proxy) ->{
				long startTime = System.currentTimeMillis();
				try {
					Method originalMethod = bean.getClass().getMethod(method.getName(),
							method.getParameterTypes());
					return originalMethod.invoke(bean, args);
				} finally {
					long workTime = System.currentTimeMillis() - startTime;
					log.info(MessageFormat.format("{0}.{1} worked {2} ms",
							bean.getClass().getName(), method.getName(), workTime));
				}
			});
			return enhancer.create();
		}
		return bean;
	}
}
