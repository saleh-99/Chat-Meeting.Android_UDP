package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment ChatFragment ;
    Fragment MapFragment ;
    Fragment  OnlineUserFragment ;
    public static BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment =new MapFragment();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);




        ChatFragment =new ChatFragment();
        OnlineUserFragment = new OnlineUserFragment();

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, ChatFragment)
                .add(R.id.fragment_container,MapFragment)
                .add(R.id.fragment_container,OnlineUserFragment)
                .hide(MapFragment).hide(OnlineUserFragment)
                .commit();


        bottomNavigationView.getMenu().getItem(0).setTitle("Chat");
        bottomNavigationView.getMenu().getItem(1).setTitle("");
        bottomNavigationView.getMenu().getItem(2).setTitle("");


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                   // Fragment selectedFragment =null;
                    switch (item.getItemId()){
                        case R.id.nav_chat:
                            if(ChatFragment.isHidden()){
                                fragmentManager.beginTransaction()
                                        .show(ChatFragment).hide(MapFragment).hide(OnlineUserFragment).commit();
                                item.setTitle("Chat");
                                bottomNavigationView.getMenu().getItem(1).setTitle("");
                                bottomNavigationView.getMenu().getItem(2).setTitle("");
                            }
                            //selectedFragment = ChatFragment;
                            break;
                        case R.id.nav_map:
                            if(MapFragment.isHidden()){
                                fragmentManager.beginTransaction()
                                        .show(MapFragment).hide(ChatFragment).hide(OnlineUserFragment).commit();
                                item.setTitle("Map");
                                bottomNavigationView.getMenu().getItem(0).setTitle("");
                                bottomNavigationView.getMenu().getItem(2).setTitle("");
                            }
                            //selectedFragment = MapFragment;
                            break;
                        case R.id.nav_online_user:
                            if(OnlineUserFragment.isHidden()){
                                fragmentManager.beginTransaction()
                                        .show(OnlineUserFragment).hide(MapFragment).hide(ChatFragment).commit();
                                item.setTitle("Online");
                                bottomNavigationView.getMenu().getItem(0).setTitle("");
                                bottomNavigationView.getMenu().getItem(1).setTitle("");
                            }
                           // selectedFragment = OnlineUserFragment;
                            break;
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        if(ChatFragment.isHidden()) {
            bottomNavigationView.setSelectedItemId(R.id.nav_chat);
        }else{
            ChatFragment.onDestroy();
            MapFragment.onDestroy();
            OnlineUserFragment.onDestroy();
            this.finish();
        }
    }
}
