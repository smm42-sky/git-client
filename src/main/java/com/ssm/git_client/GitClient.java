package com.ssm.git_client;

import com.ssm.git_client.model.Author;
import com.ssm.git_client.model.Comments;
import com.ssm.git_client.model.Discussions;
import com.ssm.git_client.model.MergeRequestResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@ConfigurationProperties("ukis-gitlab")
@Slf4j
public class GitClient extends JsonClient {

    private static final String MERGE_REQUESTS = "/%s/merge_requests?created_after=%s";

    private static final String MERGE_REQUESTS_COMMENTS =
            "/%s/merge_requests/%s/notes?per_page=100";

    private static final String MERGE_REQUESTS_DISCUSSIONS =
            "/%s/merge_requests/%s/discussions?per_page=200";

    private static final int PAGE_LIMIT = 50;

    private static final Predicate<MergeRequestResponse> BRANCH_FILTER =
            response -> "develop".equals(response.getTarget());

    @Value("${ukis-gitlab.token}")
    private String gitAccessToken;

    public Map<String, String> getAuthorizationHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + gitAccessToken);
        return headers;
    }

    public List<MergeRequestResponse> getMergeRequestIds(
            int projectId, String localDate, boolean isDevelop) {
        return getAllData(
                String.format(MERGE_REQUESTS, projectId, localDate),
                1,
                new ArrayList<>(),
                isDevelop);
    }

    public boolean reviewedByUser(int projectId, long iid, String author, String reviewer) {
        return getReviewers(projectId, iid, author).stream().anyMatch(reviewer::equals);
    }

    public List<String> getReviewers(int projectId, long iid, String author) {
        log.info("Processing MR {}", iid);
        List<Discussions> discussions =
                get(
                                String.format(MERGE_REQUESTS_DISCUSSIONS, projectId, iid),
                                Map.of(),
                                getAuthorizationHeaders(),
                                new ParameterizedTypeReference<List<Discussions>>() {})
                        .getBody();

        return org.apache.commons.collections4.CollectionUtils.emptyIfNull(discussions).stream()
                .map(Discussions::getNotes)
                .map(CollectionUtils::firstElement)
                .filter(Objects::nonNull)
                .filter(comment -> Objects.nonNull(comment.getType()))
                .filter(
                        comment ->
                                List.of("DiffNote", "DiscussionNote").contains(comment.getType()))
                .map(Comments::getAuthor)
                .map(Author::getName)
                .filter(Predicate.not(author::equals))
                .distinct()
                .collect(Collectors.toList());
    }

    private List<MergeRequestResponse> getAllData(
            String url, int page, List<MergeRequestResponse> results, boolean isDevelop) {
        String urlWithPagination = url + "&per_page=" + PAGE_LIMIT + "&page=" + page;
        List<MergeRequestResponse> response =
                get(
                                urlWithPagination,
                                Map.of(),
                                getAuthorizationHeaders(),
                                new ParameterizedTypeReference<List<MergeRequestResponse>>() {})
                        .getBody();
        if (CollectionUtils.isEmpty(response)) {
            return results;
        } else {
            response.stream()
                    .filter(isDevelop ? BRANCH_FILTER : Predicate.not(BRANCH_FILTER))
                    .forEach(results::add);
            return getAllData(url, ++page, results, isDevelop);
        }
    }
}
