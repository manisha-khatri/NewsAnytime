package manisha.khatri.newsanytime.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.util._enum.GenericStrings;

public class SplashScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        fadedAnimation();
        navigateToHomePageActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        new Handler().removeCallbacksAndMessages(null);
    }

    private void navigateToHomePageActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (internetConnectivityTest()) {
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                        ((TextView)findViewById(R.id.internet_connection_msg)).setText(GenericStrings.Internet_is_not_available.toString());
                        ((ImageView)findViewById(R.id.error_icon)).setImageResource(R.drawable.reload_icon);
                    }
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 3000);  // Give a 5 seconds delay.
    }

    private void initViews() {

        findViewById(R.id.error_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                navigateToHomePageActivity();
            }
        });
    }

    private boolean internetConnectivityTest() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void fadedAnimation() {
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.faded_animation);
        findViewById(R.id.splash_linear_layout).startAnimation(myAnim);
    }

}