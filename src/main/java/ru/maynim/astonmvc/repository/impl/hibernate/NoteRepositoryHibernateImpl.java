package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.Note;
import ru.maynim.astonmvc.entity.User;
import ru.maynim.astonmvc.repository.NoteRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NoteRepositoryHibernateImpl implements NoteRepository {

    private final Connection connection;

    @Override
    public List<Note> findAll() {
        List<Note> findNoteList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetOfNotes = statement.executeQuery(
                    "SELECT n.id, n.name, n.content, u.id as user_id, u.username FROM aston_trainee.dat_notes n " +
                            "JOIN aston_trainee.dat_users u ON n.dat_users_id = u.id"
            );
            while (resultSetOfNotes.next()) {
                Note note = Note.builder()
                        .id(resultSetOfNotes.getLong("id"))
                        .name(resultSetOfNotes.getString("name"))
                        .content(resultSetOfNotes.getString("content"))
                        .user(User.builder()
                                .id(resultSetOfNotes.getLong("user_id"))
                                .username(resultSetOfNotes.getString("username"))
                                .build())
                        .build();

                findNoteList.add(note);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findNoteList;
    }

    @Override
    public Optional<Note> findById(long id) {
        Note findNote;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT n.id, n.name, n.content, u.id as user_id, u.username FROM aston_trainee.dat_notes n " +
                            "JOIN aston_trainee.dat_users u ON n.dat_users_id = u.id " +
                            "WHERE n.id = ?"
            );
            preparedStatement.setLong(1, id);

            ResultSet resultSetOfNote = preparedStatement.executeQuery();
            resultSetOfNote.next();

            findNote = Note.builder()
                    .id(resultSetOfNote.getLong("id"))
                    .name(resultSetOfNote.getString("name"))
                    .content(resultSetOfNote.getString("content"))
                    .user(User.builder()
                            .id(resultSetOfNote.getLong("user_id"))
                            .username(resultSetOfNote.getString("username"))
                            .build())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(findNote);
    }

    @Override
    public void update(long id, Note note) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE aston_trainee.dat_notes " +
                            "SET name = ?, content = ? " +
                            "WHERE id = ?"
            );
            preparedStatement.setString(1, note.getName());
            preparedStatement.setString(2, note.getContent());
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
                    "DELETE FROM aston_trainee.dat_notes WHERE id = ?"
            );
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Note note) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO aston_trainee.dat_notes (name, content, dat_users_id) " +
                            "VALUES(?, ?, ?)"
            );
            preparedStatement.setString(1, note.getName());
            preparedStatement.setString(2, note.getContent());
            preparedStatement.setLong(3, note.getUser().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
