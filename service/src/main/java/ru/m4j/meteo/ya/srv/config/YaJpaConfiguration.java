/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.srv.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ru.m4j.meteo.**.repo")
@EntityScan(basePackages = { "ru.m4j.meteo.**.domain" })
@EnableJpaAuditing /*(auditorAwareRef = "springSecurityAuditorAware")*/
//@EnableTransactionManagement
public class YaJpaConfiguration {
}
