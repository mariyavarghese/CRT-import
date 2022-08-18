package com.example.crt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                + TOTAL_MILEAGE + " TEXT,"
                + VEHICLE_MILEAGE + " TEXT,"
                + TOTAL_FUEL_CONSUUMPTION + " TEXT,"
                + TOTAL_FUEL_CONSUMPTION_COUNTED + " TEXT,"
                + FUEL_LEVEL_PERCENT + " TEXT,"
                + FUEL_LEVEL_LITERS + " TEXT,"
                + ENGINE_SPEED_RPM + " TEXT,"
                + ENGINE_TEMPERATURE + " TEXT,"
                + VEHICLE_SPEED + " TEXT,"
                + ACCELARATION_PEDAL_POSITION + " TEXT,"
                + CNG_LEVEL_PERCENT + " TEXT,"
                + TOTAL_CNG_CONSUMPTION + " TEXT,"
                + ENGINE_IS_WORKING_ON_CNG + " TEXT,"
                + OIL_PRESSURE_LEVEL + " TEXT,"
                + FRONT_LEFT_DOOR + " TEXT,"
                + FRONT_RIGHT_DOOR + " TEXT,"
                + REAR_LEFT_DOOR + " TEXT,"
                + REAR_RIGHT_DOOR + " TEXT,"
                + ENGINE_COVER_HOOD + " TEXT,"
                + TRUNK_COVER + " TEXT,"
                + LATITUDE + " TEXT,"
                + LONGITUDE + " TEXT,"
                + COMPANY_ID + " TEXT,"
                + DATA_TIME + "TEXT,"
                + USER_ID + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewData( String   ignition,
                              String   total_mileage,
                              String   vehicle_mileage,
                              String   total_fuel_consumption,
                              String   total_fuel_consumption_counted,
                              String   fuel_level_percent,
                              String   fuel_level_liters,
                              String   engine_speed_RPM,
                              String   engine_temperature,
                              String   vehicle_speed,
                              String   accelaration_pedal_position,
                              String   cng_level_percent,
                              String   total_cng_consumption,
                              String   engine_is_working_on_cng,
                              String   oil_pressure_level,
                              String   front_left_door,
                              String   front_right_door,
                              String   rear_right_door,
                              String   rear_left_door,
                              String   trunk_cover,
                              String   engine_cover_hood,
                              String   latitude,
                              String   longitude,
                              String   company_id,
                              String   user_id) {

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
        values.put(TOTAL_MILEAGE,total_mileage);
        values.put(VEHICLE_MILEAGE,vehicle_mileage);
        values.put(TOTAL_FUEL_CONSUUMPTION,total_fuel_consumption);
        values.put(TOTAL_FUEL_CONSUMPTION_COUNTED,total_fuel_consumption_counted);
        values.put(FUEL_LEVEL_PERCENT,fuel_level_percent);
        values.put(FUEL_LEVEL_LITERS,fuel_level_liters);
        values.put(ENGINE_SPEED_RPM,engine_speed_RPM);
        values.put(ENGINE_TEMPERATURE,engine_temperature);
        values.put(VEHICLE_SPEED,vehicle_speed);
        values.put(ACCELARATION_PEDAL_POSITION,accelaration_pedal_position);
        values.put(CNG_LEVEL_PERCENT,cng_level_percent);
        values.put(TOTAL_FUEL_CONSUUMPTION,total_cng_consumption);
        values.put(ENGINE_IS_WORKING_ON_CNG,engine_is_working_on_cng);
        values.put(OIL_PRESSURE_LEVEL,oil_pressure_level);
        values.put(FRONT_LEFT_DOOR,front_left_door);
        values.put(FRONT_RIGHT_DOOR,front_right_door);
        values.put(REAR_RIGHT_DOOR,rear_right_door);
        values.put(REAR_LEFT_DOOR,rear_left_door);
        values.put(TRUNK_COVER,trunk_cover);
        values.put(ENGINE_COVER_HOOD,engine_cover_hood);
        values.put(LATITUDE,latitude);
        values.put(LONGITUDE,longitude);
        values.put(COMPANY_ID,company_id);
        values.put(USER_ID,user_id);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    public void getdata(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor resultSet = db.rawQuery("Select * from vehicle_details",null);
        resultSet.moveToFirst();
        Log.d("rest",resultSet.getString(5));



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}