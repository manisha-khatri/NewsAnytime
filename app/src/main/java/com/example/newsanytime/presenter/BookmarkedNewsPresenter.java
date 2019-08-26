package com.example.newsanytime.presenter;

import android.app.Application;
import android.os.AsyncTask;

import com.example.newsanytime.contract.BookmarkedNewsContract;
import com.example.newsanytime.room.BookmarkedNews;
import com.example.newsanytime.room.BookmarkedNewsRepository;
import com.example.newsanytime.singleton.BookmarkedNewsSingleton;

import java.util.List;

public class BookmarkedNewsPresenter {

    BookmarkedNewsContract bookmarkedNewsContract;

    public BookmarkedNewsPresenter(BookmarkedNewsContract bookmarkedNewsContract){
        this.bookmarkedNewsContract = bookmarkedNewsContract;
    }

    public void fetchNewsListFromDB(Application application){
        BookmarkedNewsRepository bookmarkedNewsRepository = BookmarkedNewsSingleton.getInstance(application);
        new fetchNewsListAsyncTask(bookmarkedNewsRepository).execute();
    }

    private  class fetchNewsListAsyncTask extends AsyncTask<Void, Void, List<BookmarkedNews>> {

        BookmarkedNewsRepository bookmarkedNewsRepository;

        fetchNewsListAsyncTask(BookmarkedNewsRepository bookmarkedNewsRepository){
            this.bookmarkedNewsRepository = bookmarkedNewsRepository;
        }
        @Override
        protected List<BookmarkedNews> doInBackground(Void... voids) {
            return bookmarkedNewsRepository.fetchNewsList();
        }
        protected void onPostExecute(List<BookmarkedNews> newsList){
            bookmarkedNewsContract.renderNewsList(newsList);
        }
    }

}
