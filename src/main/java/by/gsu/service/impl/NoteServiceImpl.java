package by.gsu.service.impl;

import by.gsu.model.Note;
import by.gsu.repository.NoteRepository;
import by.gsu.service.NoteService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> getAll() {
        return noteRepository.findAll();
    }

}