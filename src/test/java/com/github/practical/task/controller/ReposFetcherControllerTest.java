package com.github.practical.task.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.practical.task.PropertiesValues;
import com.github.practical.task.service.ReposDataFetcherService;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
class ReposFetcherControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	ReposDataFetcherService githubApiClientService;

	@MockBean
	PropertiesValues propertiesValues;

	@Test
	void testGetUserRepos() throws Exception {
		ResultActions response = mockMvc.perform(
				get("/api/repos").param("numberOfRepos", "1").param("Language", "Java").param("Date", "2020-02-20"));
		response.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
