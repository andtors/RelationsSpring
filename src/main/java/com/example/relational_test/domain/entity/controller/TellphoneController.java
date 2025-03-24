package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.Tellphone;
import com.example.relational_test.domain.entity.User;
import com.example.relational_test.domain.entity.dto.TellphoneDTO;
import com.example.relational_test.repository.TellphoneRepository;
import com.example.relational_test.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

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

        return new ResponseEntity(tellphone, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getTellList(){
        List<Tellphone> tellphoneList = tellphoneRepository.findAll();

        return new ResponseEntity(tellphoneList, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity updateTell(@PathVariable("id")  Long id, @RequestBody TellphoneDTO dto){
        Tellphone tellphone = tellphoneRepository.findById(id)
                .orElseGet(null);

        if(dto.getNumber() != tellphone.getNumber()){
            tellphone.setNumber(dto.getNumber());
        }

        tellphoneRepository.save(tellphone);

        return new ResponseEntity(tellphone, HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity getTellphone(@PathVariable("id") Long id){
        return tellphoneRepository.findById(id)
                .map(tell -> new ResponseEntity(tell, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Erro", HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTell(@PathVariable("id") Long id){

        tellphoneRepository.deleteById(id);

        return new ResponseEntity("Tellphone removed", HttpStatus.OK);
    }
}
