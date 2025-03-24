package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.User;
import com.example.relational_test.domain.entity.dto.UserDTO;
import com.example.relational_test.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return  new ResponseEntity(user, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity getUser(@PathVariable("id") Long id){

        return userRepository.findById(id)
                .map(user -> new ResponseEntity(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("User not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity getUserList(){

        List<User> userList = userRepository.findAll();
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserDTO dto){
        User user = userRepository.findById(id)
                .orElseGet(null);

        if(!dto.getName().isEmpty()){
            user.setName(dto.getName());
        }

        if(!dto.getLastName().isEmpty()){
            user.setLastName(dto.getLastName());
        }

        if(dto.getAge() != user.getAge()){
            user.setAge(dto.getAge());
        }

        userRepository.save(user);

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){

        userRepository.deleteById(id);

        return  new ResponseEntity("User removed", HttpStatus.OK);
    }
}
