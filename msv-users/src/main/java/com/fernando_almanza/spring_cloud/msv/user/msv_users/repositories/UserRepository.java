package com.fernando_almanza.spring_cloud.msv.user.msv_users.repositories;

import com.fernando_almanza.spring_cloud.msv.user.msv_users.models.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email=?1")
    Optional<User> takeByEmail(String email);

    @Query("select u from User u where u.email=?1 and u.password=?2")
    Optional<User> authenticate(String email, String password);
}
