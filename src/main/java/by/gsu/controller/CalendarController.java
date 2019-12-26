package by.gsu.controller;

import by.gsu.model.CalendarCellNode;
import by.gsu.model.Note;
import by.gsu.repository.impl.NoteRepositoryImpl;
import by.gsu.service.NoteService;
import by.gsu.service.impl.NoteServiceImpl;
import by.gsu.util.DialogUtil;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRippler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.Month.*;

@Slf4j
public class CalendarController implements Initializable {

    private static final int WEEKS_COUNT = 6;
    private static final int DAYS_COUNT = 7;

    private static final String ANOTHER_MONTH_DAYS_CSS = "another-month-days";
    private static final String SELECTED_DATE_CSS = "selected-date";
    private static final String CURRENT_DATE_CSS = "current-date";
    private static final String CHECKED_CSS = "checked";

    private static final String DELETE_DIALOG_TITLE = "Delete selected notes";
    private static final String DELETE_DIALOG_BODY = "Are you sure you want to delete selected notes?";
    private static final String WARNING_DIALOG_TITLE = "Warning";
    private static final String WARNING_DIALOG_BODY = "There are no selected notes to delete";

    @FXML
    private AnchorPane root;

    @FXML
    private Label labelYear;

    @FXML
    private Label labelMonth;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView januaryPane;

    @FXML
    private ImageView februaryPane;

    @FXML
    private ImageView marchPane;

    @FXML
    private ImageView aprilPane;

    @FXML
    private ImageView mayPane;

    @FXML
    private ImageView junePane;

    @FXML
    private ImageView julyPane;

    @FXML
    private ImageView augustPane;

    @FXML
    private ImageView septemberPane;

    @FXML
    private ImageView octoberPane;

    @FXML
    private ImageView novemberPane;

    @FXML
    private ImageView decemberPane;

    @FXML
    private GridPane notesPane;

    private int selectedDay;
    private Month selectedMonth;
    private Map<Month, ImageView> monthPanes;
    private ArrayList<CalendarCellNode> calendarCellNodes;

    private NoteService noteService;

    public CalendarController() {
        this.calendarCellNodes = new ArrayList<>();
        this.noteService = new NoteServiceImpl(new NoteRepositoryImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        monthPanes = new EnumMap<>(Month.class);
        monthPanes.put(JANUARY, januaryPane);
        monthPanes.put(FEBRUARY, februaryPane);
        monthPanes.put(MARCH, marchPane);
        monthPanes.put(APRIL, aprilPane);
        monthPanes.put(MAY, mayPane);
        monthPanes.put(JUNE, junePane);
        monthPanes.put(JULY, julyPane);
        monthPanes.put(AUGUST, augustPane);
        monthPanes.put(SEPTEMBER, septemberPane);
        monthPanes.put(OCTOBER, octoberPane);
        monthPanes.put(NOVEMBER, novemberPane);
        monthPanes.put(DECEMBER, decemberPane);

        selectedDay = LocalDate.now().getDayOfMonth();
        selectedMonth = LocalDate.now().getMonth();
        labelMonth.setText(selectedMonth.toString());
        labelYear.setText(String.valueOf(LocalDate.now().getYear()));

        activateMonthPane(LocalDate.now().getMonth());
        buildCalenderCellNodes();

        drawCalenderCells(YearMonth.now());

        rebuildNotesPane(LocalDate.now());
    }

    private void rebuildNotesPane(List<Note> notes) {
        notesPane.getChildren().clear();
        if (notes.isEmpty()) {
            Label label = new Label();
            label.setPadding(new Insets(0, 0, 5, 0));
            label.setText("There are no notes on this date");
            label.setTextFill(Paint.valueOf("#d32f2f"));
            label.setFont(new Font(14));
            notesPane.addRow(1, label);
            return;
        }

        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            JFXCheckBox noteCheckBox = new JFXCheckBox();
            noteCheckBox.setUserData(note);
            noteCheckBox.setText(note.toString());
            noteCheckBox.getStyleClass().add(CHECKED_CSS);
            noteCheckBox.setTextFill(Paint.valueOf("#212121"));
            noteCheckBox.setPadding(new Insets(0, 0, 5, 0));

            Tooltip tooltip = new Tooltip(
                    String.format("[%s-%s] %s", note.getStartDateFormatted(), note.getEndDateFormatted(), note.getDescription()));
            tooltip.setFont(new Font(14));

            noteCheckBox.setTooltip(tooltip);
            notesPane.addRow(i + 2, noteCheckBox);
        }
    }

    private void activateMonthPane(Month currentMonth) {
        monthPanes.keySet().forEach(month -> {
            ImageView imageView = monthPanes.get(month);
            boolean visibility = (month == currentMonth);
            imageView.setVisible(visibility);
        });
    }

    private void buildCalenderCellNodes() {
        for (int i = 0; i < WEEKS_COUNT; i++) {
            for (int j = 0; j < DAYS_COUNT; j++) {
                CalendarCellNode anchorPane = new CalendarCellNode();
                anchorPane.setPrefSize(200, 200);
                anchorPane.setPadding(new Insets(10));

                JFXRippler rippler = new JFXRippler(anchorPane);
                rippler.setRipplerFill(Paint.valueOf("#CCCCCC"));
                gridPane.add(rippler, j, i);

                calendarCellNodes.add(anchorPane);
            }
        }
    }

    private void drawCalenderCells(YearMonth yearMonth) {
        int year = yearMonth.getYear();
        int month = yearMonth.getMonthValue();

        LocalDate cellDate = getFirstCellDate(year, month);
        for (CalendarCellNode cell : calendarCellNodes) {
            ObservableList<Node> cellChildren = cell.getChildren();
            if (!cellChildren.isEmpty()) {
                cellChildren.clear();
            }

            cell.setDate(cellDate);

            Label dateLabel = buildDateLabel(yearMonth, cellDate, cell);
            cellChildren.add(dateLabel);

            ObservableList<String> cellStyles = cell.getStyleClass();
            cellStyles.remove(SELECTED_DATE_CSS);
            cellStyles.remove(CURRENT_DATE_CSS);

            if (LocalDate.now().equals(cellDate)) {
                cellStyles.add(CURRENT_DATE_CSS);
            }

            cell.setOnMouseClicked(event -> {
                unselectAllCells();
                cellStyles.add(SELECTED_DATE_CSS);
                rebuildNotesPane(cell.getDate());
                selectedDay = cell.getDate().getDayOfMonth();
            });

            cellDate = cellDate.plusDays(1);
        }
    }

    private void rebuildNotesPane(LocalDate date) {
        List<Note> notes;
        if (LocalDate.now().isEqual(date)) {
            notes = noteService.getSuitableByDateAndTimeAndSortedByStartDate(LocalDateTime.now());
        } else {
            notes = noteService.getSuitableByDateAndSortedByStartDate(date);
        }

        rebuildNotesPane(notes);
    }

    private void unselectAllCells() {
        for (CalendarCellNode calendarCellNode : calendarCellNodes) {
            calendarCellNode.getStyleClass().remove(SELECTED_DATE_CSS);
        }
    }

    private Label buildDateLabel(YearMonth yearMonth, LocalDate firstCellDate, CalendarCellNode cell) {
        Label dateLabel = new Label();
        dateLabel.setText(String.valueOf(firstCellDate.getDayOfMonth()));
        dateLabel.setFont(Font.font("Roboto", 16));

        ObservableList<String> dateLabelStyles = dateLabel.getStyleClass();

        if (!isCellDateWithinMonth(cell.getDate(), yearMonth)) {
            dateLabelStyles.add(ANOTHER_MONTH_DAYS_CSS);
        }

        AnchorPane.setTopAnchor(dateLabel, 5.0);
        AnchorPane.setLeftAnchor(dateLabel, 5.0);

        return dateLabel;
    }

    private LocalDate getFirstCellDate(int year, int month) {
        LocalDate firstCellDate = LocalDate.of(year, month, 1);
        while (DayOfWeek.SUNDAY != firstCellDate.getDayOfWeek()) {
            firstCellDate = firstCellDate.minusDays(1);
        }
        return firstCellDate;
    }

    private boolean isCellDateWithinMonth(LocalDate cellDate, YearMonth selectedYearMonth) {
        int year = selectedYearMonth.getYear();
        Month month = selectedYearMonth.getMonth();

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = LocalDate.of(year, month, selectedYearMonth.lengthOfMonth());

        return cellDate.equals(firstDayOfMonth) ||
                (cellDate.isAfter(firstDayOfMonth) && cellDate.isBefore(lastDayOfMonth)) ||
                cellDate.equals(lastDayOfMonth);
    }

    @FXML
    private void onButtonJanuaryClicked(ActionEvent event) {
        rebuildCalendar(JANUARY);
    }

    @FXML
    private void onButtonFebruaryClicked(ActionEvent event) {
        rebuildCalendar(FEBRUARY);
    }

    @FXML
    private void onButtonMarchClicked(ActionEvent event) {
        rebuildCalendar(MARCH);
    }

    @FXML
    private void onButtonAprilClicked(ActionEvent event) {
        rebuildCalendar(APRIL);
    }

    @FXML
    private void onButtonMayClicked(ActionEvent event) {
        rebuildCalendar(MAY);
    }

    @FXML
    private void onButtonJuneClicked(ActionEvent event) {
        rebuildCalendar(JUNE);
    }

    @FXML
    private void onButtonJulyClicked(ActionEvent event) {
        rebuildCalendar(JULY);
    }

    @FXML
    private void onButtonAugustClicked(ActionEvent event) {
        rebuildCalendar(AUGUST);
    }

    @FXML
    private void onButtonSeptemberClicked(ActionEvent event) {
        rebuildCalendar(SEPTEMBER);
    }

    @FXML
    private void onButtonOctoberClicked(ActionEvent event) {
        rebuildCalendar(OCTOBER);
    }

    @FXML
    private void onButtonNovemberClicked(ActionEvent event) {
        rebuildCalendar(NOVEMBER);
    }

    @FXML
    private void onButtonDecemberClicked(ActionEvent event) {
        rebuildCalendar(DECEMBER);
    }

    @FXML
    private void onButtonLastYearClicked(ActionEvent event) {
        int previousYear = getPreviousYear();
        labelYear.setText(String.valueOf(previousYear));
        changeCalendar(previousYear, selectedMonth);
    }

    @FXML
    private void onButtonNextYearClicked(ActionEvent event) {
        int nextYear = getNextYear();
        labelYear.setText(String.valueOf(nextYear));
        changeCalendar(nextYear, selectedMonth);
    }

    @FXML
    public void onButtonDeleteSelectedNotesClicked(ActionEvent actionEvent) {
        Stage stage = (Stage) (root.getScene().getWindow());

        ObservableList<Node> notesPaneChildren = notesPane.getChildren();
        List<JFXCheckBox> selectedCheckBoxes = notesPaneChildren.stream()
                .filter(node -> node instanceof JFXCheckBox)
                .map(JFXCheckBox.class::cast)
                .filter(CheckBox::isSelected)
                .collect(Collectors.toList());

        if (selectedCheckBoxes.isEmpty()) {
            JFXAlert<String> warningDialog = DialogUtil
                    .buildInfoDialog(stage, WARNING_DIALOG_TITLE, WARNING_DIALOG_BODY);
            warningDialog.show();
            return;
        }

        List<Note> selectedNotes = selectedCheckBoxes.stream()
                .map(Node::getUserData)
                .map(Note.class::cast)
                .collect(Collectors.toList());

        JFXAlert<String> alert = DialogUtil
                .buildYesNoModalDialog(
                        stage,
                        DELETE_DIALOG_TITLE,
                        DELETE_DIALOG_BODY,
                        () -> {
                            noteService.delete(selectedNotes);
                            notesPaneChildren.removeAll(selectedCheckBoxes);
                        });
        alert.show();
    }

    @FXML
    public void onButtonAddNewNoteClicked(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) (root.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("/layout/add-note-dialog.fxml").openStream());
        AddNewNoteDialogController addNoteController = fxmlLoader.getController();

        addNoteController.openDialog(stage, getSelectedLocalDate(), () -> rebuildNotesPane(getSelectedLocalDate()));
    }

    private LocalDate getSelectedLocalDate() {
        return LocalDate.of(getSelectedYear(), selectedMonth, selectedDay);
    }


    private int getNextYear() {
        return getSelectedYear() + 1;
    }

    private int getPreviousYear() {
        return getSelectedYear() - 1;
    }

    private int getSelectedYear() {
        return Integer.parseInt(labelYear.getText());
    }

    private void changeCalendar(int year, Month month) {
        drawCalenderCells(YearMonth.of(year, month));
        selectedMonth = month;
    }

    private void rebuildCalendar(Month month) {
        activateMonthPane(month);

        labelMonth.setText(month.name());
        changeCalendar(getSelectedYear(), month);
    }

}
