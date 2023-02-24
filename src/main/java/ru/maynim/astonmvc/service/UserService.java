package ru.maynim.astonmvc.service;

import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    void update(long id, User user);

    void deleteById(long id);

    void save(User user);

    List<User> findAllWithRoles();

    User findByIdWithRoles(long id);

    void addRole(long id, Role role);

    void deleteRole(long userId, long roleId);
}
