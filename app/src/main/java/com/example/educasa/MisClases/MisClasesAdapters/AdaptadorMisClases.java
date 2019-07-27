package com.example.educasa.MisClases.MisClasesAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.educasa.MisClases.MisClasesModels.ContentMisClases;
import com.example.educasa.R;

import java.util.List;

public class AdaptadorMisClases extends RecyclerView.Adapter<AdaptadorMisClases.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private final List<ContentMisClases> items;
    private final OnItemClickListener listener;

    public AdaptadorMisClases(Context context, List<ContentMisClases> items, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final AdaptadorMisClases AdaptadorMisClases;

        public MyViewHolder(@NonNull View v, final AdaptadorMisClases adapter) {
            super(v);
            nombre = v.findViewById(R.id.nombre);
            card = v.findViewById(R.id.misclases_cardview_alumnos);
            AdaptadorMisClases = adapter;
        }

        public void bind(final ContentMisClases item, final OnItemClickListener listener) {
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
        View v = mInflater.inflate(R.layout.card_item_misclases_alumnos, parent, false);
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
        void onItemClick(ContentMisClases item);
    }

}