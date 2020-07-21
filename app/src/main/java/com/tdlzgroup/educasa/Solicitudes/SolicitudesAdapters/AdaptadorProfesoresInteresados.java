package com.tdlzgroup.educasa.Solicitudes.SolicitudesAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesModels.ContentProfesoresInteresados;
import com.tdlzgroup.educasa.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorProfesoresInteresados extends RecyclerView.Adapter<AdaptadorProfesoresInteresados.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private final List<ContentProfesoresInteresados> items;
    private final OnItemClickListener listener;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public AdaptadorProfesoresInteresados(Context context, List<ContentProfesoresInteresados> items, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombres;
        public final TextView descripcion;
        public final TextView precio;
        public final CircleImageView imagencircular;
        public final RatingBar puntaje;
        public final Button btn_aceptar;
        public final Button btn_eliminar;

        public final AdaptadorProfesoresInteresados AdaptadorProfesoresInteresados;

        public MyViewHolder(@NonNull View v, final AdaptadorProfesoresInteresados adapter) {
            super(v);
            nombres = v.findViewById(R.id.solicitudes_detalle_nombres);
            imagencircular = v.findViewById(R.id.solicitudes_detalle_interesados_foto);
            descripcion = v.findViewById(R.id.solicitudes_detalle_profesores_descripcion);
            precio = v.findViewById(R.id.solicitudes_detalle_profesores_precio);
            puntaje = v.findViewById(R.id.solicitudes_detalle_rating_profesores_interesados);
            card = v.findViewById(R.id.solicitudes_cardview_profesores_interesados);
            btn_aceptar = v.findViewById(R.id.solicitudes_detalle_btn_aceptar);
            btn_eliminar = v.findViewById(R.id.solicitudes_detalle_btn_eliminar);
            AdaptadorProfesoresInteresados = adapter;
        }

        public void bind(final ContentProfesoresInteresados item, final OnItemClickListener listener) {
            GlideApp.with(context).load(storageReference.child("perfiles/"+item.getUrl_foto())).centerCrop().placeholder(R.drawable.user).into(imagencircular);
            nombres.setText(item.getNombres());
            descripcion.setText(item.getDescripcion());
            precio.setText(item.getPrecio()+"");
            puntaje.setRating( (float) item.getPuntuacion());

            btn_aceptar.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onAceptarClick(item);
                }
            });

            btn_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onEliminarClick(item);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_item_solicitudes_profesores_interesados, parent, false);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        return new MyViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
/*        holder.btn_aceptar.setOnClickListener((View v) -> {
            Toast.makeText(context, "ACEPTAR "+ position, Toast.LENGTH_SHORT).show();
        });
        holder.btn_eliminar.setOnClickListener((View v) -> {
            Toast.makeText(context, "ELIMINAR " + position, Toast.LENGTH_SHORT).show();
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ContentProfesoresInteresados item);
        void onAceptarClick(ContentProfesoresInteresados item);
        void onEliminarClick(ContentProfesoresInteresados item);
    }

}