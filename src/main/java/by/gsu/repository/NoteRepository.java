package by.gsu.repository;


import by.gsu.model.Note;

import java.time.LocalDateTime;
import java.util.List;

public interface NoteRepository extends CrudRepository<Note> {

    List<Note> findSuitableNotesByMinAndMaxDateTime(LocalDateTime minDateTime, LocalDateTime maxDateTime);

}
