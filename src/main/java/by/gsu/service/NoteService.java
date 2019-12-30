package by.gsu.service;

import by.gsu.model.Note;

import java.time.LocalDate;
import java.util.List;

public interface NoteService {

    List<Note> getSuitableForTodayAndSortedByStartDate(LocalDate date);

    Note add(Note note);

    void delete(List<Note> notes);

}
