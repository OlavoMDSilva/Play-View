package com.example.play_view.user;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public record UserController(UserServiceImpl userService) {

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

}
