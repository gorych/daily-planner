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

    @FXML
    private Label labelYear;

    @FXML
    private Label labelMonth;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView januaryPane, februaryPane, marchPane, aprilPane, mayPane, junePane, julyPane, augustPane, septemberPane, octoberPane, novemberPane, decemberPane;

    private String selectedMonth;
    private ArrayList<AnchorPaneNode> dateList = new ArrayList<>();
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

        labelYear.setText(String.valueOf(LocalDate.now().getYear()));
        String currentMonth = String.valueOf(LocalDate.now().getMonth());
        labelMonth.setText(currentMonth);

        activateMonthPane(LocalDate.now().getMonth());
        //activateMonthPane(getCurrentMonth()); //get the current month and show only the navigation on it.
        selectedMonth = currentMonth;

        //Add AnchorPane to GridView
        for (int i = 0; i < 6; i++) { //Row has 6, means we only shows six weeks on calendar, change it to your needs.
            for (int j = 0; j < 7; j++) { //Column has 7, for 7 days a week
                //Layout of AnchorPane
                AnchorPaneNode anchorPane = new AnchorPaneNode();
                anchorPane.setPrefSize(200, 200);
                anchorPane.setPadding(new Insets(10));

                JFXRippler rippler = new JFXRippler(anchorPane);
                rippler.setRipplerFill(Paint.valueOf("#CCCCCC"));
                gridPane.add(rippler, j, i);

                dateList.add(anchorPane); //add the AnchorPane in a list
            }
        }

        populateDate(YearMonth.now());

    }

    /**
     * Method that populate the date of moth in GridPane
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
        for (AnchorPaneNode anchorPane : dateList) {
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
                for (AnchorPaneNode anchorPaneNode : dateList) {
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

    /**
     * Method that call the method populateDate(year, month) to change the calendar according to selected month and year
     **/
    private void changeCalendar(int year, String month) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("yyyy MMMM")
                .toFormatter(Locale.ENGLISH);
        populateDate(YearMonth.parse(year + " " + month, formatter));
        selectedMonth = month;
    }

    @FXML
    private void onButtonJanuaryClicked(ActionEvent event) {
        labelMonth.setText("JANUARY");
        activateMonthPane(0);
        changeCalendar(Integer.parseInt(labelYear.getText()), "January");
    }

    @FXML
    private void onButtonFebruaryClicked(ActionEvent event) {
        labelMonth.setText("FEBRUARY");
        activateMonthPane(1);
        changeCalendar(Integer.parseInt(labelYear.getText()), "February");
    }

    @FXML
    private void onButtonMarchClicked(ActionEvent event) {
        labelMonth.setText("MARCH");
        activateMonthPane(2);
        changeCalendar(Integer.parseInt(labelYear.getText()), "March");
    }

    @FXML
    private void onButtonAprilClicked(ActionEvent event) {
        labelMonth.setText("APRIL");
        activateMonthPane(3);
        changeCalendar(Integer.parseInt(labelYear.getText()), "April");
    }

    @FXML
    private void onButtonMayClicked(ActionEvent event) {
        labelMonth.setText("MAY");
        activateMonthPane(4);
        changeCalendar(Integer.parseInt(labelYear.getText()), "May");
    }

    @FXML
    private void onButtonJuneClicked(ActionEvent event) {
        labelMonth.setText("JUNE");
        activateMonthPane(5);
        changeCalendar(Integer.parseInt(labelYear.getText()), "June");
    }

    @FXML
    private void onButtonJulyClicked(ActionEvent event) {
        labelMonth.setText("JULY");
        activateMonthPane(6);
        changeCalendar(Integer.parseInt(labelYear.getText()), "July");
    }

    @FXML
    private void onButtonAugustClicked(ActionEvent event) {
        labelMonth.setText("AUGUST");
        activateMonthPane(7);
        changeCalendar(Integer.parseInt(labelYear.getText()), "August");
    }

    @FXML
    private void onButtonSeptemberClicked(ActionEvent event) {
        labelMonth.setText("SEPTEMBER");
        activateMonthPane(8);
        changeCalendar(Integer.parseInt(labelYear.getText()), "September");
    }

    @FXML
    private void onButtonOctoberClicked(ActionEvent event) {
        labelMonth.setText("OCTOBER");
        activateMonthPane(9);
        changeCalendar(Integer.parseInt(labelYear.getText()), "October");
    }

    @FXML
    private void onButtonNovemberClicked(ActionEvent event) {
        labelMonth.setText("NOVEMBER");
        activateMonthPane(10);
        changeCalendar(Integer.parseInt(labelYear.getText()), "November");
    }

    @FXML
    private void onButtonDecemberClicked(ActionEvent event) {
        labelMonth.setText("DECEMBER");
        activateMonthPane(11);
        changeCalendar(Integer.parseInt(labelYear.getText()), "December");
    }

    @FXML
    private void onButtonLastYearClicked(ActionEvent event) {
        labelYear.setText(String.valueOf(Integer.parseInt(labelYear.getText()) - 1));
        changeCalendar(Integer.parseInt(labelYear.getText()), selectedMonth);
    }

    @FXML
    private void onButtonNextYearClicked(ActionEvent event) {
        labelYear.setText(String.valueOf(Integer.parseInt(labelYear.getText()) + 1));
        changeCalendar(Integer.parseInt(labelYear.getText()), selectedMonth);
    }

    private int getCurrentMonth() {
        LocalDate today = LocalDate.now();
        return today.getMonthValue() - 1;
    }

    /**
     * Method that hides/shows the navigation of selected month
     **/
    private void activateMonthPane(int month) {
        januaryPane.setVisible(false);
        februaryPane.setVisible(false);
        marchPane.setVisible(false);
        aprilPane.setVisible(false);
        mayPane.setVisible(false);
        junePane.setVisible(false);
        julyPane.setVisible(false);
        augustPane.setVisible(false);
        septemberPane.setVisible(false);
        octoberPane.setVisible(false);
        novemberPane.setVisible(false);
        decemberPane.setVisible(false);

        switch (month) {
            case 0:
                januaryPane.setVisible(true);
                break;
            case 1:
                februaryPane.setVisible(true);
                break;
            case 2:
                marchPane.setVisible(true);
                break;
            case 3:
                aprilPane.setVisible(true);
                break;
            case 4:
                mayPane.setVisible(true);
                break;
            case 5:
                junePane.setVisible(true);
                break;
            case 6:
                julyPane.setVisible(true);
                break;
            case 7:
                augustPane.setVisible(true);
                break;
            case 8:
                septemberPane.setVisible(true);
                break;
            case 9:
                octoberPane.setVisible(true);
                break;
            case 10:
                novemberPane.setVisible(true);
                break;
            case 11:
                decemberPane.setVisible(true);
                break;
            default:
                break;
        }
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

}
