package com.example.newsanytime.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.newsanytime.R;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    ImageView errorImage;
    TextView errorMsg;
    private Handler mWaitHandler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();

        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    boolean isInternetConnected = false;
                    isInternetConnected = internetConnectivityTest();
                    if (isInternetConnected) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        errorMsg.setText("Internet is not available!");
                        errorImage.setImageResource(R.drawable.error_1);
                    }
                }
                catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 3000);  // Give a 5 seconds delay.
    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBar);
        errorImage = findViewById(R.id.error_icon);
        errorMsg = findViewById(R.id.internet_connection_msg);
    }

    private boolean internetConnectivityTest() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}