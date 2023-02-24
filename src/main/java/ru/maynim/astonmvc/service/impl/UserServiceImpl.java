package ru.maynim.astonmvc.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.entity.User;
import ru.maynim.astonmvc.repository.UserRepository;
import ru.maynim.astonmvc.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User with ID: " + id + " was not found");
        });

        log.debug("Found user: {}", foundUser);

        return foundUser;
    }

    @Override
    public void update(long id, User user) {
        int updatedRows = userRepository.update(id, user);
        if (updatedRows == 0) {
            throw new EntityNotFoundException("User with ID: " + id + " was not found");
        } else {
            log.debug("User with ID: {} was updated to User: {}", id, user);
        }
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findAllWithRoles() {
        return userRepository.findAllWithRoles();
    }

    @Override
    public User findByIdWithRoles(long id) {
        User foundUser = userRepository.findByIdWithRoles(id).orElseThrow(() -> {
            throw new EntityNotFoundException("User with ID: " + id + " was not found");
        });

        log.debug("Found user: {}", foundUser);

        return foundUser;
    }

    @Override
    public void addRole(long id, Role role) {
        userRepository.addRole(id, role);
    }

    @Override
    public void deleteRole(long userId, long roleId) {
        userRepository.deleteRole(userId, roleId);
    }
}
