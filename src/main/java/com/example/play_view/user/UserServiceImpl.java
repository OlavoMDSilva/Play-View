package com.example.play_view.user;

import com.example.play_view.validation.EntityNotFound;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utility.SpecificationBuilder;
import utility.SpecificationHelper;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    public UserServiceImpl(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    public boolean existById(long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<UserDTO> findAll(String order, Sort.Direction orderDir, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        return userRepository.findAll(pageable).stream()
                .map(userDTOMapper)
                .toList();
    }

    @Override
    public List<UserDTO> findByAttribute(String order, Sort.Direction orderDir, int pageNum, int pageSize, String name, String email,
                                         String phoneNum, LocalDate birthStart, LocalDate birthEnd, boolean status) {
        SpecificationHelper<UserEntity> specificationHelper = new SpecificationHelper<>();
        Specification<UserEntity> spec = new SpecificationBuilder<UserEntity>()
                .add((root, query, cb) ->
                        cb.like(root.get("userName"), "%" + name + "%"), name != null && !name.isEmpty())
                .add((root, query, cb) ->
                        cb.like(root.get("email"), "%" + email + "%"), email != null && !email.isEmpty())
                .add((root, query, cb) ->
                        cb.equal(root.get("userPhoneNum"), phoneNum), phoneNum != null && !phoneNum.isEmpty())
                .add((root, query, cb) ->
                        cb.greaterThanOrEqualTo(root.get("birthDate"), birthStart), birthStart != null)
                .add((root, query, cb) ->
                        cb.lessThanOrEqualTo(root.get("birthDate"), birthEnd), birthEnd != null)
                .build();

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(orderDir, order));

        List<UserDTO> users = userRepository.findAll(spec, pageable).stream().map(userDTOMapper).toList();

        if (users.isEmpty()) throw new EntityNotFound("User not found");
        return users;
    }

    @Override
    public List<UserDTO> findById(long id) {
        if (!existById(id)) throw new EntityNotFound("User with id: " + id + " not found");
        return userRepository.findById(id).stream()
                .map(userDTOMapper)
                .toList();
    }

    @Override
    public UserDTO saveUser(CreateUserDTO userDTO, boolean isAdmin) {
        UserEntity user = userDTOMapper.toEntity(userDTO);
        UserEntity savedUser = userRepository.save(user);

        if (!isAdmin) userRepository.assignRole(savedUser.getUserId(), "ROLE_USER");

        return userDTOMapper.apply(savedUser);
    }

    @Override
    public void deleteUserById(long id) {
        if (!existById(id)) throw new EntityNotFound("User with id: " + id + " not found");
        userRepository.deleteById(id);

    }

    @Override
    public void deleteUserByEmail(String email) {
        if (!existByEmail(email)) throw new EntityNotFound("User with email: " + email + " not found");
        userRepository.deleteByEmail(email);
    }
}
