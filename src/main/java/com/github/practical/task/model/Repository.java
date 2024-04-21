package com.github.practical.task.model;

import java.math.BigInteger;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record Repository(String name, String html_url,String url, BigInteger stargazers_count , Timestamp created_at, String language) {
}
