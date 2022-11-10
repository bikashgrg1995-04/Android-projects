package com.bikashgurung.geofencing;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {StoreModel.class}, version  = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StoreDao storeDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "StoCard")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}