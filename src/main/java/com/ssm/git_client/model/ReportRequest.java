package com.ssm.git_client.model;

import com.ssm.git_client.common.Component;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReportRequest {

    @Schema(description = "Component for which the report is generated")
    private Component component;

    @Schema(
            description = "Start Date for the report",
            format = "dd-mm-yyyy",
            example = "31-01-2025")
    private String fromDate;

    @Schema(
            description =
                    "End Date for the report, if not provided current date will be considered",
            format = "dd-mm-yyyy",
            example = "31-01-2025")
    private String toDate;

    @Schema(description = "Flag to indicate if the report is for develop branch (Final MR)")
    private boolean develop;
}
