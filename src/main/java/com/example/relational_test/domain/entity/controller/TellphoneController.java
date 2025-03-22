package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.Tellphone;
import com.example.relational_test.domain.entity.User;
import com.example.relational_test.domain.entity.dto.TellphoneDTO;
import com.example.relational_test.repository.TellphoneRepository;
import com.example.relational_test.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tellphones")
public class TellphoneController {

    private final TellphoneRepository tellphoneRepository;
    private final UserRepository userRepository;

    public TellphoneController(TellphoneRepository tellphoneRepository, UserRepository userRepository) {
        this.tellphoneRepository = tellphoneRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody TellphoneDTO dto){

        User user = userRepository.findById(dto.getUser_id())
                .orElseGet(null);

        Tellphone tellphone = new Tellphone(dto.getNumber(), user);

        tellphoneRepository.save(tellphone);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("{id}")
    public ResponseEntity getTellphone(@PathVariable("id") Long id){
        return tellphoneRepository.findById(id)
                .map(tell -> new ResponseEntity(tell, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Erro", HttpStatus.NOT_FOUND));
    }

}
