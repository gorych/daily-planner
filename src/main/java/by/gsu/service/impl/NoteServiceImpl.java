package by.gsu.service.impl;

import by.gsu.model.Note;
import by.gsu.repository.NoteRepository;
import by.gsu.service.NoteService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> getAll() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getSuitableByDateAndTime(LocalDateTime dateTime) {
        return noteRepository.findByStartDateLessOrEqualAndEndDateGreaterOrEqual(dateTime);
    }

    @Override
    public List<Note> getSuitableByDate(LocalDate date) {
        LocalDateTime leftStartDate = LocalDateTime.of(date, LocalTime.of(0, 0));
        LocalDateTime rightStartDate = LocalDateTime.of(date, LocalTime.of(23, 59));
        return noteRepository.findByLeftAndRightStartDates(leftStartDate, rightStartDate);
    }

}