package com.example.newsanytime.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = {BookmarkedNews.class}, version = 3,exportSchema = false)
public abstract class BookmarkedNewsDatabase extends RoomDatabase {

    public static BookmarkedNewsDatabase dbInstance;

    public abstract BookmarkedNewsDAO newsDAO();

    public static synchronized BookmarkedNewsDatabase getInstance(Context context){
        if(dbInstance == null)
        {
                synchronized(BookmarkedNewsDatabase.class) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), BookmarkedNewsDatabase.class, "news_database")
                            .build();
            }
        }
        return dbInstance;
    }


/*
    .addMigrations(FROM_1_TO_2)
    .build();


static final Migration FROM_1_TO_2 = new Migration(1, 2) {
    @Override
    public void migrate(final SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE Repo
                         ADD COLUMN createdAt TEXT");
        }
    };*/


}
