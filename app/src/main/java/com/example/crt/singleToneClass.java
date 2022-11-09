package com.example.crt;

public class singleToneClass {
    String s;
    String ignite;
    String rpm;
    String pedal;
    String speed;

    String battery_voltage;
    String altitude;
    String   engine_temperature;
    String   engine_coolant_temperature;
    String   engine_oil_temperature;
    String engineLoad;
    String gsmstrength;


    String throttle_position;
    String odometer;

    Integer f;
    private static final singleToneClass ourInstance = new singleToneClass();
    public static singleToneClass getInstance() {
        return ourInstance;
    }
    private singleToneClass() {
    }
    public void setData(String s) {
        this.s = s;
    }
    public String getData() {
        return s;
    }

    public void setFrag(Integer f){this.f=f ;}
    public Integer getFrag() {
        return f;
    }


    public void setIgnition(String ignite){this.ignite=ignite ;}
    public String getIgnition() {
        return ignite;
    }


    public void setRpm(String rpm){this.rpm=rpm ;}
    public String getRpm() {
        return rpm;
    }

    public void setBattery(String battery_voltage){this.battery_voltage=battery_voltage ;}
    public String getBattery() {
        return battery_voltage;
    }

    public void setAltitude(String altitude){this.altitude=altitude;}
    public String getAltitude(){return altitude;}

    public void setEnginetemp(String engine_temperature){this.engine_temperature= engine_temperature;}
    public String getEngine_temperature(){return engine_temperature;}

    public void setEngine_coolant_temperature(String engine_coolant_temperature){this.engine_coolant_temperature= engine_coolant_temperature;}
    public String getEngine_coolant_temperature(){return  engine_coolant_temperature;}

    public void setEngine_oil_temperature(String engine_oil_temperature){this.engine_oil_temperature =engine_oil_temperature;}
    public  String getEngine_oil_temperature(){return  engine_oil_temperature;}

    public  void  setOdometer(String odometer){this.odometer=odometer;}
    public String getOdometer(){return odometer;}

    public void setThrottle_position(String throttle_position){this.throttle_position=throttle_position;}
    public String getThrottle_position(){return  throttle_position;}

    public void setEngineLoad(String engineLoad){this.engineLoad= engineLoad;}
    public  String getEngineLoad(){return engineLoad;}

    public void setGsmstrength(String gsmstrength){this.gsmstrength=gsmstrength;}
    public String getGsmstrength(){return gsmstrength;}


    public void setSpeed(String speed){this.ignite=speed ;}
    public String getSpeed() {
        return speed;
    }


    public void setPedal(String pedal){this.ignite=pedal ;}
    public String getPedal() {
        return pedal;
    }


}