package com.legends.taskFlow.controller;

import com.legends.taskFlow.model.dto.UserDTO;
import com.legends.taskFlow.service.Impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @Operation(summary = "Получение пользователя")
    @PostMapping("/create/user")
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        userService.createNewUser(userDTO);
        return ResponseEntity.ok("");
    }
    
}
