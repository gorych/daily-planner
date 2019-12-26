package by.gsu.util;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DialogUtil {

    public static JFXAlert<String> buildInfoDialog(Stage stage, String dialogTitle, String dialogBody) {
        JFXAlert<String> alert = new JFXAlert<>(stage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXButton yesButton = new JFXButton("OK");
        ObservableList<String> yesButtonStyles = yesButton.getStyleClass();
        yesButtonStyles.add("dialog-accept");
        yesButton.setOnAction(event -> alert.hideWithAnimation());

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(dialogTitle));
        layout.setBody(new Label(dialogBody));
        layout.setActions(yesButton);

        alert.setContent(layout);
        return alert;
    }

    public static JFXAlert<String> buildYesNoModalDialog(Stage stage, String dialogTitle, String dialogBody, Runnable yesButtonAction) {
        JFXAlert<String> alert = new JFXAlert<>(stage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXButton yesButton = new JFXButton("YES");
        ObservableList<String> yesButtonStyles = yesButton.getStyleClass();
        yesButtonStyles.add("raised-green-btn");
        yesButton.setOnAction(event -> {
            yesButtonAction.run();
            alert.hideWithAnimation();
        });

        JFXButton noButton = new JFXButton("NO");
        noButton.getStyleClass().add("raised-red-btn");
        noButton.setOnAction(event -> alert.hideWithAnimation());

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(dialogTitle));
        layout.setBody(new Label(dialogBody));
        layout.setActions(yesButton, noButton);

        alert.setContent(layout);
        return alert;
    }

}