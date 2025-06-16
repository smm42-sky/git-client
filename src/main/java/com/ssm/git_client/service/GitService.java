package com.ssm.git_client.service;

import com.ssm.git_client.model.ReportRequest;

import java.util.List;
import java.util.Map;

public interface GitService {

    Map<String, Long> getAuthorReport(ReportRequest request);

    void getAllReport(String fromDate);

    List<String> getReportByUser(ReportRequest request);
}
