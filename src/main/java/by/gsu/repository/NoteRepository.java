package by.gsu.repository;


import by.gsu.model.Note;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteRepository extends CrudRepository<Note> {

    List<Note> findByStartDateLessOrEqualAndEndDateGreaterOrEqual(LocalDateTime date);

    List<Note> findByLeftAndRightStartDates(LocalDateTime startDateLeft, LocalDateTime startDateRight);

}
