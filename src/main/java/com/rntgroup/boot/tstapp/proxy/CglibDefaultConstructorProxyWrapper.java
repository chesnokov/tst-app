package com.rntgroup.boot.tstapp.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;

@Service
@Slf4j
public class CglibDefaultConstructorProxyWrapper implements ProxyWrapper<MethodInterceptor> {
	@Override
	public Object getProxy(Object baseObject, MethodInterceptor methodIterceptor) {
		Constructor<?> defaultConstructor = getDefaultConstructor(baseObject);

		if(defaultConstructor != null) {
			return getProxyObjectWithDefaultConstructor(baseObject, methodIterceptor);
		} else {
			return baseObject;
		}
	}

	private Object getProxyObjectWithDefaultConstructor(Object baseObject, MethodInterceptor methodIterceptor) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(baseObject.getClass());
		enhancer.setCallback(methodIterceptor);
		return enhancer.create();
	}

	private Constructor<?> getDefaultConstructor(Object baseObject) {
		Constructor<?> constructor = null;
		try {
			constructor = baseObject.getClass().getConstructor();
		} catch (NoSuchMethodException e) {
			log.warn(MessageFormat.format("error getting default constructor from class {0}",
					baseObject.getClass().getName()));
		}
		return constructor;
	}
}
