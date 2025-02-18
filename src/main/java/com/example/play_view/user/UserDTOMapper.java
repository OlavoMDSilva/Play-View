package com.example.play_view.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<UserEntity, UserDTO> {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDTO apply(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getUserId(),
                userEntity.getUserName(),
                userEntity.getEmail(),
                userEntity.getProfileUrl(),
                userEntity.getUserPhoneNum(),
                userEntity.getBirthDate(),
                userEntity.isEnabled()
        );
    }

    public UserEntity toEntity(CreateUserDTO userDTO) {
        UserEntity user = new UserEntity();

        if (user.getUserId() < 0) {
            user.setUserId(0);
        }else {
            user.setUserId(userDTO.userId());
        }

        user.setUserName(userDTO.userName());
        user.setEmail(userDTO.email());
        user.setUserPassword(encoder.encode(userDTO.password()));
        user.setProfileUrl(userDTO.profileUrl());
        user.setUserPhoneNum(userDTO.userPhoneNum());
        user.setBirthDate(userDTO.birthDate());
        user.setStatus(userDTO.status());

        return user;
    }

}
