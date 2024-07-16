package com.example.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {
	
	/*
	@Bean
	public UserDetailsService authenticate(PasswordEncoder encoder) throws Exception {
		UserDetails user = User.withUsername("user").password(encoder.encode("1234")).roles("USER").build();
		UserDetails admin = User.withUsername("admin").password(encoder.encode("1234")).roles("ADMIN", "USER").build();
		return new InMemoryUserDetailsManager(user, admin);
			
	}
	*/
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
	}
	@Bean
	public SecurityFilterChain authorization(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(auth->
		auth.requestMatchers("/admin").hasRole("ADMIN")
			.requestMatchers("/user").hasAnyRole("ADMIN", "USER")
			.requestMatchers("/**").permitAll())
			.formLogin(formLogin -> formLogin.permitAll())
			.logout(logout -> logout.permitAll())
			.build();
	}
}