package by.gsu.util;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Slf4j
@UtilityClass
public class DialogUtil {

    public static JFXAlert<String> buildInfoDialog(Stage stage, String title, String body) {
        return buildDialogWithButtons(stage, title, new Label(body),
                (alert) -> buildJfxButton(alert, "OK", "dialog-accept", () -> {}));
    }

    public static JFXAlert<String> buildYesNoModalDialog(Stage stage, String title, String body, Runnable yesButtonAction) {
        return buildDialogWithButtons(stage, title, new Label(body),
                (alert) -> buildJfxButton(alert, "YES", "raised-green-btn", yesButtonAction),
                (alert) -> buildJfxButton(alert, "NO", "raised-red-btn", () -> {}));
    }

    public static JFXAlert<String> buildAddCancelModalDialog(Stage stage, String title, Node body, Runnable addAction, Runnable cancelAction) {
        return buildDialogWithButtons(stage, title, body,
                (alert) -> buildJfxButton(alert, "ADD", "raised-green-btn", addAction),
                (alert) -> buildJfxButton(alert, "CANCEL", "raised-grey-btn", cancelAction));
    }

    private static JFXAlert<String> buildDialogWithButtons(Stage stage, String title, Node body, Function<JFXAlert<String>, JFXButton>... buttonBuilders) {
        JFXAlert<String> alert = new JFXAlert<>(stage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        List<JFXButton> buttons = new ArrayList<>();
        Arrays.asList(buttonBuilders).forEach(builder -> buttons.add(builder.apply(alert)));

        JFXDialogLayout layout = buildDialogLayout(title, body, buttons.toArray(new JFXButton[0]));
        alert.setContent(layout);

        return alert;
    }

    private static JFXDialogLayout buildDialogLayout(String title, Node body, JFXButton... buttons) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(title));
        layout.setBody(body);
        layout.setActions(buttons);
        return layout;
    }

    private static JFXButton buildJfxButton(JFXAlert<String> alert, String name, String style, Runnable action) {
        JFXButton addButton = new JFXButton(name);
        addButton.getStyleClass().add(style);
        addButton.setOnAction(event -> {
            action.run();
            alert.hideWithAnimation();
        });

        return addButton;
    }
}