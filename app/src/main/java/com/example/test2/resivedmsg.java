package com.example.test2;

import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class resivedmsg {
    int Me_or,img ;
    String msg,name;
    Date time;
    public static final String DATE_FORMAT_1 = "hh:mm a";
    public resivedmsg(String msg, String name, int Me_or,int img){
        this.Me_or =Me_or;
        this.msg =msg;
        this.name =name;
        this.img =img;
        time = new Date();
    }
    public String getMsg(){
        return msg;
    }
    public String getName(){
        return name;
    }
    public int getMe_or(){return Me_or;}
    public int getImg(){return img;}

    public String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_1);
        dateFormat.setTimeZone(TimeZone.getDefault());
        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);

        //return java.text.DateFormat.getDateTimeInstance().format(time).;
    }

}
