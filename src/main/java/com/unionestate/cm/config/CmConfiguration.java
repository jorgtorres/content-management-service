package com.unionestate.cm.config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = { "com.unionestate.common.rds.repository", "com.unionestate.cm.repository" })
@ComponentScan(basePackages = "com.unionestate")
public class CmConfiguration {

}
