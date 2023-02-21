package ru.maynim.astonmvc.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.model.Role;
import ru.maynim.astonmvc.model.User;
import ru.maynim.astonmvc.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryJdbcImpl implements UserRepository {

    private final Connection connection;

    @Override
    public List<User> findAll() {
        List<User> findUserList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetOfUsers = statement.executeQuery("SELECT * FROM aston_trainee.dat_users");

            while (resultSetOfUsers.next()) {
                User user = User.builder()
                        .id(resultSetOfUsers.getLong("id"))
                        .email(resultSetOfUsers.getString("email"))
                        .username(resultSetOfUsers.getString("username"))
                        .birthDate(resultSetOfUsers.getDate("birth_date").toLocalDate())
                        .firstName(resultSetOfUsers.getString("first_name"))
                        .lastName(resultSetOfUsers.getString("last_name"))
                        .build();

                findUserList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findUserList;
    }

    @Override
    public Optional<User> findById(long id) {
        User findUser;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM aston_trainee.dat_users WHERE id = ?"
            );
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            findUser = User.builder()
                    .id(resultSet.getLong("id"))
                    .email(resultSet.getString("email"))
                    .username(resultSet.getString("username"))
                    .birthDate(resultSet.getDate("birth_date").toLocalDate())
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(findUser);
    }

    @Override
    public void update(long id, User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE aston_trainee.dat_users\n" +
                            "SET email = ?, username = ?, birth_date = ?, first_name = ?, last_name = ?\n" +
                            "WHERE id = ?\n"
            );
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setLong(6, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM aston_trainee.dat_users WHERE id = ?"
            );
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO aston_trainee.dat_users (email, username, birth_date, first_name, last_name) " +
                            "VALUES(?,?,?,?,?)"
            );
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setDate(3, Date.valueOf(user.getBirthDate()));
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAllWithRoles() {
        List<User> findUserList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetOfUsers = statement.executeQuery(
                    "SELECT u.id as user_id, u.username, r.id as role_id, r.name FROM aston_trainee.dat_users u " +
                            "INNER JOIN aston_trainee.lnk_dat_users_dic_roles lnk ON u.id = lnk.dat_users_id " +
                            "INNER JOIN aston_trainee.dic_roles r on r.id = lnk.dic_roles_id");
            while (resultSetOfUsers.next()) {
                Role role = Role.builder()
                        .id(resultSetOfUsers.getLong("role_id"))
                        .name(resultSetOfUsers.getString("name"))
                        .build();
                User user = User.builder()
                        .id(resultSetOfUsers.getLong("user_id"))
                        .username(resultSetOfUsers.getString("username"))
                        .roles(List.of(role))
                        .build();
                findUserList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findUserList;
    }

    @Override
    public Optional<User> findByIdWithRoles(long id) {
        User findUser = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT u.*, r.name FROM aston_trainee.dat_users u " +
                            "LEFT JOIN aston_trainee.lnk_dat_users_dic_roles lnk ON u.id = lnk.dat_users_id " +
                            "LEFT JOIN aston_trainee.dic_roles r ON r.id = lnk.dic_roles_id " +
                            "WHERE u.id = ?"
            );
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (findUser == null) {
                    findUser = User.builder()
                            .id(resultSet.getLong("id"))
                            .email(resultSet.getString("email"))
                            .username(resultSet.getString("username"))
                            .birthDate(resultSet.getDate("birth_date").toLocalDate())
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .build();
                }
                findUser.addRole(Role.builder()
                        .name(resultSet.getString("name"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(findUser);
    }

    @Override
    public void addRole(long id, Role role) {
        try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO aston_trainee.lnk_dat_users_dic_roles (dat_users_id, dic_roles_id) " +
                                "VALUES (?, ?)"
                );
                preparedStatement.setLong(1, id);
                preparedStatement.setLong(2, role.getId());

                preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
