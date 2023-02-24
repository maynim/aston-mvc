package ru.maynim.astonmvc.service;

import ru.maynim.astonmvc.entity.Note;

import java.util.List;

public interface NoteService {

    List<Note> findAllWithUsers();

    Note findById(long id);

    void update(long id, Note note);

    void deleteById(long id);

    void save(Note note);
}
