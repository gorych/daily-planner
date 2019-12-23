package by.gsu;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AnchorPaneNode extends AnchorPane {

    private LocalDate date;

    public AnchorPaneNode(Node... children) {
        super(children);
    }

}