package by.gsu;

import com.jfoenix.controls.JFXRippler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

import static java.time.Month.*;

@Slf4j
public class CalendarController implements Initializable {

    private static final int WEEKS_COUNT = 6;
    private static final int DAYS_COUNT = 7;

    @FXML
    private Label labelYear;

    @FXML
    private Label labelMonth;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView januaryPane, februaryPane, marchPane, aprilPane, mayPane, junePane, julyPane, augustPane, septemberPane, octoberPane, novemberPane, decemberPane;

    private String selectedMonth;
    private ArrayList<AnchorPaneNode> dateNodes = new ArrayList<>();
    private Map<Month, ImageView> monthPanes;

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

        String currentMonth = String.valueOf(LocalDate.now().getMonth());
        labelMonth.setText(currentMonth);
        selectedMonth = currentMonth;
        labelYear.setText(String.valueOf(LocalDate.now().getYear()));

        activateMonthPane(LocalDate.now().getMonth());
        buildDateNodes();

        populateDate(YearMonth.now());
    }

    private void activateMonthPane(Month currentMonth) {
        monthPanes.keySet().forEach(month -> {
            ImageView imageView = monthPanes.get(month);
            imageView.setVisible(false);

            if (month == currentMonth) {
                imageView.setVisible(true);
            }
        });
    }

    private void buildDateNodes() {
        for (int i = 0; i < WEEKS_COUNT; i++) {
            for (int j = 0; j < DAYS_COUNT; j++) {
                AnchorPaneNode anchorPane = new AnchorPaneNode();
                anchorPane.setPrefSize(200, 200);
                anchorPane.setPadding(new Insets(10));

                JFXRippler rippler = new JFXRippler(anchorPane);
                rippler.setRipplerFill(Paint.valueOf("#CCCCCC"));
                gridPane.add(rippler, j, i);

                dateNodes.add(anchorPane);
            }
        }
    }

    /**
     * Method that populate the date of month in GridPane
     **/
    private void populateDate(YearMonth yearMonthNow) {
        YearMonth yearMonth = yearMonthNow;
        // Get the date we want to start with on the calendar
        LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        // Dial back the day until it is SUNDAY (unless the month starts on a sunday)
        while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
            calendarDate = calendarDate.minusDays(1);
        }
        // Populate the calendar with day numbers
        for (AnchorPaneNode anchorPane : dateNodes) {
            if (anchorPane.getChildren().size() != 0) {
                anchorPane.getChildren().clear(); //remove the label in AnchorPane
            }

            anchorPane.setDate(calendarDate); //set date into AnchorPane

            Label label = new Label();
            label.setText(String.valueOf(calendarDate.getDayOfMonth()));
            label.setFont(Font.font("Roboto", 16)); //set the font of Text
            label.getStyleClass().add("notInRangeDays");
            if (isDateInRange(yearMonth, anchorPane.getDate())) {
                label.getStyleClass().remove("notInRangeDays");
            }
            if (anchorPane.getDate().equals(LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth()))) {
                label.getStyleClass().remove("notInRangeDays");
            }

            anchorPane.setTopAnchor(label, 5.0);
            anchorPane.setLeftAnchor(label, 5.0);
            anchorPane.getChildren().add(label);
            anchorPane.getStyleClass().remove("selectedDate"); //remove selection on date change
            anchorPane.getStyleClass().remove("dateNow"); //remove selection on current date
            if (anchorPane.getDate().equals(LocalDate.now())) { //if date is equal to current date now, then add a defualt color to pane
                anchorPane.getStyleClass().add("dateNow");
            }
            anchorPane.setOnMouseClicked(event -> { //Handle click event of AnchorPane
                System.out.println(anchorPane.getDate());
                for (AnchorPaneNode anchorPaneNode : dateNodes) {
                    anchorPaneNode.getStyleClass().remove("selectedDate");
                }
                anchorPane.getStyleClass().add("selectedDate");
            });

            calendarDate = calendarDate.plusDays(1);
            System.out.println(anchorPane.getDate());

        }
    }

    /**
     * Method that return TRUE/FALSE if the specified date is in range of the current month
     **/
    private boolean isDateInRange(YearMonth yearMonth, LocalDate currentDate) {
        LocalDate start = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
        LocalDate stop = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), yearMonth.lengthOfMonth());

        return (!currentDate.isBefore(start)) && (currentDate.isBefore(stop));
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

    private int getNextYear() {
        return Integer.parseInt(labelYear.getText()) + 1;
    }

    private int getPreviousYear() {
        return Integer.parseInt(labelYear.getText()) - 1;
    }

    private void changeCalendar(int year, String month) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy MMMM")
                .toFormatter(Locale.ENGLISH);
        populateDate(YearMonth.parse(year + " " + month, formatter));
        selectedMonth = month;
    }

    private void rebuildCalendar(Month month) {
        String monthName = month.name();

        labelMonth.setText(monthName);
        activateMonthPane(JANUARY);

        int year = Integer.parseInt(labelYear.getText());
        changeCalendar(year, monthName);
    }

}
