package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.Course;
import com.example.relational_test.domain.entity.Registration;
import com.example.relational_test.domain.entity.User;
import com.example.relational_test.domain.entity.dto.RegistrationDTO;
import com.example.relational_test.repository.CourseRepository;
import com.example.relational_test.repository.RegistrationRepository;
import com.example.relational_test.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        System.out.println(course.getName());
        System.out.println(user.getName());

        Registration registration = new Registration(user, course);

        registrationRepository.save(registration);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity getRegistration(@PathVariable("id") Long id){

        return registrationRepository.findById(id)
                .map(registration ->  new ResponseEntity(registration, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Erro", HttpStatus.NOT_FOUND));

    }
}
