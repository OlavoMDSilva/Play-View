package com.example.play_view.user;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    boolean existById(long id);
    boolean existByEmail(String email);
    List<UserDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize);
    List<UserDTO> findByAttribute(String order, Sort.Direction orderDir,
                                  int pageNum, int pageSize,
                                  String name, String email, String phoneNum, LocalDate birthStart, LocalDate birthEnd, boolean status);
    List<UserDTO> findById(long id);
    @Transactional
    UserDTO saveUser(CreateUserDTO userDTO, boolean isAdmin);
    @Transactional
    void deleteUserById(long id);
    @Transactional
    void deleteUserByEmail(String email);
}
