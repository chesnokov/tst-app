package com.rntgroup.boot.tstapp.service;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.function.Supplier;

@Component
public class TimestampSupplier implements Supplier<Timestamp> {
	@Override
	public Timestamp get() {
		return new Timestamp(System.currentTimeMillis());
	}
}
