package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.Note;
import ru.maynim.astonmvc.repository.NoteRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NoteRepositoryHibernateImpl implements NoteRepository {

    private final SessionFactory sessionFactory;

    @Override
    public List<Note> findAllWithUsers() {
        List<Note> findNoteList;

        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                findNoteList = session.createQuery("select n from Note n join fetch n.user", Note.class)
                        .getResultList();

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
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
    public int update(long id, Note note) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                int updatedRows = session.createQuery(
                                "update Note n " +
                                        "set name = :name, " +
                                        "content = :content " +
                                        "where n.id = :id"
                        )
                        .setParameter("name", note.getName())
                        .setParameter("content", note.getContent())
                        .setParameter("id", id)
                        .executeUpdate();

                session.getTransaction().commit();

                return updatedRows;
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }

    @Override
    public void deleteById(long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                session.createQuery("DELETE Note n WHERE n.id = :id")
                        .setParameter("id", id)
                        .executeUpdate();

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }

    @Override
    public void save(Note note) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                session.save(note);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }
}
