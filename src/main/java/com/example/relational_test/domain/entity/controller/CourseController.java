package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.Course;
import com.example.relational_test.domain.entity.dto.CourseDTO;
import com.example.relational_test.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CourseDTO dto){

        Course course = new Course(dto.getName());

        courseRepository.save(course);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity getCourse(@PathVariable("id") Long id){
        return courseRepository.findById(id)
                .map(courseMap -> new ResponseEntity(courseMap, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Erro", HttpStatus.NOT_FOUND));
    }
}
