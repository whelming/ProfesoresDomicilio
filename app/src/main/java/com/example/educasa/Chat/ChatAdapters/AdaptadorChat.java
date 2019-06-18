package com.example.educasa.Chat.ChatAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educasa.R;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.MyViewHolder> {
    private String[] mDataset;
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;

    public AdaptadorChat(Context context, String[] myDataset) {
        mDataset = myDataset;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final AdaptadorChat AdaptadorChat;

        public MyViewHolder(@NonNull View v, final AdaptadorChat adapter) {
            super(v);
            nombre = v.findViewById(R.id.nombre);
            card = v.findViewById(R.id.chat_cardview_alumnos);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(adapter.context, nombre.getText(), Toast.LENGTH_SHORT).show();
                }
            });
            AdaptadorChat = adapter;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_item_chat_alumnos, parent, false);
        return new MyViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nombre.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}