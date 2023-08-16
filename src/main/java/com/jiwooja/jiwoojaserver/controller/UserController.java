package com.jiwooja.jiwoojaserver.controller;

import com.jiwooja.jiwoojaserver.dto.UserDto;
import com.jiwooja.jiwoojaserver.repository.UserRepository;
import com.jiwooja.jiwoojaserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/usernameChecker")
    public boolean usernameChecker(String username) {
        return userService.usernameChecker(username);
    }

}
