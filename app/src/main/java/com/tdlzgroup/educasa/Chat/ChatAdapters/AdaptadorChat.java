package com.tdlzgroup.educasa.Chat.ChatAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tdlzgroup.educasa.Chat.ChatModels.ContentChat;
import com.tdlzgroup.educasa.R;

import java.util.List;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private final List<ContentChat> items;
    private final OnItemClickListener listener;

    public AdaptadorChat(Context context, List<ContentChat> items, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final AdaptadorChat AdaptadorChat;

        public MyViewHolder(@NonNull View v, final AdaptadorChat adapter) {
            super(v);
            nombre = v.findViewById(R.id.nombre);
            card = v.findViewById(R.id.chat_cardview_alumnos);
            AdaptadorChat = adapter;
        }

        public void bind(final ContentChat item, final OnItemClickListener listener) {
            nombre.setText(item.getTextodemo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_item_chat_alumnos, parent, false);
        return new MyViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ContentChat item);
    }

}