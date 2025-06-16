package com.ssm.git_client.config;

import com.bskyb.common.extensions.security.CommonWebSecurityConfigurerAdapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends CommonWebSecurityConfigurerAdapter {

    @Override
    protected void extendConfig(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").authenticated().and().httpBasic();
    }
}
