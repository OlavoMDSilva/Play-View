package com.example.play_view.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
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

    private void configurePermissions(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth, String basePath) {
        auth.requestMatchers(HttpMethod.GET, basePath).hasRole("USER")
            .requestMatchers(HttpMethod.GET, basePath + "/**").hasRole("USER")
            .requestMatchers(HttpMethod.POST, basePath).hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, basePath + "/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, basePath + "/**").hasRole("ADMIN");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> {
                        configurePermissions(auth, "/games");
                        configurePermissions(auth, "/companies");
                        configurePermissions(auth, "/genres");
                        configurePermissions(auth, "/publishers");
                    }
            ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
