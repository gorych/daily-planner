/*
 * This file is generated by jOOQ.
 */
package by.gsu.domain;


import by.gsu.domain.tables.Note;
import by.gsu.domain.tables.records.NoteRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<NoteRecord, Integer> IDENTITY_NOTE = Identities0.IDENTITY_NOTE;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<NoteRecord> PK_NOTE = UniqueKeys0.PK_NOTE;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<NoteRecord, Integer> IDENTITY_NOTE = Internal.createIdentity(Note.NOTE, Note.NOTE.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<NoteRecord> PK_NOTE = Internal.createUniqueKey(Note.NOTE, "pk_note", Note.NOTE.ID);
    }
}
