package com.edhaorganics.backend.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	// We use auth manager to validate the user credentials
	private AuthenticationManager authManager;

	private final JwtConfig jwtConfig;

	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JwtConfig jwtConfig) {
		this.authManager = authManager;
		this.jwtConfig = jwtConfig;

		// By default, UsernamePasswordAuthenticationFilter listens to "/login"
		// path.
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri()));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = null;
		String password = null;
		final String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.toLowerCase().startsWith("basic ")) {
			// Authorization: Basic base64credentials
			String base64Credentials = authorization.substring("basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			// credentials = username:password
			final String[] values = credentials.split(":", 2);
			username = values[0];
			password = values[1];
		}

		// 2. Create auth object (contains credentials) which will be used
		// by auth manager
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
				password, Collections.emptyList());

		// 3. Authentication manager authenticate the user, and use
		// UserDetialsServiceImpl::loadUserByUsername() method to load the
		// user.
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "content-type, edha-auth, authorization");
		return authManager.authenticate(authToken);

	}

	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current
	// authenticated user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication auth) throws IOException, ServletException {

		Long now = System.currentTimeMillis();
		String token = Jwts
				.builder()
				.setSubject(auth.getName())
				.claim("authorities",
						auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + jwtConfig.getExpiration() * 1000)) 
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes()).compact();
		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
		response.getWriter().append(jwtConfig.getPrefix() + token);
	}

}