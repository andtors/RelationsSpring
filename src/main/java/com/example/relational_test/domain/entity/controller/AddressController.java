package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.Address;
import com.example.relational_test.domain.entity.User;
import com.example.relational_test.domain.entity.dto.AddressDTO;
import com.example.relational_test.repository.AddressRepository;
import com.example.relational_test.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressController(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody AddressDTO dto){

        User user = userRepository.findById(dto.getUser_id())
                .orElseGet(null);

        Address address = new Address(dto.getStreet(), dto.getCity(), user);

        addressRepository.save(address);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity getAddress(@PathVariable("id") Long id){
        return addressRepository.findById(id)
                .map(address -> new ResponseEntity(address, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Erro", HttpStatus.NOT_FOUND));
    }
}
