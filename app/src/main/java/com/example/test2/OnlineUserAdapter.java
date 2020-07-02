package com.example.test2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.ArrayList;

class OnlineUserAdapter extends  RecyclerView.Adapter<ViewHolder> {
    static ArrayList<OnlineUser> Useres;
    private static ClickListener clickListener;


    public OnlineUserAdapter(ArrayList<OnlineUser> Useres){
        this.Useres = Useres;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View LayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,null,false);
        RecyclerView.LayoutParams lp1 = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutView.setLayoutParams(lp1);
        UserListViewHolder s = new UserListViewHolder(LayoutView);
        return s;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserListViewHolder userListViewHolder = (UserListViewHolder)holder;
        userListViewHolder.Name.setText(Useres.get(position).name);

    }

    @Override
    public int getItemCount() {
        return Useres.size();
    }



    public class UserListViewHolder extends ViewHolder implements View.OnClickListener  {
        public TextView Name;
        public UserListViewHolder (View view){
            super(view);
            Name = view.findViewById(R.id.User_name);
            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        OnlineUserAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }


}
