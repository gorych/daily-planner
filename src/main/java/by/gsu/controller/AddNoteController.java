package by.gsu.controller;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class AddNoteController implements Initializable {

    public static final String CONTENT_PANE = "ContentPane";
    public JFXButton cancelButton;

    @FXML
    private JFXButton centerButton;
    @FXML
    private JFXButton topButton;
    @FXML
    private JFXButton rightButton;
    @FXML
    private JFXButton bottomButton;
    @FXML
    private JFXButton leftButton;
    @FXML
    private JFXButton acceptButton;
    @FXML
    private JFXButton alertButton;
    @FXML
    private StackPane root;
    @FXML
    private JFXDialog dialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //root.getChildren().remove(dialog);

        this.centerButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
            dialog.show(root);
        });

        topButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.TOP);
            dialog.show(new StackPane());
        });

        rightButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.RIGHT);
            dialog.show(new StackPane());
        });

        bottomButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
            dialog.show(new StackPane());
        });

        leftButton.setOnAction(action -> {
            dialog.setTransitionType(JFXDialog.DialogTransition.LEFT);
            dialog.show();
        });

        acceptButton.setOnAction(action -> dialog.close());

        alertButton.setOnAction(action -> {
            JFXAlert alert = new JFXAlert((Stage) alertButton.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setOverlayClose(false);
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Label("Modal Dialog using JFXAlert"));
            layout.setBody(new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit,"
                    + " sed do eiusmod tempor incididunt ut labore et dolore magna"
                    + " aliqua. Utenim ad minim veniam, quis nostrud exercitation"
                    + " ullamco laboris nisi ut aliquip ex ea commodo consequat."));
            JFXButton closeButton = new JFXButton("ACCEPT");
            closeButton.getStyleClass().add("dialog-accept");
            closeButton.setOnAction(event -> alert.hideWithAnimation());
            layout.setActions(closeButton);
            alert.setContent(layout);
            alert.show();
        });

    }

}
