package by.gsu.controller;

import by.gsu.model.Note;
import by.gsu.repository.impl.NoteRepositoryImpl;
import by.gsu.service.NoteService;
import by.gsu.service.impl.NoteServiceImpl;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

@Slf4j
public class AddNewNoteDialogController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private JFXTextField noteName;

    @FXML
    private JFXTextField noteDescription;

    @FXML
    private JFXDatePicker noteStartDate;

    @FXML
    private JFXTimePicker noteStartTime;

    @FXML
    private JFXDatePicker noteEndDate;

    @FXML
    private JFXTimePicker noteEndTime;

    private NoteService noteService;

    public AddNewNoteDialogController() {
        this.noteService = new NoteServiceImpl(new NoteRepositoryImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDateTime endLocalDateTime = LocalDateTime.now().plusHours(24);

        this.noteStartDate.setValue(LocalDate.now());
        this.noteStartTime.setValue(LocalTime.now());
        this.noteEndDate.setValue(endLocalDateTime.toLocalDate());
        this.noteEndTime.setValue(endLocalDateTime.toLocalTime());
    }

    public void cleanFieldValues() {
        root.getChildren().stream()
                .filter(node -> node instanceof JFXTextField || node instanceof JFXDatePicker || node instanceof JFXTimePicker)
                .forEach(node -> {
                    if (node instanceof JFXTextField) {
                        ((JFXTextField) node).clear();
                    } else {
                        ((ComboBoxBase) node).setValue(null);
                    }
                });
    }

    public void saveNewNote() {
        noteService.add(new Note.Builder()
                .setName(noteName.getText())
                .setDescription(noteDescription.getText())
                .setStartDate(noteStartDate.getValue(), noteStartTime.getValue())
                .setEndDate(noteEndDate.getValue(), noteEndTime.getValue())
                .build());
    }

}
