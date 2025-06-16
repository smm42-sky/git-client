package com.ssm.git_client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MergeRequestResponse extends GitResponse {

    private long id;
    private long iid;
    private Author author;

    @JsonProperty("target_branch")
    private String target;

    @JsonProperty("web_url")
    private String url;
}
