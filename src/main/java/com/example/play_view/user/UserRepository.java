package com.example.play_view.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    boolean existsByEmail(String email);
    void deleteByEmail(String email);

    @Query(value = "insert into roles (cod_user, role) values (:userId, :role)", nativeQuery = true)
    void assignRole(@Param("userId") long userId, @Param("role") String role);

}
