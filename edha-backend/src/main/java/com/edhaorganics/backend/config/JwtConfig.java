package com.edhaorganics.backend.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
	@Value("${security.jwt.uri:/edha/api/user/login}")
	private String Uri;

	@Value("${security.jwt.header:edha-auth}")
	private String header;

	@Value("${security.jwt.prefix:Bearer }")
	private String prefix;

	@Value("${security.jwt.expiration:#{60*60}}")
	private int expiration;

	@Value("${security.jwt.secret:killerMonster}")
	private String secret;

	public String getUri() {
		return Uri;
	}

	public void setUri(String uri) {
		Uri = uri;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}



}