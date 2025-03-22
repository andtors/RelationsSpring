package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.User;
import com.example.relational_test.domain.entity.dto.UserDTO;
import com.example.relational_test.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody UserDTO dto){

        User user = new User(dto.getName(), dto.getLastName(),dto.getAge());

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity getUser(@PathVariable("id") Long id){

        return userRepository.findById(id)
                .map(user -> new ResponseEntity(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("User not found", HttpStatus.NOT_FOUND));
    }
}
