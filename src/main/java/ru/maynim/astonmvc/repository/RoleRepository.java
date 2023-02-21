package ru.maynim.astonmvc.repository;

import ru.maynim.astonmvc.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<Role> findAll();

    Optional<Role> findById(long id);

    void update(long id, Role role);

    void deleteById(long id);

    void save(Role role);
}
