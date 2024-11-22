package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.UserRequestDto;
import org.springframework.web.bind.annotation.*;

import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/login")
    public FullUserDto login(@RequestBody CredentialsDto credentialsDto) {
        return userService.login(credentialsDto);
    }

    @PostMapping("/{companyId}/users")
    public FullUserDto createUser(@PathVariable Long companyId, @RequestBody UserRequestDto request) {
        return userService.createUser(companyId, request);
    }


    @DeleteMapping("/{userId}")
    public FullUserDto deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
