package com.fernando_almanza.spring_cloud.msv.course.msv_courses.clients;

import com.fernando_almanza.spring_cloud.msv.course.msv_courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msv-users", url = "msv-users:8001")
public interface UserClientRest {

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id);

    @PostMapping("/")
    public User createUser(@RequestBody User user);

    @GetMapping("/users-courses")
    public List<User> getUsersCourses(@RequestParam Iterable<Long> ids);

}