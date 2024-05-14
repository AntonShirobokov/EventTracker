package ru.company.shirobokov.EventTracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ru.company.shirobokov.EventTracker.repository.PeopleRepository;
import ru.company.shirobokov.EventTracker.service.PersonDetailsService;

@Configuration
@EnableWebSecurity
public class SpringConfig {
	
	
	@Bean 
	public UserDetailsService detailsService() {
		return new PersonDetailsService();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(detailsService());
		provider.setPasswordEncoder(encoder());
		return provider;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/process_login")
				.defaultSuccessUrl("/", true).failureUrl("/login?error"));
		
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
				authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
						.requestMatchers("/css/*.css").permitAll()
						.requestMatchers("/img/ForExample/*.*").permitAll()
						.requestMatchers("/registration").anonymous()
						.requestMatchers("/profile/*").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/admin").hasRole("ADMIN")
						.requestMatchers("/", "/login", "registration", "/error").permitAll()
						.anyRequest().authenticated());
		http.logout(logout->logout.logoutUrl("/profile/logout").logoutSuccessUrl("/login"));
		return http.build();
	}
}
