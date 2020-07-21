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

//import com.ahmadrosid.svgloader.SvgDrawableTranscoder;
//import com.ahmadrosid.svgloader.SvgLoader;
//import com.ahmadrosid.svgloader.SvgSoftwareLayerSetter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.StreamEncoder;
//import com.caverock.androidsvg.SVG;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentInicio;
import com.tdlzgroup.educasa.MainActivity;
import com.tdlzgroup.educasa.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorInicio extends RecyclerView.Adapter<AdaptadorInicio.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private List<ContentInicio> items;
    private OnItemClickListener listener;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public AdaptadorInicio(Context context, List<ContentInicio> items, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final ImageView imagennormal;
        public final AdaptadorInicio AdaptadorInicio;

        public MyViewHolder(@NonNull View v, final AdaptadorInicio adapter) {
            super(v);
            nombre = v.findViewById(R.id.nombre);
            imagennormal = v.findViewById(R.id.foto_materia_normal);
            card = v.findViewById(R.id.inicio_cardview_alumnos);
            AdaptadorInicio = adapter;
        }

        public void bind(final ContentInicio item, final OnItemClickListener listener) {
            nombre.setText(item.getNombre_materia());
            GlideApp.with(context).load(storageReference.child("iconos/"+item.getUrl_imagen_materia())).centerCrop().placeholder(R.drawable.placeholder_materia).into(imagennormal);
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_item_inicio_alumnos, parent, false);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
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

    public void setFilter(List<ContentInicio> newList){
        items = new ArrayList<>();
        items.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ContentInicio item);
    }

}