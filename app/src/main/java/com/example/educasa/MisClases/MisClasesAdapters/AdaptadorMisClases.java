package com.example.educasa.MisClases.MisClasesAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
        public final TextView categoria;
        public final TextView profesor;
        public final TextView horafecha;
        public final CircleImageView foto;

        public final AdaptadorMisClases AdaptadorMisClases;

        public MyViewHolder(@NonNull View v, final AdaptadorMisClases adapter) {
            super(v);
            categoria = v.findViewById(R.id.mis_clases_categoria);
            profesor = v.findViewById(R.id.mis_clases_profesores);
            horafecha= v.findViewById(R.id.mis_clases_fecha);
            foto = v.findViewById(R.id.mis_clases_foto);
            card = v.findViewById(R.id.misclases_cardview_alumnos);
            AdaptadorMisClases = adapter;
        }

        public void bind(final ContentMisClases item, final OnItemClickListener listener) {
            categoria.setText(item.getCategoria());
            profesor.setText(item.getProfesor());
            horafecha.setText(item.getHorafecha());
            Glide.with(context).load(item.getFoto()).into(foto);

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