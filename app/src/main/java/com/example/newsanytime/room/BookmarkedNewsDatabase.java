package com.example.newsanytime.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = {BookmarkedNews.class}, version = 5,exportSchema = false)
public abstract class BookmarkedNewsDatabase extends RoomDatabase {

    public static BookmarkedNewsDatabase dbInstance;

    public abstract BookmarkedNewsDAO newsDAO();

    public static synchronized BookmarkedNewsDatabase getInstance(Context context){
        if(dbInstance == null)
        {
                synchronized(BookmarkedNewsDatabase.class) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), BookmarkedNewsDatabase.class, "news_database")
                            //.fallbackToDestructiveMigration()
                            //.addMigrations(MIGRATION_3_4)
                            .build();
            }
        }
        return dbInstance;
    }

   /* static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.

            database.execSQL("ALTER TABLE users " + " ADD COLUMN published_at TEXT");

        }
    };
*/

}
