package com.ssm.git_client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.git_client.GitClient;
import com.ssm.git_client.common.Component;
import com.ssm.git_client.model.MergeRequestResponse;
import com.ssm.git_client.model.ReportByUser;
import com.ssm.git_client.model.ReportRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class GitServiceImpl implements GitService {

    private final GitClient gitClient;

    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Long> getAuthorReport(ReportRequest request) {
        Component component = request.getComponent();
        List<MergeRequestResponse> mergeRequests =
                gitClient.getMergeRequestIds(
                        component.getId(), request.getFromDate(), request.isDevelop());
        log.info("Total MR in time period {}", mergeRequests.size());

        Map<String, Long> report =
                mergeRequests.stream()
                        .map(
                                mrResponse ->
                                        gitClient.getReviewers(
                                                component.getId(),
                                                mrResponse.getIid(),
                                                mrResponse.getAuthor().getName()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        writeReport(
                request.getComponent(),
                report,
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + ".json");
        return report;
    }

    @Override
    public void getAllReport(String fromDate) {
        Arrays.stream(Component.values())
                .forEach(
                        component -> {
                            ReportRequest request = buildReportRequest(fromDate, component);
                            log.info("Processing for {} develop", component);
                            Map<String, Long> report = getAuthorReport(request);
                            writeReport(component, report, "_Develop.json");
                            log.info("Processing for {} feature", component);
                            request.setDevelop(false);
                            report = getAuthorReport(request);
                            writeReport(component, report, "_Feature.json");
                            log.info("Process complete for {}", component);
                        });
    }

    @Override
    public List<String> getReportByUser(ReportByUser request) {
        Component component = request.getComponent();
        List<MergeRequestResponse> mergeRequests =
                gitClient.getMergeRequestIds(
                        component.getId(), request.getFromDate(), request.isDevelop());
        log.info("Total MR in time period {}", mergeRequests.size());

        return mergeRequests.stream()
                .filter(
                        mrResponse ->
                                gitClient.reviewedByUser(
                                        component.getId(),
                                        mrResponse.getIid(),
                                        mrResponse.getAuthor().getName(),
                                        request.getAuthor()))
                .map(MergeRequestResponse::getUrl)
                .collect(Collectors.toList());
    }

    private void writeReport(Component component, Map<String, Long> report, String name) {
        File file = new File(component.name() + name);
        try {
            Files.write(
                    file.toPath(),
                    objectMapper
                            .writerWithDefaultPrettyPrinter()
                            .writeValueAsString(report)
                            .getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Error while writing file for {}", component, e);
        }
    }

    private ReportRequest buildReportRequest(String fromDate, Component component) {
        return ReportRequest.builder()
                .component(component)
                .develop(true)
                .fromDate(fromDate)
                .build();
    }
}
