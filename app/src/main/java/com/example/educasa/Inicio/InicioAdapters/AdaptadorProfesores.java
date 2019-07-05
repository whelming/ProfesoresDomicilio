package com.example.educasa.Inicio.InicioAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.educasa.Inicio.InicioModels.ContentListaProfesores;
import com.example.educasa.R;

import java.util.List;

public class AdaptadorProfesores extends RecyclerView.Adapter<AdaptadorProfesores.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private final List<ContentListaProfesores> items;
    private final OnItemClickListener listener;

    public AdaptadorProfesores(Context context, List<ContentListaProfesores> items, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final AdaptadorProfesores AdaptadorProfesores;

        public MyViewHolder(@NonNull View v, final AdaptadorProfesores adapter) {
            super(v);
            nombre = v.findViewById(R.id.nombre);
            card = v.findViewById(R.id.inicio_cardview_listaprofesores_alumnos);
            AdaptadorProfesores = adapter;
        }

        public void bind(final ContentListaProfesores item, final OnItemClickListener listener) {
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
        View v = mInflater.inflate(R.layout.card_item_inicio_listaprofesores_alumnos, parent, false);
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
        void onItemClick(ContentListaProfesores item);
    }

}