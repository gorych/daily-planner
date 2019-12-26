package by.gsu.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jooq.Record;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static by.gsu.domain.tables.Note.NOTE;
import static by.gsu.util.DateTimeUtil.HH_MM_FORMATTER;
import static by.gsu.util.DateTimeUtil.YYYY_MM_DD_HH_MM_FORMATTER;

@Data
@NoArgsConstructor
public class Note {

    private int id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Note(Record record) {
        this.id = record.get(NOTE.ID);
        this.name = record.get(NOTE.NAME);
        this.description = record.get(NOTE.DESCRIPTION);
        this.startDate = LocalDateTime.parse(record.get(NOTE.STARTDATE, String.class), YYYY_MM_DD_HH_MM_FORMATTER);
        this.endDate = LocalDateTime.parse(record.get(NOTE.ENDDATE, String.class), YYYY_MM_DD_HH_MM_FORMATTER);
    }

    @Override
    public String toString() {
        return startDate.format(HH_MM_FORMATTER) + "-" +
                endDate.format(HH_MM_FORMATTER) + " " +
                name;
    }

    public static class Builder {

        private int id;
        private String name;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setStartDate(LocalDate startDate, LocalTime startTime) {
            this.startDate = LocalDateTime.of(startDate, startTime);
            return this;
        }

        public Builder setEndDate(LocalDate endDate, LocalTime endTime) {
            this.endDate = LocalDateTime.of(endDate, endTime);
            return this;
        }

        public Note build() {
            Note note = new Note();
            note.setId(id);
            note.setName(name);
            note.setDescription(description);
            note.setStartDate(startDate);
            note.setEndDate(endDate);
            return note;
        }
    }

}
