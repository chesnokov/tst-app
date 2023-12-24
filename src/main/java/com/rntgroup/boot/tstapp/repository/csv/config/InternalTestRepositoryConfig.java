package com.rntgroup.boot.tstapp.repository.csv.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix="test")
public class InternalTestRepositoryConfig extends UserTestSuffixConfig {
	private String internalDir;
}
