package com.rntgroup.boot.tstapp.repository.csv;

import com.rntgroup.boot.tstapp.repository.csv.config.ExternalTestRepositoryConfig;
import com.rntgroup.boot.tstapp.repository.csv.config.InternalTestRepositoryConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ExternalTestRepositoryConfig.class, InternalTestRepositoryConfig.class})
public class CsvRepositoryConfiguration {
}
