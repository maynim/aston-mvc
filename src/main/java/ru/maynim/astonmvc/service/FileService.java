package ru.maynim.astonmvc.service;

import ru.maynim.astonmvc.entity.File;

import java.util.List;

public interface FileService {
    List<File> findAllWithNotes();

    File findById(long id);

    void update(long id, File file);

    void deleteById(long id);

    void save(File file);

    List<File> findAllByNoteId(long id);
}
