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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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

    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        //Create admin
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("12345"))
                .roles("ADMIN")
                .build();

        //Create staff
        UserDetails staff = User.withUsername("staff")
                .password(passwordEncoder.encode("12345"))
                .roles("STAFF")
                .build();

        //Create customer
        UserDetails customer = User.withUsername("customer")
                .password(passwordEncoder.encode("12345"))
                .roles("CUSTOMER")
                .build();

        //Add user into in-memory user manager
        manager.createUser(admin);
        manager.createUser(staff);
        manager.createUser(customer);

        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //TODO: What you want to customize
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**", "/api/v1/files/**").permitAll() //spring security allows this endpoint
                //.requestMatchers("/api/v1/categories/**").hasAnyAuthority("STAFF", "ADMIN")
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAnyAuthority("product:read")
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAnyAuthority("product:create")
                .requestMatchers(
                        HttpMethod.PUT,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAnyAuthority("product:update")
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/v1/categories/**",
                        "/api/v1/products/**").hasAnyAuthority("product:delete")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyAuthority("user:read")
                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAnyAuthority("user:create")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority("user:update")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAnyAuthority("user:delete")
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
