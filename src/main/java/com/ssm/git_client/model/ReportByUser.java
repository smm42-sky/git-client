package com.ssm.git_client.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReportByUser extends ReportRequest {

    @Schema(description = "Author for which the report is generated")
    private String author;
}
