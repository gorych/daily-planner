package by.gsu.service.impl;

import by.gsu.model.Note;
import by.gsu.repository.NoteRepository;
import by.gsu.service.NoteService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> getSuitableByDateAndTimeAndSortedByStartDate(LocalDateTime dateTime) {
        return getSortedByStartDate(
                () -> noteRepository.findByStartDateLessOrEqualAndEndDateGreaterOrEqual(dateTime));
    }

    @Override
    public List<Note> getSuitableByDateAndSortedByStartDate(LocalDate date) {
        LocalDateTime leftStartDate = LocalDateTime.of(date, LocalTime.of(0, 0));
        LocalDateTime rightStartDate = LocalDateTime.of(date, LocalTime.of(23, 59));
        return getSortedByStartDate(
                () -> noteRepository.findByLeftAndRightStartDates(leftStartDate, rightStartDate));
    }

    @Override
    public void add(Note note) {
        noteRepository.add(note);
    }

    private List<Note> getSortedByStartDate(Supplier<List<Note>> notesSupplier) {
        return notesSupplier.get().stream()
                .sorted(Comparator.comparing(Note::getStartDate))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(List<Note> notes) {
        noteRepository.delete(notes);
    }

}