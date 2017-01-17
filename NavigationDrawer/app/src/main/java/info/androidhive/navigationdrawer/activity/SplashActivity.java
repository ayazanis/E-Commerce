package info.androidhive.navigationdrawer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import info.androidhive.navigationdrawer.R;

public class SplashActivity extends Activity {

    static boolean aBoolean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        aBoolean=false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
            }
        },1000);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(aBoolean){
            finish();
        }
    }
}
