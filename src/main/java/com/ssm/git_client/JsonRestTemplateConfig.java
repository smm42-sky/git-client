package com.ssm.git_client;

import com.bskyb.common.extensions.clients.http.RestTemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class JsonRestTemplateConfig extends RestTemplateConfig {

    @Autowired
    @Qualifier("jsonHttpMessageConverter")
    private HttpMessageConverter<?> jsonHttpMessageConverter;

    @Override
    protected RestTemplateBuilder getRestTemplateBuilder() {
        return new RestTemplateBuilder()
                .messageConverters(jsonHttpMessageConverter)
                .additionalMessageConverters(new FormHttpMessageConverter());
    }
}
