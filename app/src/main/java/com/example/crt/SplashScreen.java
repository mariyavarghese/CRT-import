package com.example.crt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;


    String base_url, login_Id;
    TinyDB tinyDB;
    String mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        tinyDB = new TinyDB(this);
        base_url = tinyDB.preference.getString("base_url", "");
        login_Id = tinyDB.preference.getString("company_id", "");


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //............start here................
//                if (accountCompanyID.getCompanyID().equals("")){
//                    Intent i=new Intent(SplashScreen.this,LoginActivity.class);
//                    startActivity(i);
//                }else {
//                    Intent i=new Intent(SplashScreen.this,SelectGovernorate.class);
//                    startActivity(i);
//                }
//
//                // close this activity
//                finish();
                //..................end here
                Log.d("val@@@", "login_Id" + login_Id + "base_url " + base_url);
//                if (login_Id == "" || login_Id.equals(null)) {
                    //                        Intent i = new Intent(Splashscreen.this, LoginActivity.class);
//                        Intent i = new Intent(Splashscreen.this, NetworkSettingActivity.class);
//                        startActivity(i);
//                        finish();
                    if (base_url == "" || base_url.equals(null)) {
                        Intent i = new Intent(SplashScreen.this, MainURL.class);
                        startActivity(i);
                        finish();
                    } else {
                        mService = tinyDB.preference.getString("base_url", "");
                        Config.BASE_URL=mService;
//                        Config.ICON_URL=mService+"data_folder/company/logo/";
                        Intent i = new Intent(SplashScreen.this, UserLoginpage.class);
                        startActivity(i);
                        finish();
                    }

//                } else {
//                    mService = tinyDB.preference.getString("base_url", "");
//                    Config.BASE_URL=mService;
////                    ApiUtils.ICON_URL=mService+"data_folder/company/logo/";
//                    Intent i = new Intent(SplashScreen.this, MainURL.class);
//                    startActivity(i);
//                    finish();
//                }
            }
        }, SPLASH_TIME_OUT);









    }
}