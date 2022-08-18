package com.example.crt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crt.databinding.ActivityDetailBinding;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.UUID;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    public final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDetailBinding binding;
    private final static int CURRENT_PAGE= 1;
   // private ConnectedThread maConnectedThread; // bluetooth background worker thread to send and receive data
   private static final UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier

    private BluetoothAdapter mBTAdapter;
    private Handler mHandler; // Our main handler that will receive callback notifications
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDetail.toolbar);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio


        binding.appBarDetail.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        getSupportActionBar().setTitle("Vehicle Details");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_detail);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        singleToneClass singleToneClass = com.example.crt.singleToneClass.getInstance();
        dbHandler = new DBHandler(DetailActivity.this);

        Log.d("add",singleToneClass.getData());
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //   mReadBuffer.setText(readMessage);
                    singleToneClass singleToneClass = com.example.crt.singleToneClass.getInstance();
                    Log.d("frag",String.valueOf(singleToneClass.getFrag()));

                    if(singleToneClass.getFrag()==1) {
                     String outhex = TextUtil.toHexString((byte[]) msg.obj);
                     outhex = outhex.replaceAll("\\s", "");
                     if (outhex.contains("C350")) {
                         String ignition = outhex.substring(60, 64);
                         ImageView igview = (ImageView) findViewById(R.id.ignition);


                         if (ignition.equals("0000")) {
                             Log.d("addtesigout", outhex);
                             igview.setImageResource(R.drawable.ic_gcaronoff);
                         } else {
                             igview.setImageResource(R.drawable.ic_gcaron);
                         }
                    /*    Log.d("addtes", outhex.substring(170,174));
                        int decimal=Integer.parseInt(outhex.substring(170,174),16);
                        Log.d("addtesdes", String.valueOf(decimal));
*/
                     }

                     if (outhex.contains("C3AA")) {
                         final int min = 20;
                         final int max = 80;
                         final int random = new Random().nextInt((max - min) + 1) + min;
                         String speed = outhex.substring(140, 144);
                         Log.d("addtesig", speed);
                         int decimal = Integer.parseInt(speed, 16);
                         TextView speedview = (TextView) findViewById(R.id.speed);
                         speedview.setText(String.valueOf(random));

                     }
                 }
                    {
                  //       mConnectedThread.write(bytes);
                }

                    String ignition= "0";
                    String total_mileage= "40";
                    String vehicle_mileage= "45";
                    String total_fuel_consumption= "50";
                    String total_fuel_consumption_counted= "25";
                    String fuel_level_percent= "60";
                    String fuel_level_liters= "12";
                    String engine_speed_RPM= "800";
                    String engine_temperature= "37";
                    String vehicle_speed= "600";
                    String accelaration_pedal_position= "1";
                    String cng_level_percent= "20";
                    String total_cng_consumption= "56";
                    String engine_is_working_on_cng= "1";
                    String oil_pressure_level= "5";
                    String front_left_door= "1";
                    String front_right_door= "0";
                    String rear_right_door= "0";
                    String rear_left_door= "0";
                    String trunk_cover= "1";
                    String engine_cover_hood= "1";
                    String latitude= "6.7777766";
                    String longitude= "9.88888888";
                    String company_id= "3";
                    String user_id= "1";

        dbHandler.addNewData(ignition,
                total_mileage,
                vehicle_mileage,
                total_fuel_consumption,
                total_fuel_consumption_counted,
                fuel_level_percent,
                fuel_level_liters,
                engine_speed_RPM,
                engine_temperature,
                vehicle_speed,
                accelaration_pedal_position,
                cng_level_percent,
                total_cng_consumption,
                engine_is_working_on_cng,
                oil_pressure_level,
                front_left_door,
                front_right_door,
                rear_right_door,
                rear_left_door,
                trunk_cover,
                engine_cover_hood,
                latitude,
                longitude,
                company_id,
                user_id);

                }

                if(msg.what == CONNECTING_STATUS){
                    if(msg.arg1 == 1) {
                        // mBluetoothStatus.setText("Connected to Device: " + msg.obj);
                        Log.d("addcone","Connected to"+msg.obj);
                  //      mConnectedThread.cancel();
                   //     Intent intent = new Intent (BluetoothActivity.this, DetailActivity.class);
                    //    startActivity(intent);

                       // Byte sendm= {70,77,66,88,-86,-86,-86,-86,0,34,0,2,0,0,-49,121,13,10};
                        byte[] bytes ={70,77,66,88,-86,-86,-86,-86,0,34,0,2,0,0,-49,121,13,10};
                            mConnectedThread.write(bytes);

/*
                            while(true){
                                Log.d("loop","running");
                            }
*/

                    }
                    else {
                        Log.d("addcone", "Failed con");
                        Toast.makeText(DetailActivity.this, "Couldn't Connect", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (DetailActivity.this, BluetoothActivity.class);
                        startActivity(intent);

                    }
                    //    mBluetoothStatus.setText("Connection Failed");
                }
            }
        };

        if(!mBTAdapter.isEnabled()) {
            Toast.makeText(getBaseContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();
            return;
        }
        // Get the device MAC address, which is the last 17 chars in the View
        //info.substring(info.length() - 17);
        //    .substring(0,info.length() - 17);

        // Spawn a new thread to avoid blocking the GUI one
        new Thread()
        {
            @Override
            public void run() {
                boolean fail = false;

                BluetoothDevice device = mBTAdapter.getRemoteDevice(singleToneClass.getData());

                try {
                    mBTSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                try {
                    mBTSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        mBTSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        //           Toast.makeText(getActivityContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }
                if(!fail) {
                    mConnectedThread = new ConnectedThread(mBTSocket, mHandler);
                    mConnectedThread.start();

                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, "name")
                            .sendToTarget();
                }
            }
        }.start();

        final Handler bhandler = new Handler();
        final int delay = 5000; // 1000 milliseconds == 1 second

        bhandler.postDelayed(new Runnable() {
            public void run() {
                System.out.println("myHandler: here!"); // Do your work here
                byte[] bytes ={70,77,66,88,-86,-86,-86,-86,0,34,0,2,0,0,-49,121,13,10};
                mConnectedThread.write(bytes);

                bhandler.postDelayed(this, delay);
            }
        }, delay);

        final Handler apihandler = new Handler();
        final int apidelay = 5000; // 1000 milliseconds == 1 second

        apihandler.postDelayed(new Runnable() {
            public void run() {
                System.out.println("api handler"); // Do your work here
                byte[] bytes ={70,77,66,88,-86,-86,-86,-86,0,34,0,2,0,0,-49,121,13,10};
                // postDataUsingVolley("nameEdt.getText().toString()", "jobEdt.getText().toString()");
                try {
                    postData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bhandler.postDelayed(this, apidelay);
            }
        }, delay);
    //    maConnectedThread.write("[B@857ca29");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_detail);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e("TAG", "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }
/*

    private void postDataUsingVolley(String name, String job) {
        // url to post our data
        String url = "https://reqres.in/api/users";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String apiresponse) {
              Log.d("apiresoinded",apiresponse);
                // on below line we are displaying a success toast message.
                Toast.makeText(DetailActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(apiresponse);

                    // below are the strings which we
                    // extract from our json object.
                    Log.d("apirses", String.valueOf(respObj));
                    // on below line we are setting this string s to our text view.

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(DetailActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected JSONObject getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("vehicle_details", "[{\"ignition\":\"0\",\"total_mileage\":\"40\",\"vehicle_mileage\":\"45\",\"total_fuel_consumption\":\"50\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"3\",\"user_id\":\"1\"},{\"ignition\":\"1\",\"total_mileage\":\"30\",\"vehicle_mileage\":\"45\",\"total_fuel_consumption\":\"50\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"3\",\"user_id\":\"1\"}] ");
                String str = "[{\"No\":\"17\",\"Name\":\"Andrew\"},{\"No\":\"18\",\"Name\":\"Peter\"}, {\"No\":\"19\",\"Name\":\"Tom\"}]";
                try {
                    JSONArray parray = new JSONArray(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // at last we are
                // returning our params.
                System.out.println(params);
              //  return params;
                JSONObject student1 = new JSONObject();
                try {
                    student1.put("id", "3");
                    student1.put("name", "NAME OF STUDENT");
                    student1.put("year", "3rd");
                    student1.put("curriculum", "Arts");
                    student1.put("birthday", "5/5/1993");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JSONObject student2 = new JSONObject();
                try {
                    student2.put("id", "2");
                    student2.put("name", "NAME OF STUDENT2");
                    student2.put("year", "4rd");
                    student2.put("curriculum", "scicence");
                    student2.put("birthday", "5/5/1993");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                JSONArray jsonArray = new JSONArray();

                jsonArray.put(student1);
                jsonArray.put(student2);

                JSONObject studentsObj = new JSONObject();
                try {
                    studentsObj.put("Students", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                String jsonStr = studentsObj.toString();

                System.out.println("jsonString: "+jsonStr);
                return studentsObj;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
*/
public void postData() throws JSONException {
    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
    dbHandler.getdata();
    JSONObject object = new JSONObject("{\"vehicle_details\":[{\"ignition\":\"0\",\"total_mileage\":\"40\",\"vehicle_mileage\":\"20\",\"total_fuel_consumption\":\"5\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"4\",\"user_id\":\"2\"},{\"ignition\":\"1\",\"total_mileage\":\"30\",\"vehicle_mileage\":\"45\",\"total_fuel_consumption\":\"50\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"4\",\"user_id\":\"1\"}] }");
 //   object="[{\"ignition\":\"0\",\"total_mileage\":\"40\",\"vehicle_mileage\":\"45\",\"total_fuel_consumption\":\"50\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"3\",\"user_id\":\"1\"},{\"ignition\":\"1\",\"total_mileage\":\"30\",\"vehicle_mileage\":\"45\",\"total_fuel_consumption\":\"50\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"3\",\"user_id\":\"1\"}]"
    //input your API parameters
    //   object.put("parameter","value");
    // object.put("parameter","value");

    Log.d("postreq", String.valueOf(object));
    // Enter the correct url for your api service site
    String url = "https://ogesinfotech.com/crt_app/get_data.php?p=1";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response);
                    System.out.println("response");

                    //       resultTextView.setText("String Response : "+ response.toString());
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
       //     resultTextView.setText("Error getting response");
        }
    });
    requestQueue.add(jsonObjectRequest);
}


}