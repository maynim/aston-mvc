package ru.maynim.astonmvc.repository;

import org.springframework.stereotype.Repository;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(long id);

    void update(long id, User user);

    void deleteById(long id);

    void save(User user);

    List<User> findAllWithRoles();

    Optional<User> findByIdWithRoles(long id);

    void addRole(long id, Role role);

    void deleteRole(long userId, long roleId);
}
