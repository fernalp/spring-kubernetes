package com.fernando_almanza.spring_cloud.msv.user.msv_users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvUsersApplication.class, args);
	}

}
