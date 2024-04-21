package com.github.practical.task.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.github.practical.task.model.response.GetUserRepositoriesResponse;
import com.github.practical.task.service.ReposDataFetcherService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
@RestController
public class ReposFetcherController {
	Logger log = LoggerFactory.getLogger(ReposFetcherController.class);

	@Autowired
	private ReposDataFetcherService githubApiClientService;

	public static final String GET_REPOS_URL = "/repos";

	@GetMapping(value = GET_REPOS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserRepositoriesResponse> getRepos(
			@RequestParam(required = false, defaultValue = "0") int numberOfRepos,
			@RequestParam(required = false) String language,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate

	) throws JsonMappingException, JsonProcessingException {
		log.info("Performing the data fetch");

		GetUserRepositoriesResponse response = new GetUserRepositoriesResponse(
				this.githubApiClientService.getRepos(numberOfRepos, language, fromDate));

		return ResponseEntity.ok().body(response);
	}
}
