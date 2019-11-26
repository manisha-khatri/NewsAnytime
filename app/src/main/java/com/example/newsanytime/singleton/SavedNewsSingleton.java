package com.example.newsanytime.singleton;

import android.app.Application;
import com.example.newsanytime.room.SavedNewsRepository;

public class SavedNewsSingleton {

    private static SavedNewsRepository savedNewsRepository;

    public static synchronized SavedNewsRepository getInstance(Application application){
        if(savedNewsRepository == null) {
            savedNewsRepository = new SavedNewsRepository(application);
        }
        return savedNewsRepository;
    }
}
