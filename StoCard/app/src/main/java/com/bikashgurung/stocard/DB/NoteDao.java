package com.bikashgurung.stocard.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface NoteDao {

    @Query("SELECT * FROM NoteModel WHERE cardID=:id")
    NoteModel findById(String id);

    @Query("UPDATE NoteModel SET note=:note_text WHERE idNote = :Note_id AND cardID = :id")
    void update(int Note_id, String id, String note_text);

    @Insert
    void insert(NoteModel... noteModels);

}
