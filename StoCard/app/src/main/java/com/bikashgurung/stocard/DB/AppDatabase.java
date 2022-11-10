package com.bikashgurung.stocard.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DBModel.class, NoteModel.class, StoreModel.class, DestinationModel.class}, version  = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CardDao cardDao();

    public abstract NoteDao noteDao();

    public abstract StoreDao storeDao();

    public abstract DestinationDao destinationDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {

        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "StoCard")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return INSTANCE;
    }
}