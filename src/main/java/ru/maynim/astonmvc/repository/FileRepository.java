package ru.maynim.astonmvc.repository;

import ru.maynim.astonmvc.model.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {

    List<File> findAll();

    Optional<File> findById(long id);

    void update(long id, File file);

    void deleteById(long id);

    void save(File file);

    List<File> findAllByNoteId(long id);
}
