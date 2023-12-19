package com.rntgroup.boot.tstapp.proxy;

import org.springframework.stereotype.Service;

@Service
public interface ProxyWrapper<T> {
	Object getProxy(Object baseObject, T methodIterceptor);
}
