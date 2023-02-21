package ru.maynim.astonmvc.repository;

import ru.maynim.astonmvc.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    List<Note> findAll();

    Optional<Note> findById(long id);

    void update(long id, Note note);

    void deleteById(long id);

    void save(Note note);
}
