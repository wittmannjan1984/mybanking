package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.UserData;
import org.example.model.dto.UserCreateDto;
import org.example.model.dto.UserUpdateDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User controller", description = "User management")
@RestController
@RequestMapping("authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create new user data", description = "Allow to register new user in the system")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UserCreateDto user) {
        userService.create(new UserData(user.getLogin(), user.getPassword()));
    }

    @Operation(summary = "Update user role", description = "Allow to update user role from USER to MANAGER")
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "basicauth")
    @PreAuthorize("hasAnyAuthority('MANAGER')")
    public void updateRole(@RequestBody UserUpdateDto userUpdateDto) {
        userService.updateRoleToManager(userUpdateDto.getLogin());
    }
}