package ru.maynim.astonmvc.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.model.File;
import ru.maynim.astonmvc.model.Note;
import ru.maynim.astonmvc.repository.FileRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FileRepositoryJdbcImpl implements FileRepository {

    private final Connection connection;

    @Override
    public List<File> findAll() {
        List<File> findFileList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetOfFiles = statement.executeQuery(
                    "SELECT f.id, f.name, f.url, n.id as note_id, n.name as note_name " +
                            "FROM aston_trainee.dat_files f " +
                            "JOIN aston_trainee.dat_notes n ON f.dat_notes_id = n.id"
            );
            while (resultSetOfFiles.next()) {
                File file = File.builder()
                        .id(resultSetOfFiles.getLong("id"))
                        .name(resultSetOfFiles.getString("name"))
                        .url(resultSetOfFiles.getString("url"))
                        .note(Note.builder()
                                .id(resultSetOfFiles.getLong("note_id"))
                                .name(resultSetOfFiles.getString("note_name"))
                                .build())
                        .build();

                findFileList.add(file);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findFileList;
    }

    @Override
    public Optional<File> findById(long id) {
        File findFile;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT f.id, f.name, f.url, n.id as note_id, n.name as note_name " +
                            "FROM aston_trainee.dat_files f " +
                            "JOIN aston_trainee.dat_notes n ON f.dat_notes_id = n.id " +
                            "WHERE f.id = ?"
            );
            preparedStatement.setLong(1, id);

            ResultSet resultSetOfFile = preparedStatement.executeQuery();
            resultSetOfFile.next();

            findFile = File.builder()
                    .id(resultSetOfFile.getLong("id"))
                    .name(resultSetOfFile.getString("name"))
                    .url(resultSetOfFile.getString("url"))
                    .note(Note.builder()
                            .id(resultSetOfFile.getLong("note_id"))
                            .name(resultSetOfFile.getString("note_name"))
                            .build())
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(findFile);
    }

    @Override
    public void update(long id, File file) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE aston_trainee.dat_files " +
                            "SET name = ?, url = ? " +
                            "WHERE id = ?"
            );
            preparedStatement.setString(1, file.getName());
            preparedStatement.setString(2, file.getUrl());
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
                    "DELETE FROM aston_trainee.dat_files WHERE id = ?"
            );
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(File file) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO aston_trainee.dat_files (name, url, dat_notes_id) " +
                            "VALUES(?, ?, ?)"
            );
            preparedStatement.setString(1, file.getName());
            preparedStatement.setString(2, file.getUrl());
            preparedStatement.setLong(3, file.getNote().getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<File> findAllByNoteId(long id) {
        List<File> findFileList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetOfFiles = statement.executeQuery(
                    "SELECT f.id, f.name, f.url, n.id as note_id, n.name as note_name " +
                            "FROM aston_trainee.dat_files f " +
                            "JOIN aston_trainee.dat_notes n ON f.dat_notes_id = n.id " +
                            "WHERE f.dat_notes_id = n.id"
            );
            while (resultSetOfFiles.next()) {
                File file = File.builder()
                        .id(resultSetOfFiles.getLong("id"))
                        .name(resultSetOfFiles.getString("name"))
                        .url(resultSetOfFiles.getString("url"))
                        .note(Note.builder()
                                .id(resultSetOfFiles.getLong("note_id"))
                                .name(resultSetOfFiles.getString("note_name"))
                                .build())
                        .build();

                findFileList.add(file);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findFileList;
    }
}
