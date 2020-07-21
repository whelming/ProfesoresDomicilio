package com.tdlzgroup.educasa.Solicitudes.SolicitudesAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesModels.ContentSolicitudes;
import com.tdlzgroup.educasa.R;

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
        public final TextView titulo;
        public final TextView interesados;
        public final TextView fecha;
        public final TextView hora;
        public final AdaptadorSolicitudes AdaptadorSolicitudes;

        public MyViewHolder(@NonNull View v, final AdaptadorSolicitudes adapter) {
            super(v);
            titulo = v.findViewById(R.id.solicitudes_card_titulo);
            interesados = v.findViewById(R.id.solicitudes_card_interesados);
            fecha = v.findViewById(R.id.solicitudes_card_fecha);
            hora = v.findViewById(R.id.solicitudes_card_hora);
            card = v.findViewById(R.id.solicitudes_cardview_alumnos);
            AdaptadorSolicitudes = adapter;
        }

        public void bind(final ContentSolicitudes item, final OnItemClickListener listener) {
            titulo.setText(item.getTitulo());
            interesados.setText((int)item.getInteresados()+"");

            String strFecha = ((Globales) context.getApplicationContext()).formatDate(item.getFechahora());
            String strHora = ((Globales) context.getApplicationContext()).formatHour(item.getFechahora());

            fecha.setText(strFecha);
            hora.setText(strHora);
            itemView.setOnClickListener((View v) -> {
                    listener.onItemClick(item);
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