package com.bikashgurung.stocard.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NoteModel {

    @PrimaryKey(autoGenerate = true)
    public int idNote;

    @ColumnInfo(name = "cardID")
    public String cardID;

    @ColumnInfo(name = "note")
    public String note_text;

    public NoteModel() {
    }

    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(int idNote) {
        this.idNote = idNote;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getNote_text() {
        return note_text;
    }

    public void setNote_text(String note_text) {
        this.note_text = note_text;
    }
}
