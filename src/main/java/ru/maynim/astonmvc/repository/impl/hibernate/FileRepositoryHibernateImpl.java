package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.File;
import ru.maynim.astonmvc.repository.FileRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FileRepositoryHibernateImpl implements FileRepository {

    private final SessionFactory sessionFactory;

    @Override
    public List<File> findAllWithNotes() {
        List<File> findFileList;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findFileList = session.createQuery("SELECT f FROM File f JOIN FETCH f.note", File.class)
                    .getResultList();

            session.getTransaction().commit();
        }

        return findFileList;
    }

    @Override
    public Optional<File> findById(long id) {
        File findFile;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findFile = session.get(File.class, id);

            session.getTransaction().commit();
        }

        return Optional.ofNullable(findFile);
    }

    @Override
    public int update(long id, File file) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                int updatedRows = session.createQuery(
                                "update File f " +
                                        "set name = :name, " +
                                        "url = :url " +
                                        "where f.id = :id"
                        )
                        .setParameter("name", file.getName())
                        .setParameter("url", file.getUrl())
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

                session.createQuery("DELETE File f WHERE f.id = :id")
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
    public void save(File file) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                session.save(file);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
    }

    @Override
    public List<File> findAllByNoteId(long id) {
        List<File> findFileList;
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                findFileList = session.createQuery(
                                "select f from File f join fetch f.note where f.note.id = :id",
                                File.class
                        )
                        .setParameter("id", id)
                        .getResultList();

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();

                throw e;
            }
        }
        return findFileList;
    }
}
