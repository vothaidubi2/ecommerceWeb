package com.asm.asm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
						authz -> authz.requestMatchers("/home", "/auth/login/**", "/assets/**","/cart","/checkout","/shoplist").permitAll()
								.anyRequest().authenticated())
				.formLogin(login -> login.loginPage("/auth/login/form").loginProcessingUrl("auth/login")
						.defaultSuccessUrl("/home", false).usernameParameter("username").passwordParameter("password"))
				.rememberMe(remem -> remem.rememberMeParameter("remember"));
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailManager() {
		return new InMemoryUserDetailsManager(
				User.withUsername("user").password(passwordEncoder().encode("login")).authorities("USER").build(),
				User.withUsername("admin").password(passwordEncoder().encode("login")).authorities("USER","ADMIN").build(),
				User.withUsername("guest").password(passwordEncoder().encode("login")).authorities("USER","ADMIN","GUEST").build()
				);
	}
}
