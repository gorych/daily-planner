/*
 * This file is generated by jOOQ.
 */
package by.gsu.domain;


import by.gsu.domain.tables.Note;
import by.gsu.domain.tables.SqliteSequence;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>note</code>.
     */
    public static final Note NOTE = by.gsu.domain.tables.Note.NOTE;

    /**
     * The table <code>sqlite_sequence</code>.
     */
    public static final SqliteSequence SQLITE_SEQUENCE = by.gsu.domain.tables.SqliteSequence.SQLITE_SEQUENCE;
}
