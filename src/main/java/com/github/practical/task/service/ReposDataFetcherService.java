package com.github.practical.task.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.practical.task.client.GithubApiClient;
import com.github.practical.task.model.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReposDataFetcherService {

	@Autowired
	private GithubApiClient githubApiClient;
	
	/*
		Returns the list of repositories
	*/
	public List<Repository> getRepos(int numberOfRepos, String language, Date fromDate)
			throws JsonMappingException, JsonProcessingException {

		Repository newJsonNode = null;
		List<Repository> repostories = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(this.githubApiClient.getRepos(numberOfRepos, fromDate, language));
		Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> field = fields.next();
			if (field.getKey() == "items") {
				for (JsonNode repoNode : field.getValue()) {
					ObjectMapper mapper = new ObjectMapper();
					newJsonNode = mapper.treeToValue(repoNode, Repository.class);
					repostories.add(newJsonNode);
				}

			}
		}

		return repostories;
	}

}
