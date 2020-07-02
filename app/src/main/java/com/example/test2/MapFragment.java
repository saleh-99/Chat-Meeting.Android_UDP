package com.example.test2;

import android.content.Intent;
import android.content.res.Resources;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener {
    private SupportMapFragment mSupportMapFragment;
    private static GoogleMap map;
    private static ArrayList<Marker> markers;
    private static Marker markerssend;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapwhere);
        markers = new ArrayList<>();
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapwhere, mSupportMapFragment).commit();
        }

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(this);
        }
        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (googleMap != null) {
            map = googleMap;
            try {
                boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json));
                if (!success) {
                    map.getUiSettings().setTiltGesturesEnabled(false);
                }
            } catch (Resources.NotFoundException e) {

            }

            map.setMyLocationEnabled(true);
            // map.getUiSettings().setAllGesturesEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            map.setOnMapClickListener(this);
            map.setOnMapLongClickListener(this);



        }

    }


    static void MoveCamera(int position) {
        OnlineUser user = OnlineUserFragment.Useres.get(position);
        LatLng sydney = new LatLng(user.X, user.Y);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 10);
        map.animateCamera(cameraUpdate);
    }
    static void MoveCamera(double X, double Y) {
        LatLng latLng = new LatLng(X,Y);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        map.animateCamera(cameraUpdate);
    }

    static void NewMarker(int position) {
        if (map != null) {
            OnlineUser user = OnlineUserFragment.Useres.get(position);
            LatLng latLng = new LatLng(user.X, user.Y);

            if (markers.size() <= position) {
                markers.add(position, map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(user.name)));
            } else {
                markers.get(position).setPosition(latLng);
            }
        }

    }

    static void NewMarker(double X, double Y) {
        LatLng latLng = new LatLng(X, Y);
        if (markerssend == null)
            markerssend = map.addMarker(new MarkerOptions().position(latLng).title(latLng.toString()));
        else {
            markerssend.setPosition(latLng);
        }
    }



    @Override
    public boolean onMarkerClick(final Marker marker) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + marker.getPosition().latitude + "," + marker.getPosition().longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        return true;
    }
    @Override
    public void onMapClick(LatLng latLng) {
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }
    @Override
    public void onMapLongClick(LatLng latLng) {
        Client.SendMarker = new MarkerOptions().position(latLng).title(latLng.toString());
    }

}


