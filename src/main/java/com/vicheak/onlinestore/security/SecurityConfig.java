package com.vicheak.onlinestore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //TODO: What you want to customize
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**", "/api/v1/files/**").permitAll() //spring security allows these endpoints
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("product:read")
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("product:write")
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("product:update")
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAuthority("product:delete")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAuthority("user:profile")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("user:read")
                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAuthority("user:write")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("user:update")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("user:delete")
                .anyRequest().authenticated());

        //TODO: Use default form login
        //http.formLogin(Customizer.withDefaults()); //for statefull

        //TODO: Configure HTTP Basic for Client Application. Example: Postman, Insomnia
        http.httpBasic(Customizer.withDefaults());
        http.csrf(token -> token.disable()); //when csrf disable, cannot use form login

        //TODO: Update API policy to STATELESS
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //more secured

        return http.build();
    }

}
