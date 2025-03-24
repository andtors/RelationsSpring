package com.example.relational_test.domain.entity.controller;

import com.example.relational_test.domain.entity.Address;
import com.example.relational_test.domain.entity.Course;
import com.example.relational_test.domain.entity.dto.AddressDTO;
import com.example.relational_test.domain.entity.dto.CourseDTO;
import com.example.relational_test.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return new ResponseEntity(course, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity getCourse(@PathVariable("id") Long id){
        return courseRepository.findById(id)
                .map(courseMap -> new ResponseEntity(courseMap, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity("Erro", HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}")
    public ResponseEntity updateCourse(@PathVariable("id")  Long id,@RequestBody CourseDTO dto){
        Course course = courseRepository.findById(id)
                .orElseGet(null);

        if(dto.getName() != course.getName()) {
            course.setName(dto.getName());
        }
        courseRepository.save(course);

        return new ResponseEntity(course, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getCourseList(){
        List<Course> courseList = courseRepository.findAll();

        return new ResponseEntity(courseList, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCourse(@PathVariable("id") Long id){

        courseRepository.deleteById(id);

        return new ResponseEntity("Course removed", HttpStatus.OK);
    }
}
