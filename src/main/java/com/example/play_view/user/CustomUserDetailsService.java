package com.example.play_view.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;

public class CustomUserDetailsService implements UserDetailsService {

    private final JdbcTemplate jdbcTemplate;

    public CustomUserDetailsService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        String sql = "select user_id, email, user_password, user_status from users where email = ?";
//        try {
//            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
//                long id = rs.getLong("user_id");
//                String emailFromDb = rs.getString("email");
//                String password = rs.getString("user_password");
//                String enabled = rs.getBoolean("user_status");
//            });
//        }
        return null;
    }
}
