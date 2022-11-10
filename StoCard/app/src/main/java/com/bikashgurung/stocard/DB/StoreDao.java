package com.bikashgurung.stocard.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StoreDao {

    @Query("SELECT * FROM StoreModel")
    List<StoreModel> getAll();

    @Query("SELECT * FROM StoreModel WHERE bankName = :bank_name")
    List<StoreModel> findById(String bank_name);

    @Insert
    void insert(StoreModel... storeModels);

}
