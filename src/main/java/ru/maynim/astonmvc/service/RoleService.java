package ru.maynim.astonmvc.service;

import ru.maynim.astonmvc.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAllWithUsers();

    Role findById(long id);

    void update(long id, Role role);

    void deleteById(long id);

    void save(Role role);

    List<Role> findAll();
}
