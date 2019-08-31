package com.tdlzgroup.educasa.Inicio.InicioAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentListaProfesores;
import com.tdlzgroup.educasa.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        public final TextView nombres;
        public final CircleImageView imagencircular;
        public final TextView descripcion;
        public final RatingBar puntaje;

        public final AdaptadorProfesores AdaptadorProfesores;

        public MyViewHolder(@NonNull View v, final AdaptadorProfesores adapter) {
            super(v);
            nombres = v.findViewById(R.id.inicio_listaprofesores_nombres);
            imagencircular = v.findViewById(R.id.inicio_lista_profesores_foto);
            descripcion = v.findViewById(R.id.inicio_card_lista_profesores_descripcion);
            puntaje = v.findViewById(R.id.inicio_rating_listaprofesores);
            card = v.findViewById(R.id.inicio_cardview_listaprofesores_alumnos);
            AdaptadorProfesores = adapter;
        }

        public void bind(final ContentListaProfesores item, final OnItemClickListener listener) {
            Glide.with(context).load(item.getUrl_foto()).centerCrop().placeholder(R.drawable.user).into(imagencircular);
            nombres.setText(item.getNombres());
            puntaje.setRating( (float) item.getPuntuacion());
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