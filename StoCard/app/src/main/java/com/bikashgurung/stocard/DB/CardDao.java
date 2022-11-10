package com.bikashgurung.stocard.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM dbmodel")
    List<DBModel> getAll();

    @Query("SELECT * FROM dbmodel WHERE uid=:id")
    DBModel findById(String id);

    @Insert
    void insertUser(DBModel... dbModels);

    @Delete
    void delete(DBModel dbModel);
}
