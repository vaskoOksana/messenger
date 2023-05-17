package com.messenger.publisher.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String ROLE = "USER";

    @Value(value = "${spring.security.user-name.1}")
    private String userName;

    @Value(value = "${spring.security.user-name.2}")
    private String userName2;

    @Value(value = "${spring.security.password}")
    private String password;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests().
            requestMatchers("/", "/ws/**").permitAll()
            .and()
            .authorizeHttpRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .logout(logout -> logout.logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username(userName)
            .password(password)
            .roles(ROLE)
            .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
            .username(userName2)
            .password(password)
            .roles(ROLE)
            .build();

        return new InMemoryUserDetailsManager(user, user2);
    }
}