package com.edhaorganics.backend.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.edhaorganics.backend.service.EdhaUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 @Override
	 protected void configure(HttpSecurity httpSecurity) throws Exception {
	 httpSecurity.httpBasic().and().authorizeRequests()
	 .anyRequest().authenticated().and().csrf().disable()
	 .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
	 .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
	 .sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling()
				.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED));
	 }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
//
//	@Autowired
//	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.inMemoryAuthentication().withUser("user")
//				.password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("user")).roles("ADMIN")
//				.and().withUser("user2")
//				.password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("user")).roles("USER");
//	}
	
	@Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(userDetailsService);
    }

	@Autowired
	EdhaUserDetailsService userDetailsService;
	@Autowired
	private JwtConfig jwtConfig;

	@Bean
	public JwtConfig jwtConfig() {
		return new JwtConfig();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf()
//				.disable()
//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and()
//				.exceptionHandling()
//				.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//				.and()
//				//.addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
//				.authorizeRequests().antMatchers("/edha/api/user/login").permitAll().anyRequest().authenticated().and().httpBasic();
//	}
	

}
