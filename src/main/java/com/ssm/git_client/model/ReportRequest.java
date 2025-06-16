package com.ssm.git_client.model;

import com.ssm.git_client.common.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReportRequest {

    private Component component;

    private String fromDate;

    private String toDate;

    private boolean develop;

    private String author;
}
