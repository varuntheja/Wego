package com.wego.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {CategoryEntity.class}, version = 1)
public abstract class WegoRoomDatabase extends RoomDatabase {

    public abstract WegoDao wegoDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile WegoRoomDatabase INSTANCE;

    public static WegoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WegoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WegoRoomDatabase.class, "wego_database")
                            .fallbackToDestructiveMigration()
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
