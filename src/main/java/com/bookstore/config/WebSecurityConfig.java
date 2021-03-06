package com.bookstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder =
				PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth
				.inMemoryAuthentication()
				.withUser("user")
				.password(encoder.encode("user"))
				.roles("USER")
				.and()
				.withUser("admin")
				.password(encoder.encode("admin"))
				.roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/","/list","/login", "/webapp/META-INF", "/resources/**", "/index.html", "/search/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/user/**").hasAnyRole("ADMIN","USER")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/profile")
			.failureUrl("/login?error")
			.usernameParameter("username").passwordParameter("password")
			.and()
			.logout().logoutSuccessUrl("/login?logout")
			.invalidateHttpSession(true)
																																														 .deleteCookies("JSESSIONID");
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception
	{
		webSecurity
				.ignoring()
				.antMatchers("/api/**")
				.antMatchers("/api")
				.antMatchers(AUTH_WHITELIST);
		;
	}

	private static final String[] AUTH_WHITELIST = {
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/v2/api-docs",
			"/webjars/**"
	};
}
