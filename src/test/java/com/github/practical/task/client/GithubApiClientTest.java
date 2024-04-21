package com.github.practical.task.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

import com.github.practical.task.PropertiesValues;

import kong.unirest.GetRequest;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

@ExtendWith(MockitoExtension.class)
class GithubApiClientTest {

	@InjectMocks
	GithubApiClient client;

	GithubApiClient apiClient;

	@Mock
	PropertiesValues propertiesValues;

	@Mock
	GetRequest getRequest;

	@Mock
	UriComponentsBuilder builder;

	private static final String resp = "{\"total_count\":15726010,\"incomplete_results\":true,\"items\":[{\"name\":\"JavaGuide\",\"url\":\"https://github.com/Snailclimb/JavaGuide\",\"stargazers_count\":143005,\"language\":\"Java\"}]}";

	Date date;

	String wrongUrl;

	String exceptionMessage = "URI does not specify a valid host name: https:/api.github.com/search/repositories?";

	private static final String API_VERSION_HEADER_KEY = "X-GitHub-Api-Version";

	private static final String API_VERSION_HEADER = "2022-11-28";

	private static final String API_VERSION_HEADER_VAL = "application/vnd.github+json";

	@BeforeEach
	void init() {
		propertiesValues.githubApiBaseUrl = "https://api.github.com/search/repositories?";

		propertiesValues.githubApiVersion = "2022-11-28";

		date = new Date();
		apiClient = Mockito.spy(GithubApiClient.class);
		wrongUrl = "https:/api.github.com/search/repositories?";
		exceptionMessage = "kong.unirest.UnirestException: URI does not specify a valid host name: https:/api.github.com/search/repositories?";

	}

	@Test
	void performRequestTest() throws UnirestException {

		try (MockedStatic<Unirest> mockedUnirest = Mockito.mockStatic(Unirest.class)) {
			mockedUnirest.when(() -> Unirest.get(propertiesValues.githubApiBaseUrl).header("accept", API_VERSION_HEADER)
					.header(API_VERSION_HEADER_KEY, API_VERSION_HEADER_VAL)).thenReturn(getRequest);

		}
		assertNotEquals(this.client.performRepoRequest(propertiesValues.githubApiBaseUrl).length(), 0);
		assertNotNull(this.client.performRepoRequest(propertiesValues.githubApiBaseUrl));

	}

	@Test
	void performRequestThrowExceptionTest() throws UnirestException {

		try (MockedStatic<Unirest> mockedUnirest = Mockito.mockStatic(Unirest.class)) {
			mockedUnirest.when(() -> Unirest.get(wrongUrl).header("accept", API_VERSION_HEADER)
					.header(API_VERSION_HEADER_KEY, API_VERSION_HEADER_VAL)).thenThrow(UnirestException.class);

		}
		Exception exception = assertThrows(UnirestException.class,
				() -> this.client.performRepoRequest(this.client.performRepoRequest(wrongUrl)));

		assertEquals(exceptionMessage, exception.getMessage());

	}

	@Test
	void testGetRepos() throws MalformedURLException, URISyntaxException {
		lenient().when(apiClient.buildUrlWithParameters(propertiesValues.githubApiBaseUrl, 1, "date", "java"))
				.thenReturn(propertiesValues.githubApiBaseUrl);
		try (MockedStatic<Unirest> mockedUnirest = Mockito.mockStatic(Unirest.class)) {
			mockedUnirest.when(() -> Unirest.get(propertiesValues.githubApiBaseUrl).header("accept", API_VERSION_HEADER)
					.header(API_VERSION_HEADER_KEY, API_VERSION_HEADER_VAL)).thenReturn(getRequest);

		}
		lenient().when(apiClient.performRepoRequest(propertiesValues.githubApiBaseUrl)).thenReturn(resp);

		assertNotNull(apiClient.getRepos(10, date, "java"));
		assertNotNull(apiClient.getRepos(10, null, "java"));
	}

	@Test
	void testbuildUrlWithParameters() {
		lenient().when(builder.path(propertiesValues.githubApiBaseUrl)).thenReturn(builder);
		assertEquals(client.buildUrlWithParameters(propertiesValues.githubApiBaseUrl, 0, "2020-11-20", "Go"),
				"https://api.github.com/search/repositories?q=language:go+created:2020-11-20&sort=stars&order=desc");
		assertEquals(client.buildUrlWithParameters(propertiesValues.githubApiBaseUrl, 0, "2020-11-20", "Java"),
				"https://api.github.com/search/repositories?q=language:java+created:2020-11-20&sort=stars&order=desc");
		assertEquals(client.buildUrlWithParameters(propertiesValues.githubApiBaseUrl, 2, "2020-11-20", "Java"),
				"https://api.github.com/search/repositories?q=language:java+created:2020-11-20&sort=stars&order=desc&per_page=2");
		assertEquals(client.buildUrlWithParameters(propertiesValues.githubApiBaseUrl, 2, "2020-11-20", null),
				"https://api.github.com/search/repositories?q=created:2020-11-20&sort=stars&order=desc&per_page=2");
		assertEquals(client.buildUrlWithParameters(propertiesValues.githubApiBaseUrl, 2, null, "java"),
				"https://api.github.com/search/repositories?q=language:java&sort=stars&order=desc&per_page=2");
		assertEquals(client.buildUrlWithParameters(propertiesValues.githubApiBaseUrl, 2, null, null),
				"https://api.github.com/search/repositories?q=Q&sort=stars&order=desc&per_page=2");
		assertEquals(client.buildUrlWithParameters("https://navdeep.practical.com?", 0, "2020-11-20", "Go"),
				"https://navdeep.practical.com?q=language:go+created:2020-11-20&sort=stars&order=desc");
	}
}
