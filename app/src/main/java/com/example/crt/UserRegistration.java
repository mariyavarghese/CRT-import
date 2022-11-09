package com.example.crt;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRegistration extends AppCompatActivity {
EditText ed_username,ed_email,ed_password,ed_confirm;
    private Button btn_registration, btn_login;
    private EditText edt_username,edt_email,edt_password,edt_confirmpass;
    private CheckNetwork checkNetwork;
    private DataProcessor dataProcessor;
    ProgressDialog progressDoalog;
    private boolean bt_submit_status;
    private String str_username, str_password,str_email,str_comid;
    private String userId;
    private String companyId;
    ImageView img_toggle_password, img_toggle_re_password, img_toggle_pin;
    int toggle_password = 1, toggle_re_password = 1, toggle_pin;
    private ImageView iv_sample, iv_show;
    private boolean userStatus;
    private static final int REQUEST_PHONE_STATE = 101;
    String serial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        initviews();

        checkNetwork = new CheckNetwork(UserRegistration.this);
        dataProcessor = new DataProcessor(getApplicationContext());
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if(extras != null)
//        companyId = extras.getString("EXTRA_COMPANY_ID");









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

        img_toggle_re_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle_re_password == 1) {
                    edt_confirmpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_toggle_re_password.setBackgroundResource(R.drawable.password_hide_eye);
                    toggle_re_password = 2;
                } else {
                    edt_confirmpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_toggle_re_password.setBackgroundResource(R.drawable.password_show_eye);
                    toggle_re_password = 1;
                }
            }
        });











        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                if (checkNetwork.isNetworkConnected(UserRegistration.this)) {


                    getUsercredentials();
                    if (edt_username.getText().toString().equals("")) {
//
                        setToast(getResources().getString(R.string.enter_username));
                    } else if (edt_email.getText().toString().equals("")) {
//
                        setToast(getResources().getString(R.string.enter_email));
                    } else if (edt_password.getText().toString().equals("")) {
//
                        setToast(getResources().getString(R.string.enter_pass));


                    }else  if (edt_password.getText().toString().trim().length() < 5 || edt_password.getText().toString().trim().length() > 8) {
//                            ed_password_new.setError(getResources().getString(R.string.pass_length));
                            Toast.makeText(UserRegistration.this, getResources().getString(R.string.pass_length), Toast.LENGTH_SHORT).show();
                    } else if (!edt_password.getText().toString().trim().matches(".*[A-Z].*")) {
//                            ed_password_new.setError(getResources().getString(R.string.pass_must1));
                            Toast.makeText(UserRegistration.this, getResources().getString(R.string.pass_must1), Toast.LENGTH_SHORT).show();
                    }else if (!edt_password.getText().toString().trim().matches(".*[a-z].*")) {
//                            ed_password_new.setError(getResources().getString(R.string.pass_must2));
                            Toast.makeText(UserRegistration.this, getResources().getString(R.string.pass_must2), Toast.LENGTH_SHORT).show();
                    }else if (!edt_password.getText().toString().trim().matches(".*[0-9].*")) {
//                            ed_password_new.setError(getResources().getString(R.string.pass_must3));
                            Toast.makeText(UserRegistration.this, getResources().getString(R.string.pass_must3), Toast.LENGTH_SHORT).show();


                    } else if (edt_confirmpass.getText().toString().equals("")) {
//
                        setToast(getResources().getString(R.string.enter_confirm));

                    } else if (!edt_password.getText().toString().trim().equals(edt_confirmpass.getText().toString().trim()))  {

                        setToast("Password Miss match ");

//                    callLogin();

                    }else{
                        register();
                    }
//                }




//                Toast.makeText(UserRegistration.this, " Registered successfully!!!", Toast.LENGTH_SHORT).show();
            }
        });



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent i = new Intent(UserRegistration.this,UserLoginpage.class);
                  startActivity(i);
                  overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


    }

    private void getUsercredentials() {
        str_username = edt_username.getText().toString().trim();
        str_email = edt_email.getText().toString().trim();
        str_password = edt_password.getText().toString().trim();
        companyId = dataProcessor.getCompanyidno(Config.COMPANY_IDNO);
    }

    public void setToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void register() {
        String url = Config.BASE_URL + "get_data.php?p=6";
        callprogressDialog();
        final RequestQueue requestQueue = Volley.newRequestQueue(UserRegistration.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDoalog.dismiss();
                Log.e("response", " " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");


                    progressDoalog.dismiss();
                    if (status.equals("true") ) {



                        Toast.makeText(UserRegistration.this, "" + "Registered Successfully", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(UserRegistration.this,UserLoginpage.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    }else {
                        Toast.makeText(UserRegistration.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UserRegistration.this, getResources().getString(R.string.check_internet),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(UserRegistration.this, getResources().getString(R.string.unauthorized_access),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(UserRegistration.this, getResources().getString(R.string.server_error),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(UserRegistration.this, getResources().getString(R.string.network_error),
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(UserRegistration.this, getResources().getString(R.string.parse_error),
                            Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", str_username);
                params.put("user_email", str_email);
                params.put("user_password", str_password);
                params.put("company_id", companyId);


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
        progressDoalog = new ProgressDialog(UserRegistration.this);
//        progressDoalog.setMax(100);
//        progressDoalog.setMessage("Resetting PIN....Please wait...");
        progressDoalog.setMessage(getResources().getString(R.string.loading));
//        progressDoalog.setTitle("ProgressDialog bar example");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }

    private void initviews() {
        btn_registration = (Button)findViewById(R.id.btn_register);
        btn_login = (Button)findViewById(R.id.btn_login);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_confirmpass = (EditText) findViewById(R.id.edt_confirmpass);
        edt_email = (EditText) findViewById(R.id.edt_email);
        img_toggle_password = findViewById(R.id.img_toggle_password);
        img_toggle_re_password = findViewById(R.id.img_toggle_re_password);
        img_toggle_password.setBackgroundResource(R.drawable.password_show_eye);
        img_toggle_re_password.setBackgroundResource(R.drawable.password_show_eye);
    }


    @Override
    public void onBackPressed() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserRegistration.this);
        Log.e("tag", "logout: ");

        builder.setMessage("Are you sure want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        Intent i = new Intent(UserRegistration.this, CompanyLogin.class);
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