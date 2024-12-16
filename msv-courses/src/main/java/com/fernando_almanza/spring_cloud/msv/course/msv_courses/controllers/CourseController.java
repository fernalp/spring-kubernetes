package com.fernando_almanza.spring_cloud.msv.course.msv_courses.controllers;

import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.User;
import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.entities.Course;
import com.fernando_almanza.spring_cloud.msv.course.msv_courses.services.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.findALl();
        if (courses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.findByIdWithUsers(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validErrors(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @Valid @RequestBody Course updatedCourse, BindingResult result) {
        if (result.hasErrors()) {
            return validErrors(result);
        }
        return courseService.findById(id)
                .map(course -> {
                    course.setName(updatedCourse.getName());
                    return ResponseEntity.ok(courseService.save(course));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        return courseService.findById(id)
                .map(course -> {
                    courseService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/assign-user")
    public ResponseEntity<?> assignUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = null;
        try {
            userOptional = courseService.addUser(user, id);

        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap(
                    "error: ", "Don't exist user with this id or error in the communication"
            ));
        }
            if (userOptional.isPresent()){
                return ResponseEntity.ok(userOptional.get());
            }
            return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/create-user")
    public ResponseEntity<?> createUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = null;
        try {
            userOptional = courseService.createUser(user, id);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap(
                    "error: ", "Don't exist user with this id or error in the communication"
            ));
        }
        if (userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/delete-user")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional;
        try {
            userOptional = courseService.removeUser(user, id);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap(
                    "error: ", "Don't exist user with this id or error in the communication"
            ));
        }
        if (userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete-user-courses/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        courseService.deleteCourseUserByUserId(id);
        return ResponseEntity.noContent().build();
    }


    private static ResponseEntity<Map<String, String>> validErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<String, String>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(), "El campo "+ error.getField() + " " + error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
