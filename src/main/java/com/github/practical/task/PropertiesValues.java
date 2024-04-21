package com.github.practical.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesValues {
	@Value("${github.api.url.base}")
	public String githubApiBaseUrl;
	@Value("${github.api.version}")
	public String githubApiVersion;

}
