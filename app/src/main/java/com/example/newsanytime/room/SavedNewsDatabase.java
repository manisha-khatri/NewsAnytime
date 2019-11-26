package com.example.newsanytime.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = {SavedNews.class}, version = 6,exportSchema = false)
public abstract class SavedNewsDatabase extends RoomDatabase {

    public static SavedNewsDatabase dbInstance;

    public abstract SavedNewsDAO newsDAO();

    public static synchronized SavedNewsDatabase getInstance(Context context){
        if(dbInstance == null)
        {
                synchronized(SavedNewsDatabase.class) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), SavedNewsDatabase.class, "news_database")
                                .fallbackToDestructiveMigration()
                                //.addMigrations(MIGRATION_5_6)
                                .build();
            }
        }
        return dbInstance;
    }

   /* static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.

            //database.execSQL("ALTER TABLE users " + " ADD COLUMN published_at TEXT");
            database.execSQL("ALTER TABLE bookmarked_news RENAME TO saved_news");

        }
    };*/

}
