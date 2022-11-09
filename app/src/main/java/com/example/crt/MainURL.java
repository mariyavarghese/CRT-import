package com.example.crt;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainURL extends AppCompatActivity {
    TinyDB tinyDB;
    private static final String TAG = "RemoteApp";
    View focusView;
    boolean cancel;
    private ProgressDialog progressDoalog;
Button configure;
EditText edt_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_url);

        edt_username = (EditText) findViewById(R.id.edt_username);
        configure = (Button) findViewById(R.id.btn_configure);

        tinyDB = new TinyDB(this);
        cancel = false;
        focusView = null;




        configure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Config.BASE_URL="";
                String base_url = edt_username.getText().toString().trim();
                if (TextUtils.isEmpty(base_url)) {
                    edt_username.setError("Please enter the base url");
                    focusView = edt_username;
                    cancel = true;
                }
                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.

                    Log.e(TAG, "onClick:111 ");
                    focusView.requestFocus();
                } else {
                    // Show a progress spinner, and kick off a background task to
                    // perform the user login attempt.
                    Log.e(TAG, "onClick: ");

                    tinyDB.setString("base_url", base_url);
                    Config.BASE_URL=base_url;
//                    ApiUtils.ICON_URL=base_url+"data_folder/company/logo/";

                    String base_url1 = tinyDB.preference.getString("base_url", "");
                    Log.e("base_url","From tiny DB"+base_url1);
                    Toast.makeText(MainURL.this, "base="+Config.BASE_URL, Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(MainURL.this, CompanyLogin.class);
                    startActivity(i);
                }


            }
        });


    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainURL.this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.do_you_want_exit));
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        callprogressDialog();
                        finish();
//                        Intent i = new Intent(Profile.this,LoginActivity.class);
//                        startActivity(i);
//                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });
        progressDoalog.dismiss();
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    private void callprogressDialog() {
        progressDoalog = new ProgressDialog(MainURL.this);
//        progressDoalog.setMax(100);
//        progressDoalog.setMessage("Resetting PIN....Please wait...");
        progressDoalog.setMessage(getResources().getString(R.string.loading));
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }


}