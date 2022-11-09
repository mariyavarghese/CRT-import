package com.example.crt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;


public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "vehicledb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "vehicle_details";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    private static final String   IGNITION= "igntition";
    private static final String   TOTAL_MILEAGE="total_mileage";
    private static final String   VEHICLE_MILEAGE="vehicle_mileage";
    private static final String   TOTAL_FUEL_CONSUUMPTION= "total_fuel_consumption";
    private static final String   TOTAL_FUEL_CONSUMPTION_COUNTED= "total_fuel_consumption_counted";
    private static final String   FUEL_LEVEL_PERCENT= "fuel_level_percent";
    private static final String   FUEL_LEVEL_LITERS="fuel_level_liters";
    private static final String   ENGINE_SPEED_RPM="engine_speed_rpm";
    private static final String   ENGINE_TEMPERATURE="engine_temperature";
    private static final String   VEHICLE_SPEED="vehicle_speed";
    private static final String   ACCELARATION_PEDAL_POSITION="accelaration_pedal_position";
    private static final String   CNG_LEVEL_PERCENT="cng_level_percent";
    private static final String   TOTAL_CNG_CONSUMPTION="total_cng_consumption";
    private static final String   ENGINE_IS_WORKING_ON_CNG="engine_is_working_on_cng";
    private static final String   OIL_PRESSURE_LEVEL="oil_pressure_level";
    private static final String   FRONT_LEFT_DOOR="front_left_door";
    private static final String   FRONT_RIGHT_DOOR="front_right_door";
    private static final String   REAR_RIGHT_DOOR="rear_right_door";
    private static final String   REAR_LEFT_DOOR="rear_left_door";
    private static final String   TRUNK_COVER="trunk_cover";
    private static final String   ENGINE_COVER_HOOD="engine_cover_hood";
    private static final String   LATITUDE="latitude";
    private static final String   LONGITUDE="longitude";
    private static final String   COMPANY_ID="company_id";
    private static final String   USER_ID="user_id";
    private static final String   DATA_TIME="data_time";
    private static final String   BATTERY_VOLTAGE="battery_voltage";
    private static final String   ALTITUDE="altitude";
    private static final String   THROTTLE_POSITION="throttle_position";
    private static final String   COOLANT_TEMPERATURE="coolant_temperature";
    private static final String   GSM_STRENGTH="gsm_strength";
    private static final String   ENGINE_COOLANT_TEMPERATURE="engine_coolant_temperature";
    private static final String   ENGINE_OIL_TEMPERATURE="engine_oil_temperature";
    private static final String   ENGINE_LOAD="engine_load";
    private static final String   ODOMETER="odometer";


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + IGNITION + " TEXT,"
                + ENGINE_SPEED_RPM + " TEXT,"
                + BATTERY_VOLTAGE + " TEXT,"
                + ALTITUDE + " TEXT,"
                + ENGINE_TEMPERATURE + " TEXT,"
                + ENGINE_COOLANT_TEMPERATURE + " TEXT,"
                + ENGINE_OIL_TEMPERATURE + " TEXT,"
                + ENGINE_LOAD + " TEXT,"
                + VEHICLE_SPEED + " TEXT,"
                + ACCELARATION_PEDAL_POSITION + " TEXT,"
                + THROTTLE_POSITION + " TEXT,"
                + ODOMETER + " TEXT,"
                + GSM_STRENGTH + " TEXT,"


                + COMPANY_ID + " TEXT,"
                + DATA_TIME + " TEXT,"
                + USER_ID + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewData( String   ignition,

                            String   engine_speed_RPM,
                            String battery_voltage,
                            String altitude,
                            String   engine_temperature,
                            String   engine_coolant_temperature,
                            String   engine_oil_temperature,
                            String engineLoad,
                            String   vehicle_speed,
                            String   accelaration_pedal_position,
                            String throttle_position,
                            String odometer,
                            String gsm,


                            String   company_id,
                            String   user_id) {
        Date currentTime = Calendar.getInstance().getTime();
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(IGNITION,ignition);
        values.put(ENGINE_SPEED_RPM,engine_speed_RPM);

        values.put(BATTERY_VOLTAGE,battery_voltage);
        values.put(ALTITUDE,altitude);
        values.put(ENGINE_TEMPERATURE,engine_temperature);
        values.put(ENGINE_COOLANT_TEMPERATURE,engine_coolant_temperature);
        values.put(ENGINE_OIL_TEMPERATURE,engine_oil_temperature);
        values.put(ENGINE_LOAD,engineLoad);
        values.put(VEHICLE_SPEED,vehicle_speed);
        values.put(ACCELARATION_PEDAL_POSITION,accelaration_pedal_position);
        values.put(THROTTLE_POSITION,throttle_position);
        values.put(ODOMETER,odometer);
        values.put(GSM_STRENGTH,gsm);
        values.put(COMPANY_ID,company_id);
        values.put(DATA_TIME, String.valueOf(currentTime));

        values.put(COMPANY_ID,company_id);
        values.put(USER_ID,user_id);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);
        Log.d("vehicle added",battery_voltage);
        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public Cursor getdata(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor resultSet = db.rawQuery("Select * from vehicle_details ORDER BY id DESC",null);
        return resultSet;
        //resultSet.moveToFirst();
        //  Log.d("rest",resultSet.getString(5));



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}