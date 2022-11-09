package com.example.crt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
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
    DataProcessor dataProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDetail.toolbar);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio
        dataProcessor = new DataProcessor(getApplicationContext());


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


                String ignition ="";
                String total_mileage= "0";
                String vehicle_mileage= "0";
                String total_fuel_consumption= "0";
                String total_fuel_consumption_counted= "5";
                String fuel_level_percent= "0";
                String fuel_level_liters= "2";
                String engine_speed_RPM= "0";
                String engine_temperature= "37";
                String vehicle_speed= "0";
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
                        if (outhex.contains("C35000")) {
                            int valpos= outhex.indexOf("C35000", 0);
                            ignition = outhex.substring(valpos+6, valpos+8);

                            ImageView igview = (ImageView) findViewById(R.id.ignition);


                            if (ignition.equals("00")) {
                                Log.d("addtesigout", outhex);
                                ignition= "0";
                                igview.setImageResource(R.drawable.ic_gcaronoff);
                                igview.setContentDescription("");
                                singleToneClass.setIgnition("0");

                            } else {
                                igview.setImageResource(R.drawable.ic_gcaron);
                                ignition ="1";
                                singleToneClass.setIgnition("1");
                            }
                    /*    Log.d("addtes", outhex.substring(170,174));
                        int decimal=Integer.parseInt(outhex.substring(170,174),16);
                        Log.d("addtesdes", String.valueOf(decimal));
*/
                        }

                        if (outhex.contains("9CEA")) {
                            int valpos= outhex.indexOf("9CEA", 0);
                            String speed = outhex.substring(valpos+6, valpos+10);
                            Log.d("addtesig", "speed");
                            int decimal = Integer.parseInt(speed, 16);
                            TextView speedview = (TextView) findViewById(R.id.speed);
                            speedview.setText(String.valueOf(decimal));
                            singleToneClass.setSpeed(String.valueOf(decimal));

                        }

                        if (outhex.contains("C36E")) {
                            int valpos= outhex.indexOf("C36E", 0);
                            String speed = outhex.substring(valpos+6, valpos+8);
                            //  Log.d("Gsm", speed);
                            int decimal = Integer.parseInt(speed, 16);
                            TextView speedview = (TextView) findViewById(R.id.gsmstrength);
                            String settext= String.valueOf(decimal) + "/ 5";
                            speedview.setText(String.valueOf(settext) );
                            singleToneClass.setGsmstrength(String.valueOf(decimal));

                        }

                        if (outhex.contains("C3A0")) {

                            int valpos= outhex.indexOf("C3A0", 0);
                            String speed = outhex.substring(valpos+6, valpos+10);
                            Log.d("batvol", speed);
                            int decimal = Integer.parseInt(speed, 16)/1000;
                            TextView speedview = (TextView) findViewById(R.id.enginetempvalue);
                            String setstring = String.valueOf(decimal);
                            setstring = setstring + " V";
                            speedview.setText(setstring);
                            singleToneClass.setBattery(String.valueOf(decimal));

                        }


                        if (outhex.contains("9CE003")) {

                            int valpos= outhex.indexOf("9CE003", 0);
                            String speed = outhex.substring(valpos+6, valpos+10);

                            int decimal = Integer.parseInt(speed, 16);
                            TextView speedview = (TextView) findViewById(R.id.enginespeed);
                            String setstring = String.valueOf(decimal);
                            setstring = setstring + " RPM";
                            speedview.setText(setstring);
                            singleToneClass.setRpm(String.valueOf(decimal));

                        }


                        if (outhex.contains("9CB801")) {

                            int valpos= outhex.indexOf("9CB801", 0);
                            String speed = outhex.substring(valpos+6, valpos+8);

                            int decimal = Integer.parseInt(speed, 16);
                            TextView speedview = (TextView) findViewById(R.id.workingoncngvalue);
                            String setstring = String.valueOf(decimal);
                            setstring = setstring + " C";
                            speedview.setText(setstring);
                            singleToneClass.setEngine_coolant_temperature(String.valueOf(decimal));

                        }

                        if (outhex.contains("9CAE00")) {

                            int valpos= outhex.indexOf("9CAE00", 0);
                            String speed = outhex.substring(valpos+6, valpos+8);

                            int decimal = Integer.parseInt(speed, 16);
                            TextView speedview = (TextView) findViewById(R.id.engineload);
                            String setstring = String.valueOf(decimal);
                            setstring = setstring + " %";
                            speedview.setText(setstring);
                            singleToneClass.setEngineLoad(String.valueOf(decimal));

                        }

                        if (outhex.contains("9D1200")) {

                            int valpos= outhex.indexOf("9D1200", 0);
                            String speed = outhex.substring(valpos+6, valpos+8);

                            int decimal = Integer.parseInt(speed, 16);
                            TextView speedview = (TextView) findViewById(R.id.throttleposition);
                            String setstring = String.valueOf(decimal);
                            setstring = setstring + " %";
                            speedview.setText(setstring);
                            singleToneClass.setThrottle_position(String.valueOf(decimal));

                        }

                        if (outhex.contains("9DBC00")) {

                            int valpos= outhex.indexOf("9DBC00", 0);
                            String speed = outhex.substring(valpos+6, valpos+8);

                            int decimal = Integer.parseInt(speed, 16);
                            TextView speedview = (TextView) findViewById(R.id.engoil);
                            String setstring = String.valueOf(decimal);
                            setstring = setstring + " C";
                            speedview.setText(setstring);
                            singleToneClass.setEngine_oil_temperature(String.valueOf(decimal));

                        }

                        if (outhex.contains("9CB801")) {

                            int valpos= outhex.indexOf("9CB801", 0);
                            String speed = outhex.substring(valpos+6, valpos+8);

                            //  int decimal = Integer.parseInt(speed, 16);
                            short decimal = (short) Integer.parseInt(speed,16);
                            TextView speedview = (TextView) findViewById(R.id.coolanttemp);
                            String setstring = String.valueOf(decimal);
                            setstring = setstring + " C";
                            speedview.setText(setstring);
                            singleToneClass.setEngine_coolant_temperature(String.valueOf(decimal));

                        }




                        if (outhex.contains("C3F0")) {

                            int valpos= outhex.indexOf("C3F0", 0);
                            String speed = outhex.substring(valpos+6, valpos+14);

                            int decimal = Integer.parseInt(speed, 16);
                            TextView speedview = (TextView) findViewById(R.id.textView27);
                            String setstring = String.valueOf(decimal);
                            int kms= decimal/1000;
                            setstring = String.valueOf(kms) + " km";
                            speedview.setText(setstring);
                            singleToneClass.setOdometer(String.valueOf(kms));

                        }

                        if (outhex.contains("010B09")) {

                            int valpos= outhex.indexOf("010B09", 0);
                            String speed = outhex.substring(valpos+6, valpos+22);

                            long longHex = parseUnsignedHex(speed);
                            double d = Double.longBitsToDouble(longHex);
                            System.out.println(d);

                            Log.d("010B",speed);


                            Log.d("double", String.valueOf(d));
                            TextView speedview = (TextView) findViewById(R.id.cnglevelvalue);
                            String setstring = String.valueOf(d);
                            setstring = setstring + " m";
                            speedview.setText(setstring);
                            singleToneClass.setAltitude(String.valueOf(d));

                        }

                    }
                    else  {
                        //       mConnectedThread.write(bytes);
                    }



                    dbHandler.addNewData(singleToneClass.getIgnition(),
                            singleToneClass.getRpm(),

                            singleToneClass.getBattery(),
                            singleToneClass.getAltitude(),
                            singleToneClass.getEngine_temperature(),

                            singleToneClass.getEngine_coolant_temperature(),
                            singleToneClass.getEngine_oil_temperature(),
                            singleToneClass.getEngineLoad(),
                            singleToneClass.getSpeed(),
                            singleToneClass.getPedal(),
                            singleToneClass.getThrottle_position(),
                            singleToneClass.getOdometer(),
                            singleToneClass.getGsmstrength(),
                            dataProcessor.getCompanyidno(Config.COMPANY_IDNO),
                            dataProcessor.getUserId(Config.COMPANY_ID)


//                            "4", //add the code to get company id (for mariya)
//                            "25"        //add the code to get user id (for mariya)
                    );

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
                System.out.println("myHandler: here!");
                byte[] bytes ={70,77,66,88,-86,-86,-86,-86,0,34,0,2,0,0,-49,121,13,10}; // for simple IO
                mConnectedThread.write(bytes);

                byte[] gnss ={70,77,66,88,-86,-86,-86,-86,0,30,0,2,0,0,-54,41,13,10}; //for gnss
                mConnectedThread.write(gnss);

                byte[] obd ={70,77,66,88,-86,-86,-86,-86,0,36,0,2,0,0,-49,-15,13,10};
                mConnectedThread.write(obd);

                bhandler.postDelayed(this, delay);
            }
        }, delay);

        final Handler apihandler = new Handler();
        final int apidelay = 300000; // 1000 milliseconds == 1 second

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.logitout) {
            try {
                mBTSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this,UserLoginpage.class);
            startActivity(intent);
            return true;
        }




        return super.onOptionsItemSelected(item);
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

    public static long parseUnsignedHex(String text) {
        if (text.length() == 16) {
            return (parseUnsignedHex(text.substring(0, 1)) << 60)
                    | parseUnsignedHex(text.substring(1));
        }
        return Long.parseLong(text, 16);
    }

    public void postData() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Cursor rcursor= dbHandler.getdata();
        rcursor.moveToFirst();
        // String sendata= "{\"vehicle_details\":[{\"ignition\":\""+ rcursor.getString(1)+"\",\"total_mileage\":\"40\",\"vehicle_mileage\":\"20\",\"total_fuel_consumption\":\"5\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\""+rcursor.getString(8)+"\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"4\",\"user_id\":\"2\"}] }";
        String sendata= "{\n" +
                "\"vehicle_details\": [\n" +
                "    {\n" +
                "      \"ignition\": \""+rcursor.getString(1)+"\",\n" +
                "      \"altitude\":\""+rcursor.getString(4)+"\",\n" +
                "      \"battery_voltage\":\""+rcursor.getString(3)+"\",\n" +
                "      \"engine_speed\":\""+rcursor.getString(2)+"\",\n" +
                "      \"enginecoolant_temperature\":\""+rcursor.getString(6)+"\",\n" +
                "      \"engineload\":\""+rcursor.getString(8)+"\",\n" +
                "      \"coolant_temperature\":\""+rcursor.getString(6)+"\",\n" +
                "      \"throttleposition\":\""+rcursor.getString(11)+"\",\n" +
                "      \"GSM_signal_strength\":\""+rcursor.getString(13)+"\",\n" +
                "      \"engineoil_temperature\":\""+rcursor.getString(7)+"\",\n" +
                "      \"odometer\":\""+rcursor.getString(12)+"\",\n" +
                "      \"speed\":\""+rcursor.getString(9)+"\",\n" +
                "      \"acceleration_pedal_position\":\""+rcursor.getString(10)+"\",\n" +
                "       \"company_id\":\""+rcursor.getString(14)+"\",\n" +
                "      \"user_id\":\""+rcursor.getString(16)+"\",\n" +
                "      \"reading_time\":\""+rcursor.getString(15)+"\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        //   JSONObject object = new JSONObject("{\"vehicle_details\":[{\"ignition\":\"0\",\"total_mileage\":\"40\",\"vehicle_mileage\":\"20\",\"total_fuel_consumption\":\"5\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"4\",\"user_id\":\"2\"}] }");
        JSONObject object = new JSONObject(sendata);
        //   object="[{\"ignition\":\"0\",\"total_mileage\":\"40\",\"vehicle_mileage\":\"45\",\"total_fuel_consumption\":\"50\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"3\",\"user_id\":\"1\"},{\"ignition\":\"1\",\"total_mileage\":\"30\",\"vehicle_mileage\":\"45\",\"total_fuel_consumption\":\"50\",\"total_fuel_consumption_counted\":\"25\",\"fuel_level_percent\":\"60\",\"fuel_level_liters\":\"12\",\"engine_speed_RPM\":\"800\",\"engine_temperature\":\"37\",\"vehicle_speed\":\"600\",\"accelaration_pedal_position\":\"1\",\"cng_level_percent\":\"20\",\"total_cng_consumption\":\"56\",\"engine_is_working_on_cng\":\"1\",\"oil_pressure_level\":\"5\",\"front_left_door\":\"1\",\"front_right_door\":\"0\",\"rear_right_door\":\"0\",\"rear_left_door\":\"0\",\"trunk_cover\":\"1\",\"engine_cover_hood\":\"1\",\"latitude\":\"6.7777766\",\"longitude\":\"9.88888888\",\"company_id\":\"3\",\"user_id\":\"1\"}]"
        //input your API parameters
        //   object.put("parameter","value");
        // object.put("parameter","value");

        Log.d("postreq", String.valueOf(object));
        // Enter the correct url for your api service site
        String url = Config.BASE_URL + "get_data.php?p=1";
        Log.e("urlbase", url);
//        String url = "https://ogesinfotech.com/crt_app/get_data.php?p=1"; //add code to get post api for mariya
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