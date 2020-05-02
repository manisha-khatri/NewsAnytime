package manisha.khatri.newsanytime.network.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

@Database(entities = {BookmarkedNews.class}, version = 8,exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    public static NewsDatabase dbInstance;

    public abstract NewsDAO newsDAO();

    public static synchronized NewsDatabase getInstance(Context context){
        if(dbInstance == null)
        {
                synchronized(NewsDatabase.class) {
                    dbInstance = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "news_database")
                                //.fallbackToDestructiveMigration()
                                .addMigrations(MIGRATION_7_8)
                                .build();
            }
        }
        return dbInstance;
    }

    static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.

            //database.execSQL("ALTER TABLE users " + " ADD COLUMN published_at TEXT");
            //database.execSQL("ALTER TABLE bookmarked_news RENAME TO saved_news");

        }
    };

}
