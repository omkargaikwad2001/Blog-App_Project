package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomUserDetailsService;
import com.blog.security.JwtAuthenticationEntryPoint;
import com.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig{
	
	public static final String[] PUBLIC_URLS= {
		
			"/api/auth/login",
			"/v3/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
			
	};
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests()
        .requestMatchers(HttpMethod.GET).permitAll().requestMatchers("/swagger-ui/**").permitAll().requestMatchers(PUBLIC_URLS).permitAll()
        .anyRequest()
        .authenticated()
        .and().exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		http.authenticationProvider(daoAuthenticationProvider());
		
		DefaultSecurityFilterChain build = http.build();
		
		return build;
	}
	
	
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(this.customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception{
		return configuration.getAuthenticationManager();
		
	}
	
}