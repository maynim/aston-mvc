package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.Note;
import ru.maynim.astonmvc.repository.NoteRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NoteRepositoryHibernateImpl implements NoteRepository {

    private final Connection connection;
    private final SessionFactory sessionFactory;

    @Override
    public List<Note> findAllWithUsers() {
        List<Note> findNoteList;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findNoteList = session.createQuery("select n from Note n join fetch n.user", Note.class)
                    .getResultList();

            session.getTransaction().commit();
        }

        return findNoteList;
    }

    @Override
    public Optional<Note> findById(long id) {
        Note findNote;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findNote = session.get(Note.class, id);

            session.getTransaction().commit();
        }

        return Optional.ofNullable(findNote);
    }

    @Override
    public void update(long id, Note note) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Note noteToUpdate = session.get(Note.class, id);
            noteToUpdate.setName(note.getName());
            noteToUpdate.setContent(note.getContent());
            session.update(noteToUpdate);

            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createQuery("DELETE Note n WHERE n.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void save(Note note) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(note);

            session.getTransaction().commit();
        }
    }
}
