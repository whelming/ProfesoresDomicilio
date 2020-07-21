package com.tdlzgroup.educasa.Chat.ChatFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tdlzgroup.educasa.Chat.Chat;
import com.tdlzgroup.educasa.Chat.ChatAdapters.AdaptadorChatDetalle;
import com.tdlzgroup.educasa.Chat.ChatModels.ContentChat;
import com.tdlzgroup.educasa.Chat.ChatModels.ContentChatDetalle;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ChatDetalle extends Fragment {
    private static final int RC_PHOTO_PICKER = 222;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentChatDetalle> milista;
    //private RecyclerView.Adapter miadaptador;
    private AdaptadorChatDetalle miadaptador;
    private FirebaseFirestore db;
    private ListenerRegistration registration;
    private String IDUser;
    private String tipoUsuario;

    private ImageButton btn_enviar;
    private EditText mensaje_enviar;

    private String IDChat;
    private String titulo;
    private String tipoUser;
    //private View v;
    private Bundle bundle;
    private CircleImageView userfoto;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    public View v;
    int height;
    int width;
    private Context context;

    public ChatDetalle() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_chat_detalle, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        userfoto = v.findViewById(R.id.fragment_chat_detalle_userfoto);
        btn_enviar = v.findViewById(R.id.chat_detalle_btn_enviar);
        mensaje_enviar = v.findViewById(R.id.chat_detalle_mensaje_enviar);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        ((MenuBottom)getActivity()).hideMenuBottom();

        bundle = this.getArguments();
        if (bundle != null) {
            ContentChat objetoChat = (ContentChat) bundle.getSerializable("ObjetoChat");
            IDChat = objetoChat.getId();
            tipoUser = bundle.getString("tipoUser");
            if (tipoUser.equals("alumnos")) {
                titulo = objetoChat.getNombreProf();
                GlideApp.with(context).load(storageReference.child("perfiles/"+objetoChat.getUrlfotoProf())).centerCrop().placeholder(R.drawable.user).into(userfoto);
                //Glide.with(getContext()).load(objetoChat.getUrlfotoProf()).centerCrop().placeholder(R.drawable.user).into(userfoto);
            }
            else if (tipoUser.equals("profesores")) {
                titulo = objetoChat.getNombreAlum();
                GlideApp.with(context).load(storageReference.child("perfiles/"+objetoChat.getUrlfotoAlum())).centerCrop().placeholder(R.drawable.user).into(userfoto);
                //Glide.with(getContext()).load(objetoChat.getUrlfotoAlum()).centerCrop().placeholder(R.drawable.user).into(userfoto);
            }
        }

        //String bundlerecibido = getArguments().getString("IDchat");
        Toolbar toolbar = v.findViewById(R.id.chat_toolbar_detalle);
        toolbar.setTitle(titulo);

        if (getActivity() != null) {
            ((Chat) getActivity()).setSupportActionBar(toolbar);
            ((Chat) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Chat) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icons_back_24dp);
            ((Chat) getActivity()).getSupportActionBar().show();
        }

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        IDUser = currentFirebaseUser.getUid();
        db = FirebaseFirestore.getInstance();

        recyclerView = v.findViewById(R.id.chat_recycler_detalle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        milista = new ArrayList<>();

        btn_enviar.setOnClickListener((View vista)-> {
            String textomsjenviar = mensaje_enviar.getText().toString();

            if (!textomsjenviar.equals("")) {
                enviarMensajeFire(tipoUser, textomsjenviar);
                mensaje_enviar.setText("");
                //recyclerView.post(() -> recyclerView.smoothScrollToPosition(milista.size()-1));
/*                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(milista.size()-1);
                    }
                }, 1000);*/
            }
            else {
                Toasty.warning(context, "Escribe un mensaje...", Toast.LENGTH_SHORT, true).show();
            }

        });

        v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    //Toast.makeText(getContext(), "TECLADO ACTIVE", Toast.LENGTH_SHORT).show();
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.scrollToPosition(milista.size()-1);
                    }
                }, 92);
                    //((MenuBottom) getActivity()).hideMenuBottom();
                }
                else {
                    //MenuBottom.navView.setVisibility(View.VISIBLE);
                    //((MenuBottom) getActivity()).showMenuBottom();
                }
            }
        });

        mensaje_enviar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*DisplayMetrics displayMetrics = new DisplayMetrics();
                            ((Chat) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                            height = displayMetrics.heightPixels;
                            width = displayMetrics.widthPixels;*/
                            height = v.getHeight();
                            width = v.getWidth();
                            //Toast.makeText(getContext(), height+ "FOCUSS "+ width, Toast.LENGTH_SHORT).show();

                        }
                    }, 2000);
                }
                else {
                    //NO FOCUS
                }

            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //return super.onContextItemSelected(item);
        switch (item.getItemId()){
            case 121:
                //miadaptador.removeItem(item.getGroupId());
                eliminarMsj(milista.get(item.getGroupId()).getId());
                return true;
                default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LlenaMILista();
    }

    private void eliminarMsj(String documentChatID){
        db.collection("chats/"+IDChat+"/mensajes")
                .document(documentChatID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar snackbar = Snackbar
                                .make(v,"Mensaje Eliminado !",Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.WHITE)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(getContext(), "clickedd", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        snackbar.show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void enviarMensajeFire(String tipoUser, String textomsjenviar) {

        Map<String, Object> mensajeToSend = new HashMap<>();
        mensajeToSend.put("mensaje", textomsjenviar);
        mensajeToSend.put("fechahora", new Timestamp(new Date()));
        mensajeToSend.put("usuario", tipoUser.equals("alumnos") ? 2 : 1 );

        /*if (tipoUser.equals("alumnos")) {mensajeToSend.put("usuario", 2);}
        else if (tipoUser.equals("profesores")) {mensajeToSend.put("usuario", 1);}*/

        db.collection("chats/"+IDChat+"/mensajes")
                .add(mensajeToSend)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d("OOOOOOOOOOO", "DocumentSnapshot written with ID: " + documentReference.getId());
                        mensajeToSend.clear();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w("EEEEEEE", "Error adding document", e);
                    }
                });
    }
    private void LLenarListLocal(){
        if (milista.size() > 0){
            milista.clear();
        }
        int width = 0;
        milista.add(new ContentChatDetalle("00000",1, "m ipsum dolor sit amet consecte", new Date()));
        milista.add(new ContentChatDetalle("11111",2, "elit nec litora, tem nec litora, tem", new Date()));
        milista.add(new ContentChatDetalle("22222",1, "ant vehicula in dui n", new Date()));
        milista.add(new ContentChatDetalle("33333",1, "dolor sit amet cons", new Date()));
        milista.add(new ContentChatDetalle("44444",2, " nec litora, tem ", new Date()));
        milista.add(new ContentChatDetalle("55555",2, "hicula in dui ", new Date()));
        milista.add(new ContentChatDetalle("66666",1, "sum dolor sit ame", new Date()));
        milista.add(new ContentChatDetalle("77777",2, "t vehicula ", new Date()));
        milista.add(new ContentChatDetalle("88888",1, "ec litora, t", new Date()));
        milista.add(new ContentChatDetalle("99999",1, "sum dolor sit amet consecte", new Date()));
        miadaptador = new AdaptadorChatDetalle(context, milista, width,new AdaptadorChatDetalle.OnItemClickListener() {
            @Override public void onItemClick(ContentChatDetalle item) {
                if (getActivity() != null){
                    Toast.makeText(context, ""+item.getUsuario(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // UPDTE VIEW AL SALIR TECLADO RECYCLER LAS POSITION

        recyclerView.setAdapter(miadaptador);
        //miadaptador.notifyDataSetChanged();
        //miadaptador.notifyItemRangeInserted( loadingMore ? 1 : 0, milista.size() );
        //recyclerView.invalidate();
    }

    private void LlenarListaGet(){
        if (milista.size() > 0){
            milista.clear();
        }

        //((Chat)getActivity()).showLoader();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Chat) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        CollectionReference query = db.collection("chats/"+IDChat+"/mensajes");
        final int[] conteoinicial = {0};
        final int[] conteofinal = {0};
        db.collection("chats/"+IDChat+"/mensajes")
                                .orderBy("fechahora", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()){
                                ((Chat)getActivity()).hideLoader();
                            }
                            if (milista.size() > 0){
                                milista.clear();
                            }
                            for (QueryDocumentSnapshot doc : task.getResult()) {

                                ContentChatDetalle mensajito = new ContentChatDetalle();
                                if (doc.getId() != null) {
                                    mensajito.setId(doc.getId());
                                }
                                if (doc.getString("mensaje") != null) {
                                    mensajito.setMensaje(doc.getString("mensaje"));
                                }
                                if (doc.getDouble("usuario") != null) {
                                    mensajito.setUsuario(doc.getDouble("usuario"));
                                }
                                if (doc.getTimestamp("fechahora") != null) {
                                    mensajito.setFechahora(doc.getTimestamp("fechahora").toDate());
                                }
                                milista.add(mensajito);
                            }
                            miadaptador = new AdaptadorChatDetalle(context, milista, width,new AdaptadorChatDetalle.OnItemClickListener() {
                                @Override public void onItemClick(ContentChatDetalle item) {
                                    if (getActivity() != null){
                                        Toast.makeText(context, ""+item.getUsuario(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            // UPDTE VIEW AL SALIR TECLADO RECYCLER LAS POSITION

                            recyclerView.setAdapter(miadaptador);
                            //miadaptador.notifyDataSetChanged();
                            //miadaptador.notifyItemRangeInserted( loadingMore ? 1 : 0, milista.size() );
                            //recyclerView.invalidate();

                            //recyclerView.post(() -> recyclerView.smoothScrollToPosition(milista.size()-1));
                            //((Chat)getActivity()).hideLoader();
                        }
                        else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    private void LlenaMILista() {

        if (milista.size() > 0){
            milista.clear();
        }
        ((Chat)getActivity()).showLoader();

        Query query = db.collection("chats/"+IDChat+"/mensajes");

        registration = query
                .orderBy("fechahora", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            ((Chat)getActivity()).hideLoader();
                            return;
                        }
                        if (milista.size() > 0){
                            milista.clear();
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            ContentChatDetalle mensajito = new ContentChatDetalle();
                            if (doc.getId() != null) {
                                mensajito.setId(doc.getId());
                            }
                            if (doc.getString("mensaje") != null) {
                                mensajito.setMensaje(doc.getString("mensaje"));
                            }
                            if (doc.getDouble("usuario") != null) {
                                mensajito.setUsuario(doc.getDouble("usuario"));
                            }
                            if (doc.getTimestamp("fechahora") != null) {
                                mensajito.setFechahora(doc.getTimestamp("fechahora").toDate());
                            }
                            milista.add(mensajito);
                        }

                        miadaptador = new AdaptadorChatDetalle(context, milista, width,new AdaptadorChatDetalle.OnItemClickListener() {
                            @Override public void onItemClick(ContentChatDetalle item) {
                                if (getActivity() != null){
                                    Toast.makeText(context, ""+item.getUsuario(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        recyclerView.setAdapter(miadaptador);
                        //miadaptador.notifyDataSetChanged();
                        //miadaptador.notifyItemRangeInserted( loadingMore ? 1 : 0, milista.size() );
                        //recyclerView.invalidate();
                        //recyclerView.requestLayout();
                        recyclerView.scrollToPosition(milista.size() - 1);

                        //recyclerView.post(() -> recyclerView.smoothScrollToPosition(milista.size()-1));
                        ((Chat)getActivity()).hideLoader();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MenuBottom)getActivity()).showMenuBottom();
        if (getActivity() != null)
            Globales.hideSoftKeyboard(getActivity());
        registration.remove();
    }
}
