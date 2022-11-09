package com.example.crt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CompanyLogin extends AppCompatActivity {
    Button btn_login;
    EditText edt_email, edt_password;
    ImageView img_reset_updates;
    String str_email, str_password;
    private ProgressDialog progressDoalog;
    DataProcessor dataProcessor;
    private CheckNetwork checkNetwork;
    private String mService;
    Context context;
    TinyDB tinyDB;
    private static final int ACCESS_COARSE_LOCATION_RESULT_CODE = 4;
    private static final int BLUETOOTH_RESULT_CODE = 5;
    private static final int DANGEROUS_RESULT_CODE = 1;
    private static final String TAG = "CRT";
    ImageView img_toggle_password, img_toggle_re_password, img_toggle_pin;
    int toggle_password = 1, toggle_re_password = 1, toggle_pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
        checkNetwork = new CheckNetwork(CompanyLogin.this);
        dataProcessor = new DataProcessor(getApplicationContext());
        context = this;
        initviews();

        tinyDB = new TinyDB(this);

        mService = tinyDB.preference.getString("base_url", "") + "get_data.php?";



        img_toggle_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle_password == 1) {
                    edt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_toggle_password.setBackgroundResource(R.drawable.password_hide_eye);
                    toggle_password = 2;
                } else {
                    edt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_toggle_password.setBackgroundResource(R.drawable.password_show_eye);
                    toggle_password = 1;
                }
            }
        });





        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (checkNetwork.isNetworkConnected(CompanyLogin.this)) {
                getUsercredentials();
                if (edt_email.getText().toString().equals("")) {
                    edt_email.setError("Enter Email ID");
                    setToast(getResources().getString(R.string.enter_email));
                } else if (edt_password.getText().toString().equals("")) {
                    edt_password.setError("Enter Your Password");
                    setToast(getResources().getString(R.string.enter_pass));
                } else {
                    callLogin();
//                    register();
                }


            }
        });


        img_reset_updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CompanyLogin.this);
                Log.d(TAG, "logout: ");

                builder.setMessage("Are you sure want to change URL?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                tinyDB.resetSetting();
                                finish();
                                Intent i = new Intent(CompanyLogin.this, MainURL.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                builder.show();

            }
        });


    }

    private void callLogin() {

        callprogressDialog();
        final RequestQueue requestQueue = Volley.newRequestQueue(CompanyLogin.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mService + "p=2", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDoalog.dismiss();
                Log.e("response", " " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");


                    progressDoalog.dismiss();
                    if (code.equals("1")) {


                        Toast.makeText(CompanyLogin.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray = jsonObject.getJSONArray("Company_Details");


                        System.out.println("@@@@@@@@" + jsonArray);
                        if (jsonArray.length() > 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                dataProcessor.setCompanyidno(Config.COMPANY_IDNO, jsonObject1.getString("id"));
                                tinyDB.setString("company_id", jsonObject1.getString("id"));
                                Intent intent = new Intent(CompanyLogin.this, UserLoginpage.class);
//                                intent.putExtra("EXTRA_COMPANY_ID", dataProcessor.getCompanyId(Config.COMPANY_ID));
                                startActivity(intent);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


                            }
                        }
                    } else {
                        Toast.makeText(CompanyLogin.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDoalog.dismiss();
//                Toast.makeText(LoginActivity.this, "unable_to_connect"+error.toString(), Toast.LENGTH_SHORT).show();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(CompanyLogin.this, getResources().getString(R.string.check_internet),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(CompanyLogin.this, getResources().getString(R.string.unauthorized_access),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(CompanyLogin.this, getResources().getString(R.string.server_error),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(CompanyLogin.this, getResources().getString(R.string.network_error),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(CompanyLogin.this, getResources().getString(R.string.parse_error),
                            Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("company_email", str_email);
                params.put("company_password", str_password);
                params.put("company_password", str_password);


                Log.e("params", "" + params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);


    }

    private void callprogressDialog() {
        progressDoalog = new ProgressDialog(CompanyLogin.this);
//        progressDoalog.setMax(100);
//        progressDoalog.setMessage("Resetting PIN....Please wait...");
        progressDoalog.setMessage(getResources().getString(R.string.loading));
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }

    public void setToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    private void getUsercredentials() {
        str_email = edt_email.getText().toString().trim();
        str_password = edt_password.getText().toString().trim();
    }

    private void initviews() {
        btn_login = (Button) findViewById(R.id.btn_login);
//            btn_registration = (Button)findViewById(R.id.btn_register);
//            edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
//            edt_confirmpass = (EditText) findViewById(R.id.edt_confirmpass);
        edt_email = (EditText) findViewById(R.id.edt_email);
        img_reset_updates = (ImageView) findViewById(R.id.img_reset_updates);
        img_toggle_password = findViewById(R.id.img_toggle_password);
        img_toggle_password.setBackgroundResource(R.drawable.password_show_eye);


    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompanyLogin.this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.do_you_want_exit));
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//                        callprogressDialog();
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
}
