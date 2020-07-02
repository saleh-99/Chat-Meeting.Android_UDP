package com.example.test2;

import com.google.android.gms.maps.model.Marker;

public class OnlineUser {

    public String name;
    public int Img;
    public double X,Y;

    public OnlineUser(String name,double X,double Y,int Img) {
        this.name = name;
        this.X = X;
        this.Y = Y;
        this.Img = Img;
    }
}
