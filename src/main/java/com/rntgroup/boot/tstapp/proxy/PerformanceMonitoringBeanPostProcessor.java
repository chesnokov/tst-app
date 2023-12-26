package com.rntgroup.boot.tstapp.proxy;

import com.rntgroup.boot.tstapp.annotation.BPPBenchmark;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
@Slf4j
public class PerformanceMonitoringBeanPostProcessor implements BeanPostProcessor {

	private final CglibCopyConstructorProxyWrapper copyConstructorProxyWrapper;
	private final CglibDefaultConstructorProxyWrapper defaultConstructorProxyWrapper;
	private final ProxyFactoryBeanWrapper proxyFactoryBeanWrapper;

	@Lazy
	public PerformanceMonitoringBeanPostProcessor(CglibCopyConstructorProxyWrapper copyConstructorProxyWrapper,
			  CglibDefaultConstructorProxyWrapper defaultConstructorProxyWrapper,
			  ProxyFactoryBeanWrapper proxyFactoryBeanWrapper) {
		this.copyConstructorProxyWrapper = copyConstructorProxyWrapper;
		this.defaultConstructorProxyWrapper = defaultConstructorProxyWrapper;
		this.proxyFactoryBeanWrapper = proxyFactoryBeanWrapper;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		Annotation annotation = bean.getClass().getAnnotation(BPPBenchmark.class);
		if(annotation != null) {
			Object proxy = copyConstructorProxyWrapper.getProxy(bean,
					new PerformanceMonitorCglibMethodInterceptor());
			proxy = proxy != bean ? proxy : defaultConstructorProxyWrapper.getProxy(bean,
					new PerformanceMonitorCglibMethodInterceptor(bean));
			return proxy != bean ? proxy : proxyFactoryBeanWrapper.getProxy(bean,
					new PerformanceMonitorProxyFactoryBeanMethodInterceptor(bean));
		}
		return bean;
	}
}
