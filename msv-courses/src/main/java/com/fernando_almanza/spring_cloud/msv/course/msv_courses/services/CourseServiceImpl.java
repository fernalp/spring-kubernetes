package com.fernando_almanza.spring_cloud.msv.course.msv_courses.services;

import com.fernando_almanza.spring_cloud.msv.course.msv_courses.clients.UserClientRest;
import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.User;
import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.entities.Course;
import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.entities.CourseUser;
import com.fernando_almanza.spring_cloud.msv.course.msv_courses.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final UserClientRest userClient;

    public CourseServiceImpl(CourseRepository courseRepository, UserClientRest userClient) {
        this.courseRepository = courseRepository;
        this.userClient = userClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findALl() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findByIdWithUsers(Long id) {
        Optional<Course> oCourse = courseRepository.findById(id);
        if (oCourse.isPresent()) {
            Course course = oCourse.get();
            if (!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers().stream().map(CourseUser::getUserId).toList();
                List<User> users = userClient.getUsersCourses(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteCourseUserByUserId(Long userId) {
        courseRepository.deleteCourseUsersByUserId(userId);
    }

    @Override
    @Transactional
    public Optional<User> addUser(User user, Long courseId) {
        Optional<Course> oCourse = courseRepository.findById(courseId);
        if (oCourse.isPresent()) {
            User userMsv = userClient.getUser(user.getId());
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsv.getId());
            Course course = oCourse.get();
            course.addCourseUser(courseUser);
            courseRepository.save(course);
            // TODO: Return
            return Optional.of(userMsv);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> oCourse = courseRepository.findById(courseId);
        if (oCourse.isPresent()) {
            User newUser = userClient.createUser(user);
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(newUser.getId());
            Course course = oCourse.get();
            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(newUser);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUser(User user, Long courseId) {
        Optional<Course> oCourse = courseRepository.findById(courseId);
        if (oCourse.isPresent()) {
            User userMsv = userClient.getUser(user.getId());
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsv.getId());
            Course course = oCourse.get();
            course.removeCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMsv);
        }
        return Optional.empty();
    }



}
