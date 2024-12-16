package com.fernando_almanza.spring_cloud.msv.user.msv_users.controllers;

import com.fernando_almanza.spring_cloud.msv.user.msv_users.models.entities.User;
import com.fernando_almanza.spring_cloud.msv.user.msv_users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message: ", "email already exists!"));
        }
        if (result.hasErrors()) {
            return validErrors(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser, BindingResult result) {
        if (result.hasErrors()) {
            return validErrors(result);
        }
        return userService.findById(id)
               .map(user -> {
                   if (!user.getEmail().equalsIgnoreCase(updatedUser.getEmail().trim()) && userService.findByEmail(updatedUser.getEmail()).isPresent()) {
                       return ResponseEntity.badRequest().body(Collections.singletonMap("message: ", "email already exists!"));
                   }
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    return ResponseEntity.ok(userService.save(user));
                })
               .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.findById(id)
               .map(user -> {
                    userService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
               .orElse(ResponseEntity.notFound().build());
    }

    private static ResponseEntity<Map<String, String>> validErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<String, String>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), "El campo + error.getField() + " + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping("/users-courses")
    public ResponseEntity<?> listUsersWithCourse(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(userService.findAllById(ids));
    }
}
