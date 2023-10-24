package com.rntgroup.boot.tstapp.repository.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix="test")
public class ExternalTestRepositoryConfig extends UserTestSuffixConfig {
	private String externalDir;
}
