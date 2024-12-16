package com.fernando_almanza.spring_cloud.msv.user.msv_users.services;

import com.fernando_almanza.spring_cloud.msv.user.msv_users.client.CourseClientRest;
import com.fernando_almanza.spring_cloud.msv.user.msv_users.models.entities.User;
import com.fernando_almanza.spring_cloud.msv.user.msv_users.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CourseClientRest courseClientRest;

    public UserServiceImpl(UserRepository userRepository, CourseClientRest courseClientRest) {
        this.userRepository = userRepository;
        this.courseClientRest = courseClientRest;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.cleanAndTransform();
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        courseClientRest.deleteUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase().trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllById(Iterable<Long> id) {
        return (List<User>) userRepository.findAllById(id);
    }

}
