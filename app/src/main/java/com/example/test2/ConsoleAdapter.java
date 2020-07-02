package com.example.test2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

public class ConsoleAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<resivedmsg> Resivedmsg;
    private Context context;
    private ArrayList<Integer> drawableArray;

    public ConsoleAdapter(ArrayList<resivedmsg> Resivedmsg, Context context){
        this.Resivedmsg = Resivedmsg;
        this.context = context;
        drawableArray = new ArrayList();
    }

    @Override
    public int getItemViewType(int position) {
        return Resivedmsg.get(position).getMe_or();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View LayoutView1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagesent,null,false);
                RecyclerView.LayoutParams lp1 = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                LayoutView1.setLayoutParams(lp1);
                return new sentmsgHolder(LayoutView1);
            case 1:
                View LayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message,null,false);
                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                LayoutView.setLayoutParams(lp);
                return new ResivedmsgHolder(LayoutView);
            case 2:
                View LayoutView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.marker_sent,null,false);
                RecyclerView.LayoutParams lp2 = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                LayoutView2.setLayoutParams(lp2);
                return new SentMarkerHolder(LayoutView2);
            case 3:
                View LayoutView3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.marker_reseved,null,false);
                RecyclerView.LayoutParams lp3 = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                LayoutView3.setLayoutParams(lp3);
                return new ResivedMarkerHolder(LayoutView3);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                sentmsgHolder viewHolder0 = (sentmsgHolder)holder;
                viewHolder0.Message.setText(Resivedmsg.get(position).getMsg());
                viewHolder0.Time.setText(Resivedmsg.get(position).getTime());
                break;
            case 1:
                ResivedmsgHolder viewHolder1 = (ResivedmsgHolder)holder;
                viewHolder1.Name.setText(Resivedmsg.get(position).getName());
                viewHolder1.Message.setText(Resivedmsg.get(position).getMsg());
                viewHolder1.Time.setText(Resivedmsg.get(position).getTime());
                String mDrawableName = "a"+Resivedmsg.get(position).getImg();
                int resID = context.getResources().getIdentifier(mDrawableName , "drawable", context.getPackageName());
                Bitmap bImage = BitmapFactory.decodeResource(context.getResources(),resID);
                viewHolder1.img.setImageBitmap(bImage);
                break;
            case 2:
                SentMarkerHolder viewHolder2 = (SentMarkerHolder)holder;
                viewHolder2.pin_drop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double X = Double.parseDouble(Resivedmsg.get(position).getMsg().split("/y/")[0]);
                        double Y = Double.parseDouble(Resivedmsg.get(position).getMsg().split("/y/")[1]);
                        MainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_map);
                        MapFragment.MoveCamera(X,Y);
                    }
                });
                viewHolder2.Time.setText(Resivedmsg.get(position).getTime());
                break;
            case 3:
                ResivedMarkerHolder viewHolder3 = (ResivedMarkerHolder)holder;
                viewHolder3.Name.setText(Resivedmsg.get(position).getName());
                viewHolder3.pin_drop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double X = Double.parseDouble(Resivedmsg.get(position).getMsg().split("/y/")[0]);
                        double Y = Double.parseDouble(Resivedmsg.get(position).getMsg().split("/y/")[1]);
                        MainActivity.bottomNavigationView.setSelectedItemId(R.id.nav_map);
                        MapFragment.MoveCamera(X,Y);
                    }
                });
                viewHolder3.Time.setText(Resivedmsg.get(position).getTime());
                String mDrawableName1 = "a"+Resivedmsg.get(position).getImg();
                int resID1 = context.getResources().getIdentifier(mDrawableName1 , "drawable", context.getPackageName());
                Bitmap bImage1 = BitmapFactory.decodeResource(context.getResources(),resID1);
                viewHolder3.img.setImageBitmap(bImage1);
                break;

        }


    }



    @Override
    public int getItemCount() {
        return Resivedmsg.size();
    }


    class SentMarkerHolder extends ViewHolder {
        TextView Time;
        ImageButton pin_drop;
        public SentMarkerHolder(View view) {
            super(view);
            pin_drop = view.findViewById(R.id.pin_drop);
            Time = view.findViewById(R.id.Time);

        }
    }
    class ResivedMarkerHolder extends ViewHolder {
        TextView Name,Time;
        ImageButton pin_drop;
        ImageView img;
        public ResivedMarkerHolder(View view) {
            super(view);
            Name = view.findViewById(R.id.User);
            pin_drop = view.findViewById(R.id.pin_drop);
            Time = view.findViewById(R.id.Time);
            img = view.findViewById(R.id.image_profile);
        }
    }

    class sentmsgHolder extends ViewHolder {
        TextView Message,Time;
        public sentmsgHolder(View view) {
            super(view);
            Message = view.findViewById(R.id.Message);
            Time = view.findViewById(R.id.Time);

        }
    }

     class ResivedmsgHolder extends ViewHolder {
        TextView Name,Message,Time;
        ImageView img;
        public ResivedmsgHolder (View view){
            super(view);
            Name = view.findViewById(R.id.User);
            Message = view.findViewById(R.id.Message);
            Time = view.findViewById(R.id.Time);
            img = view.findViewById(R.id.image_profile);

        }
    }
}
