package by.gsu.repository.impl;

import by.gsu.model.Note;
import by.gsu.repository.ConnectionHolder;
import by.gsu.repository.NoteRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static by.gsu.domain.tables.Note.NOTE;

public class NoteRepositoryImpl implements NoteRepository {

    @Override
    public List<Note> findAll() {
        return Collections.singletonList(findById(1).get());
    }

    @Override
    public Optional<Note> findById(int id) {
        Connection connection = ConnectionHolder.getConnection();
        DSLContext create = DSL.using(connection, SQLDialect.SQLITE);

        Note note = create.select()
                .from(NOTE)
                .where(NOTE.ID.eq(id))
                .fetchOne()
                .map(Note::new);

        return Optional.of(note);
    }

    @Override
    public void add(Note note) {

    }

    @Override
    public void update(Note note) {

    }

    @Override
    public void delete(Note note) {

    }
}
