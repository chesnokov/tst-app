package com.rntgroup.boot.tstapp.application;

import com.rntgroup.boot.tstapp.repository.config.ExternalTestRepositoryConfig;
import com.rntgroup.boot.tstapp.repository.config.InternalTestRepositoryConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Set;

@Configuration
@EnableConfigurationProperties({ExternalTestRepositoryConfig.class, InternalTestRepositoryConfig.class})
public class ApplicationConfiguration {
	@Bean
	public ConversionService conversionService(Set<Converter<?,?>> converters) {
		ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
		factory.setConverters(converters);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript("schema.sql")
				.addScripts("data.sql")
				.setName("execises")
				.build();
	}
}
