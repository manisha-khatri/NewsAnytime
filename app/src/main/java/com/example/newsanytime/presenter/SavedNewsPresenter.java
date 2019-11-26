package com.example.newsanytime.presenter;

import android.app.Application;
import android.os.AsyncTask;
import com.example.newsanytime.contract.SavedNewsContract;
import com.example.newsanytime.room.SavedNews;
import com.example.newsanytime.room.SavedNewsRepository;
import com.example.newsanytime.singleton.SavedNewsSingleton;

import java.util.List;

public class SavedNewsPresenter {

    SavedNewsContract savedNewsContract;

    public SavedNewsPresenter(SavedNewsContract savedNewsContract){
        this.savedNewsContract = savedNewsContract;
    }

    public void fetchNewsFromDB(Application application){
        SavedNewsRepository savedNewsRepository = SavedNewsSingleton.getInstance(application);
        new fetchNewsListAsyncTask(savedNewsRepository).execute();
    }

    private  class fetchNewsListAsyncTask extends AsyncTask<Void, Void, List<SavedNews>> {
        SavedNewsRepository savedNewsRepository;
        fetchNewsListAsyncTask(SavedNewsRepository savedNewsRepository){
            this.savedNewsRepository = savedNewsRepository;
        }
        @Override
        protected List<SavedNews> doInBackground(Void... voids) {
            return savedNewsRepository.fetchNewsList();
        }
        protected void onPostExecute(List<SavedNews> newsList){
            if(newsList.size()>0)
                savedNewsContract.renderNewsList(newsList);
            else
                savedNewsContract.handleNoSavedNewsInDB();
        }
    }

}
