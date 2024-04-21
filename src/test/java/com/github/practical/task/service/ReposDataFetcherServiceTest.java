package com.github.practical.task.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.practical.task.client.GithubApiClient;

@ExtendWith(MockitoExtension.class)
class ReposDataFetcherServiceTest {

	@Mock
	GithubApiClient githubApiClient;

	@InjectMocks
	ReposDataFetcherService service;

	Date date = new Date();
	String resp = "{\"total_count\":15726010,\"incomplete_results\":true,\"items\":[{\"name\":\"JavaGuide\",\"url\":\"https://github.com/Snailclimb/JavaGuide\",\"stargazers_count\":143005,\"language\":\"Java\"}]}";

	@Test
	void testGetRepos() throws JsonMappingException, JsonProcessingException {
		Mockito.when(this.githubApiClient.getRepos(1, date, "Java")).thenReturn(resp);
		assertEquals(this.service.getRepos(1, "Java", date).size(), 1);
	}

}
