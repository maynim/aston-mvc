package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.User;
import ru.maynim.astonmvc.repository.UserRoleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class UserRoleRepositoryHibernateImpl implements UserRoleRepository {

    private final Connection connection;

    @Override
    public void delete(long userId, long roleId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM aston_trainee.lnk_dat_users_dic_roles " +
                            "WHERE dat_users_id = ? and dic_roles_id = ?"
            );
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, roleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO aston_trainee.lnk_dat_users_dic_roles (dat_users_id, dic_roles_id) " +
                            "VALUES(?,?)"
            );
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, user.getRoles().get(0).getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
