package com.rntgroup.boot.tstapp.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix="test")
public class ExternalTestRepositoryConfig extends UserTestSuffixConfig {
	private String externalDir;
}
