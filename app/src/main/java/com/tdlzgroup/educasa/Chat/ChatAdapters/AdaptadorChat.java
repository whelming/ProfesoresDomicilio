package com.tdlzgroup.educasa.Chat.ChatAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdlzgroup.educasa.Chat.ChatModels.ContentChat;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView card;
    private Context context;
    private final List<ContentChat> items;
    private final OnItemClickListener listener;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public AdaptadorChat(Context context, List<ContentChat> items, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre;
        public final TextView fecha;
        public final CircleImageView imagencircular;
        public final AdaptadorChat AdaptadorChat;


        public MyViewHolder(@NonNull View v, final AdaptadorChat adapter) {
            super(v);
            nombre = v.findViewById(R.id.chat_card_nombre);
            fecha = v.findViewById(R.id.chat_card_fechahora);
            imagencircular = v.findViewById(R.id.chat_card_foto_usuario);
            card = v.findViewById(R.id.chat_cardview_alumnos);
            AdaptadorChat = adapter;
        }

        public void bind(final ContentChat item, final OnItemClickListener listener) {
            String tipoUsuario = ((Globales) context.getApplicationContext()).getTipoUsuario();
            if (tipoUsuario.equals("alumnos")){
                nombre.setText(item.getNombreProf());
                //Glide.with(context).load(item.getUrlfotoProf()).centerCrop().placeholder(R.drawable.user).into(imagencircular);
                GlideApp.with(context).load(storageReference.child("perfiles/"+item.getUrlfotoProf())).centerCrop().placeholder(R.drawable.user).into(imagencircular);
            }
            else if (tipoUsuario.equals("profesores")) {
                nombre.setText(item.getNombreAlum());
                //Glide.with(context).load(item.getUrlfotoAlum()).centerCrop().placeholder(R.drawable.user).into(imagencircular);
                GlideApp.with(context).load(storageReference.child("perfiles/"+item.getUrlfotoAlum())).centerCrop().placeholder(R.drawable.user).into(imagencircular);
            }

            String strFecha = ((Globales) context.getApplicationContext()).formatDate(item.getFechahora());
            String strHora = ((Globales) context.getApplicationContext()).formatHour(item.getFechahora());

            fecha.setText(strFecha+" "+strHora);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_item_chat_alumnos, parent, false);
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

    public interface OnItemClickListener {
        void onItemClick(ContentChat item);
    }

}