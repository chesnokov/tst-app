package com.rntgroup.boot.tstapp.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;

@Service
@Slf4j
public class CglibCopyConstructorProxyWrapper implements ProxyWrapper<MethodInterceptor> {

	@Override
	public Object getProxy(Object baseObject, MethodInterceptor methodIterceptor) {

		// we use copy constructor to create proxy with the same properties as baseObject
		Constructor<?> copyConstructor = getCopyConstructor(baseObject);

		if(copyConstructor != null) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(baseObject.getClass());
			enhancer.setCallback(methodIterceptor);
			return enhancer.create(new Class[] { baseObject.getClass() },
					new Object [] {baseObject });
		}
		return baseObject;
	}

	private Constructor<?> getCopyConstructor(Object baseObject) {
		Constructor<?> constructor = null;
		try {
			constructor = baseObject.getClass().getConstructor(baseObject.getClass());
		} catch (NoSuchMethodException e) {
			log.warn(MessageFormat.format("error getting copy constructor from class {0}",
					baseObject.getClass().getName()));
		}
		return constructor;
	}
}
