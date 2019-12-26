package by.gsu.repository.impl;

import by.gsu.domain.tables.records.NoteRecord;
import by.gsu.model.Note;
import by.gsu.repository.ConnectionHolder;
import by.gsu.repository.NoteRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static by.gsu.domain.tables.Note.NOTE;
import static by.gsu.util.DateTimeUtil.convertToBigDecimal;

public class NoteRepositoryImpl implements NoteRepository {

    private static final DSLContext DSL_CONTEXT = DSL.using(ConnectionHolder.getConnection(), SQLDialect.SQLITE);

    @Override
    public List<Note> findAll() {
        return DSL_CONTEXT.select()
                .from(NOTE)
                .fetch()
                .map(Note::new);
    }

    @Override
    public Optional<Note> findById(int id) {
        Note note = DSL_CONTEXT.select()
                .from(NOTE)
                .where(NOTE.ID.eq(id))
                .fetchOne()
                .map(Note::new);

        return Optional.of(note);
    }

    @Override
    public Note add(Note note) {
        NoteRecord noteRecord = DSL_CONTEXT.newRecord(NOTE);
        return saveOrUpdate(note, noteRecord);
    }

    @Override
    public void update(Note note) {
        NoteRecord noteRecord = DSL_CONTEXT.fetchOne(NOTE, NOTE.ID.eq(note.getId()));
        saveOrUpdate(note, noteRecord);
    }

    private Note saveOrUpdate(Note note, NoteRecord noteRecord) {
        noteRecord.setName(note.getName());
        noteRecord.setDescription(note.getDescription());
        noteRecord.setStartdate(convertToBigDecimal(note.getStartDate()));
        noteRecord.setEnddate(convertToBigDecimal(note.getEndDate()));
        noteRecord.store();
        return new Note(noteRecord);
    }

    @Override
    public void delete(Note note) {
        DSL_CONTEXT
                .delete(NOTE)
                .where(NOTE.ID.eq(note.getId()))
                .execute();
    }

    @Override
    public void delete(List<Note> notes) {
        notes.forEach(this::delete);
    }

    @Override
    public List<Note> findByStartDateLessOrEqualAndEndDateGreaterOrEqual(LocalDateTime date) {
        return DSL_CONTEXT.select()
                .from(NOTE)
                .where(NOTE.STARTDATE.lessOrEqual(convertToBigDecimal(date))
                        .and(NOTE.ENDDATE.greaterOrEqual(convertToBigDecimal(date))))
                .fetch()
                .map(Note::new);
    }

    @Override
    public List<Note> findByLeftAndRightStartDates(LocalDateTime leftStartDate, LocalDateTime rightStartDate) {
        return DSL_CONTEXT.select()
                .from(NOTE)
                .where(NOTE.STARTDATE.lessOrEqual(convertToBigDecimal(rightStartDate))
                        .and(NOTE.ENDDATE.greaterOrEqual(convertToBigDecimal(leftStartDate)))
                )
                .fetch()
                .map(Note::new);
    }

}