package by.gsu.model;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CalendarCellNode extends AnchorPane {

    private LocalDate date;

    public CalendarCellNode(Node... children) {
        super(children);
    }

}