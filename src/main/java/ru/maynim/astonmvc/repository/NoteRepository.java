package ru.maynim.astonmvc.repository;

import ru.maynim.astonmvc.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    List<Note> findAllWithUsers();

    Optional<Note> findById(long id);

    int update(long id, Note note);

    void deleteById(long id);

    void save(Note note);
}
