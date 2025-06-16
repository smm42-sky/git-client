package com.ssm.git_client.controller;

import com.ssm.git_client.model.ReportRequest;
import com.ssm.git_client.service.GitService;

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
    public Map<String, Long> getAuthorReport(@RequestBody ReportRequest request) {
        return gitService.getAuthorReport(request);
    }

    @GetMapping("verify")
    public String verify() {
        return "Hello User";
    }

    @GetMapping("/getAllReport")
    @ResponseStatus(HttpStatus.CREATED)
    public void getAllReport(@RequestParam("fromDate") String fromDate) {
        gitService.getAllReport(fromDate);
    }

    @GetMapping("/reportByUser")
    public List<String> getReportByUser(@RequestBody ReportRequest request) {
        return gitService.getReportByUser(request);
    }
}
