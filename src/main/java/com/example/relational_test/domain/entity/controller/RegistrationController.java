package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.Course;
import com.example.relational_test.domain.entity.Registration;
import com.example.relational_test.domain.entity.User;
import com.example.relational_test.domain.entity.dto.CourseDTO;
import com.example.relational_test.domain.entity.dto.RegistrationDTO;
import com.example.relational_test.repository.CourseRepository;
import com.example.relational_test.repository.RegistrationRepository;
import com.example.relational_test.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationRepository registrationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public RegistrationController(RegistrationRepository registrationRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.registrationRepository = registrationRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody RegistrationDTO dto){

        Course course = courseRepository.findById(dto.getCourse_id())
                .orElseGet(null);

        User user = userRepository.findById(dto.getUser_id())
                .orElseGet(null);

        Registration registration = new Registration(user, course);

        registrationRepository.save(registration);

        return new ResponseEntity(registration, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity getRegistration(@PathVariable("id") Long id){

        return registrationRepository.findById(id)
                .map(registration ->  new ResponseEntity(registration, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Erro", HttpStatus.NOT_FOUND));

    }

    @PutMapping("{id}")
    public ResponseEntity updateRegistration(@PathVariable("id")  Long id,@RequestBody RegistrationDTO dto){
        Registration registration = registrationRepository.findById(id)
                .orElseGet(null);

        Course course = courseRepository.findById(dto.getCourse_id())
                .orElseGet(null);

        if(dto.getCourse_id() != registration.getCourse().getId()) {
            registration.setCourse(course);
        }

        registrationRepository.save(registration);

        return new ResponseEntity(registration, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getCourseList(){
        List<Registration> registrationsList = registrationRepository.findAll();

        return new ResponseEntity(registrationsList, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteRegistration(@PathVariable("id") Long id){

        registrationRepository.deleteById(id);

        return new ResponseEntity("Registration removed", HttpStatus.OK);
    }
}
