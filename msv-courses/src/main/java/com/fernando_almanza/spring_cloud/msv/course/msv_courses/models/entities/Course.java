package com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.entities;

import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty()
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers;

    @Transient
    private List<User> users;

    public Course() {
        courseUsers = new ArrayList<>();
        users = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseUser> getCourseUsers() {
        return courseUsers;
    }

    public void setCourseUsers(List<CourseUser> courseUsers) {
        this.courseUsers = courseUsers;
    }

    public void addCourseUser(CourseUser courseUser) {
        courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser) {
        courseUsers.remove(courseUser);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
