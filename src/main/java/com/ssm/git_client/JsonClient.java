package com.ssm.git_client;

import com.bskyb.common.extensions.clients.http.Client;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class JsonClient extends Client {
    @Autowired
    private JsonRestTemplateConfig restTemplateConfig;
}
