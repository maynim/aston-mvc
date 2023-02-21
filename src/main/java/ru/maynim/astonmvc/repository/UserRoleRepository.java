package ru.maynim.astonmvc.repository;

import ru.maynim.astonmvc.model.User;

public interface UserRoleRepository {

    void delete(long userId, long roleId);

    void save(User user);
}
