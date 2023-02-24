package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.entity.User;
import ru.maynim.astonmvc.repository.UserRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryHibernateImpl implements UserRepository {

    private final Connection connection;
    private final SessionFactory sessionFactory;

    @Override
    public List<User> findAll() {
        List<User> findUserList;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findUserList = session.createQuery("select u from User u", User.class)
                    .getResultList();

            session.getTransaction().commit();
        }
        return findUserList;
    }

    @Override
    public Optional<User> findById(long id) {
        User findUser;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findUser = session.get(User.class, id);

            session.getTransaction().commit();
        }

        return Optional.ofNullable(findUser);
    }

    @Override
    public void update(long id, User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User userToUpdate = session.get(User.class, id);
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setBirthDate(user.getBirthDate());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            session.update(userToUpdate);

            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createQuery("DELETE User u WHERE u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> findAllWithRoles() {
        List<User> findUserList;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findUserList = session.createQuery("select г from User г join fetch г.roles", User.class)
                    .getResultList();

            session.getTransaction().commit();
        }

        return findUserList;
    }

    @Override
    public Optional<User> findByIdWithRoles(long id) {
        User findUser;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findUser = session.createQuery("select u from User u left join fetch u.roles where u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();

            session.getTransaction().commit();
        }

        return Optional.ofNullable(findUser);
    }

    @Override
    public void addRole(long id, Role role) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User userForAddNewRole = session.get(User.class, id);
            userForAddNewRole.addRole(role);

            session.update(userForAddNewRole);

            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteRole(long userId, long roleId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User userWithRoles = session.createQuery(
                            "select u from User u left join fetch u.roles where u.id = :id",
                            User.class
                    )
                    .setParameter("id", userId)
                    .getSingleResult();

            Role roleForRemove = session.get(Role.class, roleId);

            userWithRoles.removeRole(roleForRemove);

            session.getTransaction().commit();
        }
    }


}
