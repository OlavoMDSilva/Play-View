package com.example.play_view.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery("select email, user_password, user_status from users where email = ?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("""
                select u.email, `role` from `roles` r
                join users u on r.cod_user = user_id
                where u.email = ?;
                """);

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
//                            .anyRequest().permitAll()
                        .requestMatchers(HttpMethod.GET, "/games").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/games/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/games").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/games/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/companies").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/companies/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/companies").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/companies/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/companies/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/genres").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/genres/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/genres").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/genres/**").hasRole("ADMIN")
            ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
