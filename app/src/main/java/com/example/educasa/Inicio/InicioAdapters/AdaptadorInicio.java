package com.example.educasa.Inicio.InicioAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.educasa.Inicio.InicioModels.ContentInicio;
import com.example.educasa.R;

import java.util.List;

public class AdaptadorInicio extends RecyclerView.Adapter<AdaptadorInicio.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private final List<ContentInicio> items;
    private final OnItemClickListener listener;

    public AdaptadorInicio(Context context, List<ContentInicio> items, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final AdaptadorInicio AdaptadorInicio;

        public MyViewHolder(@NonNull View v, final AdaptadorInicio adapter) {
            super(v);
            nombre = v.findViewById(R.id.nombre);
            card = v.findViewById(R.id.inicio_cardview_alumnos);
            AdaptadorInicio = adapter;
        }

        public void bind(final ContentInicio item, final OnItemClickListener listener) {
            nombre.setText(item.getTextodemo());
            //card.setBackgroundResource(item.getImagen_categoria());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_item_inicio_alumnos, parent, false);
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
        void onItemClick(ContentInicio item);
    }

}