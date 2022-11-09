package com.example.crt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLoginpage extends AppCompatActivity {
Button btn_login,btn_register;
EditText edt_username,edt_password;
ImageView round;
String str_email,str_password;
private ProgressDialog progressDoalog;
DataProcessor dataProcessor;
    private CheckNetwork checkNetwork;
    private String mService;
    Context context;
private Button btn_registeruser;
ImageView img_toggle_password, img_toggle_re_password, img_toggle_pin;
int toggle_password = 1, toggle_re_password = 1, toggle_pin;
private static final int BLUETOOTH_RESULT_CODE = 5;
private static final int DANGEROUS_RESULT_CODE = 1;
private ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNetwork = new CheckNetwork(UserLoginpage.this);
        dataProcessor = new DataProcessor(getApplicationContext());
        context = this;
       initviews();


        Glide.with(getApplicationContext())
                .load(R.drawable.user)
                .into(round);








        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {



            if(checkNetwork.isNetworkConnected(UserLoginpage.this)) {


                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                //   bluetoothOn();
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, 1);
                } else if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);

                } else if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);

                }

            }
        }









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


logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserLoginpage.this);
        Log.e("tag", "logout: ");

        builder.setMessage("Are you sure want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        Intent i = new Intent(UserLoginpage.this, CompanyLogin.class);
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




        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUsercredentials();
                if (edt_username.getText().toString().equals("")) {
                    setToast(getResources().getString(R.string.enter_email));
                } else if (edt_password.getText().toString().equals("")) {
                    setToast(getResources().getString(R.string.enter_pass));
                } else {
                    callLogin();
                }
//                Toast.makeText(UserLoginpage.this, " Login successfull !!!", Toast.LENGTH_SHORT).show();
            }
        });



        btn_registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginpage.this, UserRegistration.class);
                startActivity(intent);
            }
        });





    }

    private void callLogin() {
        callprogressDialog();
        String url = Config.BASE_URL + "get_data.php?p=3";
        final RequestQueue requestQueue = Volley.newRequestQueue(UserLoginpage.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url , new com.android.volley.Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDoalog.dismiss();
                Log.e("response", " " + response);
                Log.e("request", url);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");


                    progressDoalog.dismiss();
                    if (code.equals("1") ) {


                        Toast.makeText(UserLoginpage.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray = jsonObject.getJSONArray("User_Details");


                        System.out.println("@@@@@@@@1" + jsonArray);
                        if (jsonArray.length() > 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                dataProcessor.setUserId(Config.COMPANY_ID, jsonObject1.getString("id"));
                                dataProcessor.setUserName(Config.USER_ID, jsonObject1.getString("user_name"));
                                dataProcessor.setUserPassword(Config.PASSWORD_ID, jsonObject1.getString("user_password"));
                                dataProcessor.setUserEmail(Config.EMAIL_ID, jsonObject1.getString("user_email"));
                                dataProcessor.setUserStatus(Config.STATUS_ID, jsonObject1.getString("status"));
                                dataProcessor.setCompanyidno(Config.COMPANY_IDNO, jsonObject1.getString("company_id"));

                                Intent intent = new Intent(UserLoginpage.this, MainActivity.class);
                                startActivity(intent);
                                Log.e("userid", dataProcessor.getUserId(Config.COMPANY_ID));
                                Log.e("companyid", dataProcessor.getCompanyidno(Config.COMPANY_IDNO));
                            }
                        }





                    }else{
                        Toast.makeText(UserLoginpage.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDoalog.dismiss();
//                Toast.makeText(LoginActivity.this, "unable_to_connect"+error.toString(), Toast.LENGTH_SHORT).show();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(UserLoginpage.this, getResources().getString(R.string.check_internet),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UserLoginpage.this, getResources().getString(R.string.unauthorized_access),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UserLoginpage.this, getResources().getString(R.string.server_error),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UserLoginpage.this, getResources().getString(R.string.network_error),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UserLoginpage.this, getResources().getString(R.string.parse_error),
                            Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_email", str_email);
                params.put("user_password", str_password);
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



    private void getUsercredentials() {
        str_email = edt_username.getText().toString().trim();
        str_password = edt_password.getText().toString().trim();
    }

    public void setToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void callprogressDialog() {
        progressDoalog = new ProgressDialog(UserLoginpage.this);
//        progressDoalog.setMax(100);
//        progressDoalog.setMessage("Resetting PIN....Please wait...");
        progressDoalog.setMessage(getResources().getString(R.string.loading));
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }

    private void initviews() {
        logout = (ImageView)findViewById(R.id.idlogout);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_registeruser = (Button) findViewById(R.id.btn_registeruser);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        round = (ImageView)findViewById(R.id.round);
        img_toggle_password = findViewById(R.id.img_toggle_password);
        img_toggle_password.setBackgroundResource(R.drawable.password_show_eye);


    }



    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserLoginpage.this);
        Log.e("tag", "logout: ");

        builder.setMessage("Are you sure want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        Intent i = new Intent(UserLoginpage.this, CompanyLogin.class);
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









}