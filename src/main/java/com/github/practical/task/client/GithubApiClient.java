package com.github.practical.task.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public final class GithubApiClient {

	Logger log = LoggerFactory.getLogger(GithubApiClient.class);
	private static final String BASE_URL = "https://api.github.com/search/repositories";
	private static final String API_VERSION_HEADER_KEY = "X-GitHub-Api-Version";
	private static final String API_VERSION_HEADER_VER = "2022-11-28";
	private static final String API_VERSION_HEADER = "application/vnd.github+json";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String PER_PAGE_PARAM_KEY = "per_page";
	private static final String DATE_PARAM_KEY = "created";
	private static final String Q_PARAM_KEY = "q";
	private static final String SORT_PARAM_KEY = "sort";
	private static final String SORT_PARAM_VAL = "stars";
	private static final String ORDER_PARAM_KEY = "order";
	private static final String ORDER_PARAM_VAL = "desc";
	private static final String LANG_PARAM_KEY = "language";
	private static final String COLON_SYMBOL = ":";
	private static final String ADD_SYMBOL = "+";

	/*
	 * Returns the repositories from Github Api & Can be used for further manipulation
	 * of user values.
	 */
	public String getRepos(int numberOfRepos, Date fromDate, String language) {
		String formattedDate = null;
		if (fromDate != null) {
			formattedDate = new SimpleDateFormat(DATE_FORMAT).format(fromDate);
		}

		return performRepoRequest(buildUrlWithParameters(BASE_URL, numberOfRepos, formattedDate, language));

	}

	/*
	 * Hits the Github Api to fetch repos
	 */
	public String performRepoRequest(String url) throws UnirestException {
		HttpResponse<String> response = Unirest.get(url).header("Accept", API_VERSION_HEADER)
				.header(API_VERSION_HEADER_KEY, API_VERSION_HEADER_VER).asString();
		return response.getBody();

	}

	/*
	 * builds the url for fetching on basis of user defined values.
	 */
	public String buildUrlWithParameters(String url, int numberOfRepos, String fromDate, String language) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if (StringUtils.hasText(language) & StringUtils.hasText(fromDate)) {
			StringBuilder uriString = new StringBuilder();
			uriString.append(LANG_PARAM_KEY);
			uriString.append(COLON_SYMBOL);
			uriString.append(language.toLowerCase());
			uriString.append(ADD_SYMBOL);
			uriString.append(DATE_PARAM_KEY);
			uriString.append(COLON_SYMBOL);
			uriString.append(fromDate);
			builder.queryParam(Q_PARAM_KEY, uriString);

		} else if (StringUtils.hasText(language) & !StringUtils.hasText(fromDate)) {
			StringBuilder uriString = new StringBuilder();
			uriString.append(LANG_PARAM_KEY);
			uriString.append(COLON_SYMBOL);
			uriString.append(language.toLowerCase());
			builder.queryParam(Q_PARAM_KEY, uriString);
		}

		if (!StringUtils.hasText(language) && StringUtils.hasText(fromDate)) {

			builder.queryParam(Q_PARAM_KEY, DATE_PARAM_KEY + COLON_SYMBOL + fromDate);

		} else if (language == null && fromDate == null) {
			builder.queryParam(Q_PARAM_KEY, 'Q');
		}
		builder.queryParam(SORT_PARAM_KEY, SORT_PARAM_VAL);
		builder.queryParam(ORDER_PARAM_KEY, ORDER_PARAM_VAL);
		if (numberOfRepos > 0) {

			builder.queryParam(PER_PAGE_PARAM_KEY, numberOfRepos);
		}
		return builder.build(true).toUriString();
	}
}