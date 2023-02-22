package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.Role;
import ru.maynim.astonmvc.repository.RoleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleRepositoryHibernateImpl implements RoleRepository {

    private final Connection connection;

    @Override
    public List<Role> findAll() {
        List<Role> findRoleList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetOfRoles = statement.executeQuery("SELECT * FROM aston_trainee.dic_roles");
            while (resultSetOfRoles.next()) {
                Role role = Role.builder()
                        .id(resultSetOfRoles.getLong("id"))
                        .name(resultSetOfRoles.getString("name"))
                        .description(resultSetOfRoles.getString("description"))
                        .build();

                findRoleList.add(role);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findRoleList;
    }

    @Override
    public Optional<Role> findById(long id) {
        Role findRole;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM aston_trainee.dic_roles WHERE id = ?"
            );
            preparedStatement.setLong(1, id);

            ResultSet resultSetOfRole = preparedStatement.executeQuery();
            resultSetOfRole.next();

            findRole = Role.builder()
                    .id(resultSetOfRole.getLong("id"))
                    .name(resultSetOfRole.getString("name"))
                    .description(resultSetOfRole.getString("description"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(findRole);
    }

    @Override
    public void update(long id, Role role) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE aston_trainee.dic_roles\n" +
                            "SET name = ?, description = ?" +
                            "WHERE id = ?"
            );
            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, role.getDescription());
            preparedStatement.setLong(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM aston_trainee.dic_roles WHERE id = ?"
            );
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Role role) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO aston_trainee.dic_roles (name, description) " +
                            "VALUES(?,?)"
            );
            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, role.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
