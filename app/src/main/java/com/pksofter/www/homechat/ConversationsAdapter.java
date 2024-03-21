package com.pksofter.www.homechat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ViewHolder> {

    Context context;
    List<ConverstaionsModel> converstaionsModelList;



    public ConversationsAdapter(Context context, List<ConverstaionsModel> converstaionsModelList) {
        this.context = context;
        this.converstaionsModelList = converstaionsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.conversations_card,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tv_name.setText(converstaionsModelList.get(position).getReceiverName());
        holder.lastmsg.setText(converstaionsModelList.get(position).getLastsms());
        holder.time.setText(converstaionsModelList.get(position).getTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChatingActivity.class);
                intent.putExtra("receiverid",converstaionsModelList.get(position).getReceiverId());
                intent.putExtra("name",converstaionsModelList.get(position).getReceiverName());
                intent.putExtra("image",converstaionsModelList.get(position).getImage());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return converstaionsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name,lastmsg,time;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.tv_name);
            lastmsg=itemView.findViewById(R.id.tv_lastmsg);
            time=itemView.findViewById(R.id.tv_time);
            cardView=itemView.findViewById(R.id.card);
        }
    }
}
