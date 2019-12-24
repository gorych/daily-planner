package by.gsu.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Note {

    private int id;

    public Note(int id) {
        this.id = id;
    }
}
