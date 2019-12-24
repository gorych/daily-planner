package by.gsu.model;

import lombok.Data;
import org.jooq.Record;

import java.time.LocalDateTime;

import static by.gsu.domain.tables.Note.NOTE;
import static by.gsu.util.DateTimeUtil.YYYY_MM_DD_HH_MM_FORMATTER;

@Data
public class Note {

    private int id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Note(Record record) {
        this.id = record.get(NOTE.ID);
        this.name = record.get(NOTE.NAME);
        this.description = record.get(NOTE.SHORTDESCRIPTION);
        this.startDate = LocalDateTime.parse(record.get(NOTE.STARTDATE), YYYY_MM_DD_HH_MM_FORMATTER);
        this.endDate = LocalDateTime.parse(record.get(NOTE.ENDDATE), YYYY_MM_DD_HH_MM_FORMATTER);
    }

}
