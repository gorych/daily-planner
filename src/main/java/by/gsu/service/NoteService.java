package by.gsu.service;

import by.gsu.model.Note;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface NoteService {

    List<Note> getAll();

    List<Note> getSuitableByDateAndTime(LocalDateTime dateTime);

    List<Note> getSuitableByDate(LocalDate date);

}
