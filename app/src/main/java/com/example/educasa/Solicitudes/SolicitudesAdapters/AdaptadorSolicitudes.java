package com.example.educasa.Solicitudes.SolicitudesAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.educasa.Solicitudes.SolicitudesModels.ContentSolicitudes;
import com.example.educasa.R;

import java.util.List;

public class AdaptadorSolicitudes extends RecyclerView.Adapter<AdaptadorSolicitudes.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private final List<ContentSolicitudes> items;
    private final OnItemClickListener listener;

    public AdaptadorSolicitudes(Context context, List<ContentSolicitudes> items, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final TextView solicitudes_interesados;
        public final AdaptadorSolicitudes AdaptadorSolicitudes;

        public MyViewHolder(@NonNull View v, final AdaptadorSolicitudes adapter) {
            super(v);
            nombre = v.findViewById(R.id.nombre);
            solicitudes_interesados = v.findViewById(R.id.solicitudes_interesados);
            card = v.findViewById(R.id.solicitudes_cardview_alumnos);
            AdaptadorSolicitudes = adapter;
        }

        public void bind(final ContentSolicitudes item, final OnItemClickListener listener) {
            nombre.setText(item.getTextodemo());
            solicitudes_interesados.setText(item.getId()+" interesados");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_item_solicitudes_alumnos, parent, false);
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
        void onItemClick(ContentSolicitudes item);
    }

}