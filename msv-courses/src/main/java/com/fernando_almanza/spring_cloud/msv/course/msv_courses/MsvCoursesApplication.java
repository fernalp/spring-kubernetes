package com.fernando_almanza.spring_cloud.msv.course.msv_courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvCoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvCoursesApplication.class, args);
	}

}
