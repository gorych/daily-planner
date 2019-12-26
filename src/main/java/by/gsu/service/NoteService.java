package by.gsu.service;

import by.gsu.model.Note;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface NoteService {

    List<Note> getSuitableByDateAndTimeAndSortedByStartDate(LocalDateTime dateTime);

    List<Note> getSuitableByDateAndSortedByStartDate(LocalDate date);

    Note add(Note note);

    void delete(List<Note> notes);

}
