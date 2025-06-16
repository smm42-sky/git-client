package com.ssm.git_client.config;

import com.bskyb.common.extensions.config.ExtensionsConfig;
import com.bskyb.common.extensions.exceptions.ApplicationExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableCaching
@Configuration
@Import({ExtensionsConfig.class, ApplicationExceptionHandler.class})
@Slf4j
@ComponentScan(
        basePackages = {
            "com.ssm.*",
        })
public class ApplicationConfig {}
