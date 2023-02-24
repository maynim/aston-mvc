package ru.maynim.astonmvc.repository.impl.hibernate;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import ru.maynim.astonmvc.entity.File;
import ru.maynim.astonmvc.repository.FileRepository;

import java.util.ArrayList;
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
    public void update(long id, File file) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            File fileToUpdate = session.get(File.class, id);
            fileToUpdate.setName(file.getName());
            fileToUpdate.setUrl(file.getUrl());
            session.update(fileToUpdate);

            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createQuery("DELETE File f WHERE f.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void save(File file) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(file);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<File> findAllByNoteId(long id) {
        List<File> findFileList = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            findFileList = session.createQuery(
                            "select f from File f join fetch f.note where f.note.id = :id",
                            File.class
                    )
                    .setParameter("id", id)
                    .getResultList();

            session.getTransaction().commit();
        }
        return findFileList;
    }
}
