package manisha.khatri.newsanytime.singleton;

import android.app.Application;
import manisha.khatri.newsanytime.room.SavedNewsRepository;

public class SavedNewsSingleton {

    private static SavedNewsRepository savedNewsRepository;

    public static synchronized SavedNewsRepository getInstance(Application application){
        if(savedNewsRepository == null) {
            savedNewsRepository = new SavedNewsRepository(application);
        }
        return savedNewsRepository;
    }
}
