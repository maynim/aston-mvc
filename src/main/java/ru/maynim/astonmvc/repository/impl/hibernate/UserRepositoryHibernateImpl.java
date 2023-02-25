package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.entity.User;
import ru.maynim.astonmvc.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryHibernateImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Override
    public List<User> findAll() {
        List<User> findUserList;

        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                findUserList = session.createQuery("select u from User u", User.class).getResultList();

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
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
    public int update(long id, User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                int updatedRows = session.createQuery(
                                "update User u " +
                                        "set email = :email, " +
                                        "username = :username, " +
                                        "birthDate = :birthDate, " +
                                        "firstName = :firstName, " +
                                        "lastName = :lastName " +
                                        "where u.id = :id"
                        )
                        .setParameter("email", user.getEmail())
                        .setParameter("username", user.getUsername())
                        .setParameter("birthDate", user.getBirthDate())
                        .setParameter("firstName", user.getFirstName())
                        .setParameter("lastName", user.getLastName())
                        .setParameter("id", id)
                        .executeUpdate();

                session.getTransaction().commit();

                return updatedRows;
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }

    @Override
    public void deleteById(long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                session.createQuery("DELETE User u WHERE u.id = :id")
                        .setParameter("id", id)
                        .executeUpdate();

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                session.save(user);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }

    @Override
    public List<User> findAllWithRoles() {
        List<User> findUserList;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findUserList = session.createQuery("select distinct u from User u join fetch u.roles", User.class)
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
                    .uniqueResult();

            session.getTransaction().commit();
        }

        return Optional.ofNullable(findUser);
    }

    @Override
    public void addRole(long id, Role role) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                User userForAddNewRole = session.get(User.class, id);
                userForAddNewRole.addRole(role);

                session.update(userForAddNewRole);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }

    @Override
    public void deleteRole(long userId, long roleId) {
        try (Session session = sessionFactory.openSession()) {
            try {
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
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }


}
