package com.tdlzgroup.educasa.Inicio.InicioAdapters;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgDrawableTranscoder;
import com.ahmadrosid.svgloader.SvgLoader;
import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.StreamEncoder;
import com.caverock.androidsvg.SVG;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentInicio;
import com.tdlzgroup.educasa.MainActivity;
import com.tdlzgroup.educasa.R;

import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        public final CircleImageView imagencircular;
        public final ImageView imagennormal;
        public final AdaptadorInicio AdaptadorInicio;

        public MyViewHolder(@NonNull View v, final AdaptadorInicio adapter) {
            super(v);
            nombre = v.findViewById(R.id.nombre);
            imagencircular = v.findViewById(R.id.foto_materia);
            imagennormal = v.findViewById(R.id.foto_materia_normal);
            card = v.findViewById(R.id.inicio_cardview_alumnos);
            AdaptadorInicio = adapter;
        }

        public void bind(final ContentInicio item, final OnItemClickListener listener) {
            nombre.setText(item.getNombre_materia());

          //Glide.with(context).load(item.getUrl_imagen_materia()).into(imagencircular);
            Glide.with(context).load(item.getUrl_imagen_materia()).into(imagennormal);

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