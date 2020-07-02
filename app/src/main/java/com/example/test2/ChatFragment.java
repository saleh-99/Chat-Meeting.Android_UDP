package com.example.test2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements Runnable {

    private RecyclerView console;
    private RecyclerView.Adapter consoleAdapter;
    private RecyclerView.LayoutManager consoleLayoutManager;
    private ArrayList<resivedmsg> Messages;

    private int Port;
    private String Name;
    private String address = "92.241.48.15";
    private Client client;
    private FusedLocationProviderClient fusedLocationClient;
    private boolean running;
    private Thread run, listen;
    private Button btnsend;
    private EditText edit;
    private MarkerOptions marker;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        Intent incoming = getActivity().getIntent();
        Port = incoming.getIntExtra("Port", 0); // Get the post
        Name = incoming.getStringExtra("Name"); // Get Name
        edit = v.findViewById(R.id.Sendtext); // inPut Chat
        btnsend = v.findViewById(R.id.send); // Send Button
        Messages = new ArrayList<>(); // list of Messages resived and send
        client = new Client(Name, address, Port); // setup a clint class to send and resevd (datagram)
        run = new Thread(this, "Running"); // the run Thread
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


        console = v.findViewById(R.id.console);
        console.setNestedScrollingEnabled(false);
        consoleLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayout.VERTICAL, false);
        console.setLayoutManager(consoleLayoutManager);
        consoleAdapter = new ConsoleAdapter(Messages, getContext());
        console.setAdapter(consoleAdapter);

        btnsend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                send(edit.getText().toString(), true);
            }
        });

        boolean connect = client.openConnection(address);
        if (!connect) {
            con("Connection failed!", "System", 0, -1);
        }

        con("Attempting a connection to " + address + ":" + Port + ", user: " + Name, "system", 0, -1);

        String connection = "/c/" + Name + "/e/";
        client.send(connection.getBytes());


        running = true;
        run.start();

        return v;
    }

    @Override
    public void run() {
        listen();
    }

    private void listen() {
        listen = new Thread("Listen") {
            @Override
            public void run() {
                while (running) {
                    String message = client.receive();
                    if (message.startsWith("/c/")) {
                        client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
                        con("Successfully connected to server! ID: " + client.getID(), "system", 0, -1);
                    } else if (message.startsWith("/m/")) {
                        String name = message.split(":")[0].substring(3);
                        String text = message.substring(3);
                        text = text.split("/e/")[0];
                        text = text.substring(name.length() + 1);
                        if (name.equals(Name)) {
                            con(text, "me", 0, -1);
                        } else con(text, name, 1, OnlineUserFragment.FindUser(name));
                    } else if (message.startsWith("/i/")) {
                        String text = "/i/" + client.getID() + "/e/";
                        send(text, false);
                        getLoction();
                        CheckMarker();
                    } else if (message.startsWith("/u/")) {
                        String Name = message.split("/u/|/e/")[1];
                        OnlineUserFragment.NewUser(Name);
                    } else if (message.startsWith("/l/")) {
                        SetUsersLocation(message);
                    } else if (message.startsWith("/r/")) {
                        String Name = message.split("/r/|/e/")[1];
                        OnlineUserFragment.RemoveUser(Name);
                    } else if (message.startsWith("/f/")) {
                        SetMarker(message);
                    }
                }
            }
        };
        listen.start();
    }


    private void send(String message, boolean text) {
        if (message.equals(""))
            return;
        if (text) {
            message = client.getName() + ": " + message;
            message = "/m/" + message + "/e/";
            edit.setText("");
        }
        client.send(message.getBytes());

    }


    private void con(String msg, String Name, int Me_or, int Img) {
        resivedmsg NewMsg = new resivedmsg(msg, Name, Me_or, Img);
        Messages.add(NewMsg);
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                consoleAdapter.notifyDataSetChanged();
                console.smoothScrollToPosition(Messages.size());
            }
        });

    }

    private void SetUsersLocation(String message) {
        final String Name = message.split("/l/|/i/")[1];
        final float X = Float.parseFloat(message.substring(message.indexOf("/i/") + 3, message.lastIndexOf("/y/")));
        final float Y = Float.parseFloat(message.substring(message.indexOf("/y/") + 3, message.lastIndexOf("/e/")));
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                OnlineUserFragment.SetLocation(Name, X, Y);
            }
        });
    }

    private void CheckMarker() {
        if (Client.SendMarker != marker && Client.SendMarker != null) {
            String m = "/f/" + client.getName() + "/l/" + Client.SendMarker.getPosition().latitude + "/y/" + Client.SendMarker.getPosition().longitude + "/e/";
            send(m, false);
            marker = Client.SendMarker;
        }
    }

    private void SetMarker(String message) {
        String name = message.split("/f/|/l/")[1];
        final double X = Double.parseDouble(message.substring(message.indexOf("/l/") + 3, message.lastIndexOf("/y/")));
        final double Y = Double.parseDouble(message.substring(message.indexOf("/y/") + 3, message.lastIndexOf("/e/")));
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                MapFragment.NewMarker(X, Y);
            }
        });
        if (name.equals(Name)) {
            con(X + "/y/" + Y, "me", 2, -1);
        } else con(X + "/y/" + Y, name, 3, OnlineUserFragment.FindUser(name));
    }

    private void getLoction() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            double X = location.getLatitude();
                            double Y = location.getLongitude();
                            if (Client.X != X || Client.Y != Y) {
                                Client.X = X;
                                Client.Y = Y;
                                String string = "/l/" + client.getName() + "/i/" + Client.X + "/y/" + Client.Y + "/e/";
                                send(string, false);
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        String disconnect = "/d/" + client.getID() + "/e/";
        send(disconnect, false);
        client.close();
        super.onDestroy();
    }
}
