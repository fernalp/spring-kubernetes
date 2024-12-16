package com.fernando_almanza.spring_cloud.msv.user.msv_users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "msv-courses", url="http://host.docker.internal:8002")
@FeignClient(name = "msv-courses", url="msv-courses:8002")
public interface CourseClientRest {

    @DeleteMapping("/delete-user/{id}")
    void deleteUser(@PathVariable Long id);

}
