package com.asm.asm;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.asm.dao.UserDAO;
import com.asm.service.CustomUserDetals;
import com.asm.service.UserService;

@Configuration
@EnableWebSecurity
public class AuthConfig {
	@Autowired
	DataSource datasource;
	@Autowired
	UserDAO dao;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
		  .authorizeHttpRequests(auth -> auth
			        .requestMatchers("/admin/**").hasAuthority("ADMIN")
			        .requestMatchers("/cart/**").hasAnyAuthority("USER", "ADMIN")
			        .anyRequest().permitAll()
			    )
		  .oauth2Login(auth -> auth.loginPage("/auth/login/form")
				  .defaultSuccessUrl("/auth/login/success", true)
				  .authorizationEndpoint( t -> t.baseUri("/oauth2/authorization"))
				  )

				.formLogin(form -> form.loginPage("/auth/login/form").loginProcessingUrl("/auth/login/form")
						.defaultSuccessUrl("/home", true));
		
		return http.build();
	}
	
	public void create() {
		
	}
	
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;
	}


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

}
