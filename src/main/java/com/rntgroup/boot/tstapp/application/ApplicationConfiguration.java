package com.rntgroup.boot.tstapp.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Configuration
public class ApplicationConfiguration {
	@Bean
	public ConversionService conversionService(Set<Converter> converters) {
		ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
		factory.setConverters(converters);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public CommandLineRunner commandLineRunner(Application application) {
		return args -> {
			application.run();
		};
	}
}
