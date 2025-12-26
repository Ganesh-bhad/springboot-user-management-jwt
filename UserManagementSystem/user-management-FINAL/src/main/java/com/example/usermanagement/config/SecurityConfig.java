package com.example.usermanagement.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.usermanagement.security.JwtFilter;
import com.example.usermanagement.repository.UserRepository;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

	private final UserRepository repo;

	public SecurityConfig(UserRepository repo) {
		this.repo = repo;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()

				.antMatchers("/login", "/register").permitAll()

				.antMatchers("/users").hasRole("ADMIN")

				.antMatchers("/users/**").hasAnyRole("USER", "ADMIN")

				.anyRequest().authenticated().and()
				.addFilterBefore(new JwtFilter(repo), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
