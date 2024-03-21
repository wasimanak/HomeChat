package com.pksofter.www.homechat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    List<UserModel> userModelList;
    Context context;

    public UsersAdapter(List<UserModel> userModelList, Context context) {
        this.userModelList = userModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.userslayout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_username.setText(userModelList.get(position).getUsername());
        holder.tv_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ChatingActivity.class);
                intent.putExtra("receiverid",userModelList.get(position).getUserid());
                intent.putExtra("name",userModelList.get(position).getUsername());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModelList==null ? 0 : userModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_username=itemView.findViewById(R.id.tv_username);
        }
    }
}
