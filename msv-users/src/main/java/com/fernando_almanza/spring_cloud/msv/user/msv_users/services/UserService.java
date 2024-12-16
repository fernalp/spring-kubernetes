package com.fernando_almanza.spring_cloud.msv.user.msv_users.services;

import com.fernando_almanza.spring_cloud.msv.user.msv_users.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAllById(Iterable<Long> id);
}
