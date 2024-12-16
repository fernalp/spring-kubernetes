package com.fernando_almanza.spring_cloud.msv.course.msv_courses.services;

import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.User;
import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findALl();
    Optional<Course> findById(Long id);
    Optional<Course> findByIdWithUsers(Long id);
    Course save(Course course);
    void deleteById(Long id);
    void deleteCourseUserByUserId(Long userId);
    Optional<User> addUser(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> removeUser(User user, Long courseId);
}
