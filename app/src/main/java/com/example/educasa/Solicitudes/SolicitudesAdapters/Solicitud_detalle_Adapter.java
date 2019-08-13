package com.example.educasa.Solicitudes.SolicitudesAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educasa.Chat.Chat;
import com.example.educasa.Chat.ChatAdapters.AdaptadorChat;
import com.example.educasa.Chat.ChatFragments.ChatEjm;
import com.example.educasa.R;
import com.example.educasa.Solicitudes.SolicitudesFragments.SolicitudesDetalle;
import com.example.educasa.Solicitudes.SolicitudesModels.ContentSolicitudes;
import com.example.educasa.Solicitudes.SolicitudesModels.ContentSolicitudesDetalle;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.content.ContextCompat.startActivities;
import static android.support.v4.content.ContextCompat.startActivity;
import static android.support.v4.content.ContextCompat.startForegroundService;

public class Solicitud_detalle_Adapter extends RecyclerView.Adapter<Solicitud_detalle_Adapter.ViewHolderSolicitudDetalle> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private final List<ContentSolicitudesDetalle> items;
    private final Solicitud_detalle_Adapter.OnItemClickListener listener;

    public Solicitud_detalle_Adapter(Context context, List<ContentSolicitudesDetalle> items, Solicitud_detalle_Adapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }
    public class ViewHolderSolicitudDetalle extends RecyclerView.ViewHolder{
        public final TextView nombre;
        public final TextView solicitudes_descripcion;
        public final TextView precio;
        public final CircleImageView imgdetalle;
        public final Button btnEliminarSolicitud;
        public final Button btnAceptarSolicitud;


        public ViewHolderSolicitudDetalle(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            solicitudes_descripcion = itemView.findViewById(R.id.solicitudes_descripcion);
            precio = itemView.findViewById(R.id.precio);
            card = itemView.findViewById(R.id.solicitudes_cardview_detalle);
            imgdetalle = itemView.findViewById(R.id.imgdetalle);
            btnEliminarSolicitud = itemView.findViewById(R.id.EliminarDetalle);
            btnAceptarSolicitud = itemView.findViewById(R.id.AceptarDetalle);
            btnAceptarSolicitud.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast1 =
                          Toast.makeText(context, "Solicitud Aceptada", Toast.LENGTH_SHORT);
                    toast1.show();
                    Intent   intent = new Intent (context, Chat.class);
                   context.startActivity(intent);
                }
            });
            btnEliminarSolicitud.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast toast2 =
                            Toast.makeText(context, "Solicitud Eliminada", Toast.LENGTH_SHORT);
                    toast2.show();

                }
            });

        }

        public void bind(final ContentSolicitudesDetalle item, final Solicitud_detalle_Adapter.OnItemClickListener listener) {
            nombre.setText(item.getNombre());
            precio.setText(item.getPrecio());
            solicitudes_descripcion.setText(item.getDescripcion());
            imgdetalle.setImageResource(item.getFotoPerfil());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    //Llenar con lo que queremos que pase cuando se de click a cada item de la lista
                }
            });
        }
    }

    @NonNull
    @Override
    public Solicitud_detalle_Adapter.ViewHolderSolicitudDetalle onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = mInflater.inflate(R.layout.card_item_solicitudes_detalle, viewGroup, false);
        return new Solicitud_detalle_Adapter.ViewHolderSolicitudDetalle(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Solicitud_detalle_Adapter.ViewHolderSolicitudDetalle holder, int i) {
        holder.bind(items.get(i),listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public interface OnItemClickListener {
        void onItemClick(ContentSolicitudes item);
    }
}
