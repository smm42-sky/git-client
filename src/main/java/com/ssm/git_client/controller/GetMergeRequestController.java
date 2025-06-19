package com.ssm.git_client.controller;

import com.ssm.git_client.model.ReportByUser;
import com.ssm.git_client.model.ReportRequest;
import com.ssm.git_client.service.GitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/merge-requests")
@RequiredArgsConstructor
public class GetMergeRequestController {

    private final GitService gitService;

    @GetMapping("/report")
    @Operation(
            summary = "Get MR report for a component",
            description = "Fetches a report of authors and their contributions",
            responses = {
                @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid request")
            })
    public Map<String, Long> getAuthorReport(@RequestBody ReportRequest request) {
        return gitService.getAuthorReport(request);
    }

    @GetMapping("/getAllReport")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Get MR report for all components",
            description = "Fetches all reports based on the provided date",
            responses = {
                @ApiResponse(responseCode = "201", description = "Reports fetched successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid date format")
            })
    public void getAllReport(
            @RequestParam("fromDate") @Schema(format = "dd-mm-yyyy", example = "31-01-2025")
                    String fromDate) {
        gitService.getAllReport(fromDate);
    }

    @GetMapping("/reportByUser")
    @Operation(
            summary = "Get report by user",
            description = "Fetches a report for a specific user based on the request",
            responses = {
                @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid request")
            })
    public List<String> getReportByUser(@RequestBody ReportByUser request) {
        return gitService.getReportByUser(request);
    }
}
