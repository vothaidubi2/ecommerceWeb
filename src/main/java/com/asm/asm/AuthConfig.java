package com.asm.asm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeHttpRequests(
						authz -> authz.requestMatchers("/home", "/auth/login/**", "/assets/**", "/lib/**").permitAll()
								.anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/auth/login/form").loginProcessingUrl("auth/login")
						.defaultSuccessUrl("/auth/login/form", false).usernameParameter("username").passwordParameter("password"))
				.rememberMe(remem -> remem.rememberMeParameter("remember"));
		return http.build();
	}
	
	

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(( username) -> {
				
				return null;
			
		});
//		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER")
//				.and().withUser("admin").password(passwordEncoder().encode("password")).roles("USER", "ADMIN").and()
//				.withUser("guest").password(passwordEncoder().encode("password")).roles("USER", "ADMIN", "GUEST");
	}
}
