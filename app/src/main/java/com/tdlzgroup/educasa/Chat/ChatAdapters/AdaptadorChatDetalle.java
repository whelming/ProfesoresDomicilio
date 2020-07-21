package com.tdlzgroup.educasa.Chat.ChatAdapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tdlzgroup.educasa.Chat.ChatModels.ContentChatDetalle;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorChatDetalle extends RecyclerView.Adapter<AdaptadorChatDetalle.MyViewHolder> {
    private LayoutInflater mInflater;
    private CardView cardReceived;
    private CardView cardSender;
    private Context context;
    private int width;
    private final List<ContentChatDetalle> items;
    private final OnItemClickListener listener;

    public AdaptadorChatDetalle(Context context, List<ContentChatDetalle> items, int width, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
        this.width = width;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public final LinearLayout containerReceived;
        public final LinearLayout containerSender;
        public final TextView mensajeReceived;
        public final TextView mensajeSender;
        public final TextView fechaReceived;
        public final TextView fechaSender;
        public final String tipoUsuario;
        public final ConstraintLayout container;

        public double userBD;
        public int userDBint;
        //public final CircleImageView imagencircular;
        public final AdaptadorChatDetalle AdaptadorChatDetalle;

        public MyViewHolder(@NonNull View v, final AdaptadorChatDetalle adapter) {
            super(v);
            container = v.findViewById(R.id.card_chat_detalle_containers);
            container.setOnCreateContextMenuListener(this);
            containerReceived = v.findViewById(R.id.chat_cardview_detalle_received_container);
            containerSender = v.findViewById(R.id.chat_cardview_detalle_sender_container);

            mensajeReceived = v.findViewById(R.id.chat_card_detalle_mensaje_received);
            mensajeSender = v.findViewById(R.id.chat_card_detalle_mensaje_sender);

            fechaReceived = v.findViewById(R.id.chat_card_detalle_fechahora_received);
            fechaSender = v.findViewById(R.id.chat_card_detalle_fechahora_sender);

            //imagencircular = v.findViewById(R.id.chat_card_foto_usuario);
            cardReceived = v.findViewById(R.id.chat_cardview_detalle_received);
            cardSender = v.findViewById(R.id.chat_cardview_detalle_sender);
            tipoUsuario = ((Globales) context.getApplicationContext()).getTipoUsuario();

            AdaptadorChatDetalle = adapter;
            //v.setBackgroundResource(R.drawable.);
        }

        public void bind(final ContentChatDetalle item, final OnItemClickListener listener) {

            //            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_WRAP, ConstraintLayout.LayoutParams.MATCH_CONSTRAINT_WRAP);
//            params.gravity = Gravity.START;
            userBD = item.getUsuario();
            userDBint = (int) userBD;

            // profesor 1 || alumno 2
            SimpleDateFormat format = new SimpleDateFormat("dd/MM hh:mm");
            SimpleDateFormat hora = new SimpleDateFormat("HH");
            Date fechahora = item.getFechahora();
            String dateString = format.format(fechahora);
            String hora24 = hora.format(fechahora);
            String ampm;

            if(Integer.parseInt(hora24)> 12){
                ampm = " pm";
            }
            else {
                ampm = " am";
            }
            dateString+=ampm;

/*            int random = 1 + (int)(Math.random() * ((2 - 1) + 1));
            Log.d("RANDOMM", "RANDOMM: "+random);
            Log.d("USERDBINT", "USERDBINT: "+userDBint);*/

            mensajeReceived.setText(item.getMensaje());
            fechaReceived.setText(dateString);
            mensajeSender.setText(item.getMensaje());
            fechaSender.setText(dateString);

            if(tipoUsuario.equals("profesores")){
                if (userDBint == 1) {
                    // RECEIVED
                    containerSender.setVisibility(View.VISIBLE);
                    containerReceived.setVisibility(View.INVISIBLE);

                    //containerReceived.setVisibility(View.VISIBLE);
                    //containerSender.removeAllViews();

                    //itemView.setBackgroundColor(Color.GRAY);
                    //itemView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    //itemView.setX(50);
                    //itemView.setForegroundGravity(Gravity.RIGHT);
                    //card.setForegroundGravity(Gravity.RIGHT);
//                card.setLayoutParams(params);
                    //itemView.setRight(90);
                }
                else if (userDBint == 2) {
                    containerSender.setVisibility(View.INVISIBLE);
                    containerReceived.setVisibility(View.VISIBLE);
                    // SENDER
                    //containerReceived.removeAllViews();
                }
            }
            else {
                if (userDBint == 1) {
                    // RECEIVED
                    containerSender.setVisibility(View.INVISIBLE);
                    containerReceived.setVisibility(View.VISIBLE);

                    //containerReceived.setVisibility(View.VISIBLE);
                    //containerSender.removeAllViews();

                    //itemView.setBackgroundColor(Color.GRAY);
                    //itemView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                    //itemView.setX(50);
                    //itemView.setForegroundGravity(Gravity.RIGHT);
                    //card.setForegroundGravity(Gravity.RIGHT);
//                card.setLayoutParams(params);
                    //itemView.setRight(90);
                }
                else if (userDBint == 2) {
                    containerSender.setVisibility(View.VISIBLE);
                    containerReceived.setVisibility(View.INVISIBLE);

                    // SENDER
                    //containerReceived.removeAllViews();
                }
            }

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });*/

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemClick(item);
                    return false;
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//            menu.setHeaderTitle("Elige una opción");
            menu.add(this.getAdapterPosition(),121,0,"Borrar");
            //menu.add(this.getAdapterPosition(),122,1,"Detalle");
        }
    }

    public void removeItem(int pos){
        //Toast.makeText(context, "POS: "+pos, Toast.LENGTH_SHORT).show();
        items.remove(pos);
        //notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.card_item_chat_detalle, parent, false);
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
        void onItemClick(ContentChatDetalle item);
    }

}