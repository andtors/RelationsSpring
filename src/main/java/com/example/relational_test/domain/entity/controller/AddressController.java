package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.Address;
import com.example.relational_test.domain.entity.Tellphone;
import com.example.relational_test.domain.entity.User;
import com.example.relational_test.domain.entity.dto.AddressDTO;
import com.example.relational_test.domain.entity.dto.TellphoneDTO;
import com.example.relational_test.repository.AddressRepository;
import com.example.relational_test.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return new ResponseEntity(address, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity getAddress(@PathVariable("id") Long id){
        return addressRepository.findById(id)
                .map(address -> new ResponseEntity(address, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Erro", HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity updateAddress(@PathVariable("id")  Long id,@RequestBody AddressDTO dto){
        Address address = addressRepository.findById(id)
                .orElseGet(null);

        if(dto.getStreet() != address.getStreet()){
            address.setStreet(dto.getStreet());
        }

        if(dto.getCity() != address.getCity()){
            address.setCity(dto.getCity());
        }

        addressRepository.save(address);

        return new ResponseEntity(address, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getAddressList(){
        List<Address> addressList = addressRepository.findAll();

        return new ResponseEntity(addressList, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAddress(@PathVariable("id") Long id){

        addressRepository.deleteById(id);

        return new ResponseEntity("Address removed", HttpStatus.OK);
    }
}
