package com.example.play_view.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Slf4j
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

        jdbcUserDetailsManager.setEnableGroups(false);
        jdbcUserDetailsManager.setEnableAuthorities(true);

        return jdbcUserDetailsManager;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsManager userDetailsManager) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsManager);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }

    private void configurePermissions(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth, String basePath) {
        switch (basePath) {
            case "/reviews" -> auth.requestMatchers(HttpMethod.POST, basePath).hasAnyRole("USER")
                    .requestMatchers(HttpMethod.PUT, basePath + "/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, basePath + "/**").hasAnyRole("USER", "ADMIN");

            case "/users" -> auth.requestMatchers(HttpMethod.PUT, basePath + "/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, basePath + "/**").hasAnyRole("USER", "ADMIN");

            default -> auth.requestMatchers(HttpMethod.POST, basePath).hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, basePath + "/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, basePath + "/**").hasRole("ADMIN");
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> {
                        configurePermissions(auth, "/games");
                        configurePermissions(auth, "/companies");
                        configurePermissions(auth, "/genres");
                        configurePermissions(auth, "/publishers");
                        configurePermissions(auth, "/users");
                        configurePermissions(auth, "/reviews");
                        auth.anyRequest().permitAll();
                    }
            ).authenticationProvider(authenticationProvider)
            .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }

}
