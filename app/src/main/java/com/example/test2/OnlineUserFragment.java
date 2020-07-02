package com.example.test2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.xml.namespace.QName;

public class OnlineUserFragment extends Fragment {

    private static RecyclerView List;
    private static RecyclerView.Adapter ListAdapter ;
    private RecyclerView.LayoutManager ListLayoutManager ;
    static ArrayList<OnlineUser> Useres;
    static Random random;

    @SuppressLint("WrongConstant")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_online_user, container, false);
        random = new Random();
        Useres = new ArrayList<>();

        List =v.findViewById(R.id.online_user_view);
        List.setNestedScrollingEnabled(false);
        ListLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayout.VERTICAL,false);
        List.setLayoutManager(ListLayoutManager);
        ListAdapter = new OnlineUserAdapter(Useres);

       ((OnlineUserAdapter) ListAdapter).setOnItemClickListener(new OnlineUserAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_map);
                MapFragment.MoveCamera(position);
            }
        });
        List.setAdapter(ListAdapter);
        return v;
    }
    static void NewUser(String Name){
        for(OnlineUser user : Useres){
            if(user.name.equals(Name))
                return;
        }
        Useres.add(new OnlineUser(Name,0,0,random.nextInt(26)));
                try {
                    ListAdapter.notifyDataSetChanged();
                    List.smoothScrollToPosition(Useres.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

        static int FindUser(String Name){
            for(OnlineUser user : Useres){
                if(user.name.equals(Name))
                    return user.Img;
            }
            return -1;
        }

    static void SetLocation(String Name, float X, float Y){
        for(OnlineUser user:Useres){
            if(user.name.equals(Name)){
                user.X =X;
                user.Y =Y;
                try{
                MapFragment.NewMarker(Useres.indexOf(user));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
    static void RemoveUser(String Name){
        for(OnlineUser user:Useres){
            if(user.name.equals(Name)){
                Useres.remove(user);
                try {
                    ListAdapter.notifyDataSetChanged();
                    List.smoothScrollToPosition(Useres.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
