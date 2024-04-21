
package com.github.practical.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.practical.task.client.GithubApiClient;

@Configuration
public class ReposDataFetcherConfig {

	@Bean
	GithubApiClient githubApiClient() {
		return new GithubApiClient();
	}
}
