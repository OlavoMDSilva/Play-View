package com.example.play_view.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByEmail(String email);
    boolean existsByEmail(String email);

    @Transactional

    void deleteByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "insert into roles (cod_user, role) values (:userId, :role)", nativeQuery = true)
    void assignRole(@Param("userId") long userId, @Param("role") String role);

}
