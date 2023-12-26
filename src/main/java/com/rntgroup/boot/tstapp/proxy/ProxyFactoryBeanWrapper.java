package com.rntgroup.boot.tstapp.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class ProxyFactoryBeanWrapper implements ProxyWrapper<MethodInterceptor> {
	@Override
	public Object getProxy(Object baseObject, MethodInterceptor methodIterceptor) {
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTarget(baseObject);
		proxyFactoryBean.setProxyTargetClass(true);
		proxyFactoryBean.addAdvice(methodIterceptor);
		proxyFactoryBean.setSingleton(true);
		return proxyFactoryBean.getObject();
	}
}
