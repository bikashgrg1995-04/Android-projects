package com.bikashgurung.stocard.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DestinationDao {

    @Query("SELECT * FROM DestinationModel")
    List<DestinationModel> getAll();

    @Query("SELECT * FROM DestinationModel WHERE destination_full_address=:full_address")
    DestinationModel findById(String full_address);

    @Insert
    void insert(DestinationModel... destinationModels);

    @Delete
    void delete(DestinationModel destinationModel);
}
