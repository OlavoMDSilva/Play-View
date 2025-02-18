package com.example.play_view.user;

import com.example.play_view.validation.EntityNotFound;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> findAllUsers(@RequestParam(name = "orderBy", defaultValue = "userName", required = false) String order,
                                      @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                      @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                      @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {

        Sort.Direction direction = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        return userService.findAll(order, direction, pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public List<UserDTO> findUserById(@PathVariable long id) {
        return userService.findById(id);
    }

    @GetMapping("/filters")
    public List<UserDTO> findUsersByFilters(@RequestParam(name = "orderBy", defaultValue = "userName", required = false) String order,
                                            @RequestParam(name = "orderDirection", defaultValue = "asc", required = false) String orderDir,
                                            @RequestParam(name = "pageNum", defaultValue = "0", required = false) int pageNum,
                                            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
                                            @RequestParam(name = "userName", defaultValue = "", required = false) String userName,
                                            @RequestParam(name = "email", defaultValue = "", required = false) String email,
                                            @RequestParam(name = "userPhoneNum", defaultValue = "", required = false) String phoneNum,
                                            @RequestParam(name = "startDate", defaultValue = "1900-01-01", required = false) String birthStart,
                                            @RequestParam(name = "endDate", defaultValue = "9999-12-31", required = false) String birthEnd) {
        Sort.Direction orderDirection = orderDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return userService.findByAttribute(order, orderDirection, pageNum, pageSize, userName, email, phoneNum,
                LocalDate.parse(birthStart), LocalDate.parse(birthEnd));
    }

    @PostMapping
    public UserDTO createUser(@RequestParam(name = "isAdmin", defaultValue = "false", required = false) String isAdmin,
                              @Valid @RequestBody CreateUserDTO userDTO) {

        boolean admin = !isAdmin.equalsIgnoreCase("false");

        return userService.saveUser(userDTO, admin);
    }

    @PreAuthorize("#username == authentication.principal.username or hasRole('ADMIN')")
    @PutMapping("/{username}")
    public UserDTO updateUser(@Valid @RequestBody CreateUserDTO userDTO,
                              @PathVariable String username) {

        UserDTO userDTO1 = userService.findByEmail(username);
        CreateUserDTO updatedUser = new CreateUserDTO(userDTO1.userId(), userDTO.userName(), userDTO.email(), userDTO.password(),
                userDTO.profileUrl(), userDTO.userPhoneNum(), userDTO.birthDate(), userDTO.status());
        return userService.saveUser(updatedUser);
    }

    @PreAuthorize("#username == authentication.principal.username or hasRole('ADMIN')")
    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
        userService.deleteUserByEmail(username);
        return "User with email: " + username + " successfully deleted";
    }

}
