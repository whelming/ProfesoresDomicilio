package com.tdlzgroup.educasa.Chat.ChatFragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Chat.Chat;
import com.tdlzgroup.educasa.Chat.ChatAdapters.AdaptadorChat;
import com.tdlzgroup.educasa.Chat.ChatModels.ContentChat;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ChatEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ContentChat> milista;
    private FirebaseFirestore db;
    private String IDUser;
    private String tipoUsuario;
    private Context context;

    public ChatEjm() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_ejm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Toolbar toolbar = v.findViewById(R.id.chat_toolbar);
        toolbar.setTitle("Conversaciones");

        if (getActivity() != null) {
            ((Chat)getActivity()).setSupportActionBar(toolbar);
        }

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        IDUser = currentFirebaseUser.getUid();
        db = FirebaseFirestore.getInstance();

        recyclerView = v.findViewById(R.id.chat_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        milista = new ArrayList<>();
        LlenaMILista();
    }

    private void LlenaMILista() {
        /*milista.add(new ContentChat(
                "DF5d4f5111",
                "DFALUM111",
                "IDPROF111",
                "Nombre Alumn111",
                "Nombre Prof111",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Ruben2017.jpg/245px-Ruben2017.jpg",
                "https://pbs.twimg.com/profile_images/977232658047660038/z0Fnsh-8.jpg",
                new Date()
        ));*/


        if (milista.size() > 0){
            milista.clear();
        }

        ((Chat)getActivity()).showLoader();

        tipoUsuario = ((Globales) getActivity().getApplicationContext()).getTipoUsuario();

        Query query = null;

        if (tipoUsuario.equals("alumnos")) {
            query = db.collection("chats").whereEqualTo("idAlum", IDUser);
        }

        else if (tipoUsuario.equals("profesores")) {
            query = db.collection("chats").whereEqualTo("idProf", IDUser);
        }

            query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()){
                                Toasty.info(context, "Aún no tienes Conversaciones", Toast.LENGTH_SHORT, true).show();
                                ((Chat)getActivity()).hideLoader();
                            }

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentChat chat = new ContentChat();
                                chat.setId(document.getId());
                                chat.setIdAlum(document.getString("idAlum"));
                                chat.setIdProf(document.getString("idProf"));
                                chat.setNombreAlum(document.getString("nombreAlum"));
                                chat.setNombreProf(document.getString("nombreProf"));
                                chat.setUrlfotoAlum(document.getString("urlfotoAlum"));
                                chat.setUrlfotoProf(document.getString("urlfotoProf"));
                                chat.setFechahora(document.getTimestamp("fechahora").toDate());
                                        //document.getDocumentReference("materia")
                                milista.add(chat);
                                recyclerView.setAdapter(new AdaptadorChat(context, milista, new AdaptadorChat.OnItemClickListener() {
                                    @Override public void onItemClick(ContentChat item) {
                                        if (getActivity() != null){
                                            Bundle bundle = new Bundle();
                                            bundle.putString("tipoUser", tipoUsuario);
                                            bundle.putSerializable("ObjetoChat", item);
                                            ChatDetalle fragmentDetalleChat = new ChatDetalle();
                                            fragmentDetalleChat.setArguments(bundle);
                                            getActivity().getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.frag_chat_principal, fragmentDetalleChat, "fragmentDetalleChat")
                                                    .addToBackStack(null).commit();
                                        }
                                    }
                                }));
                                //recyclerView.post(() -> recyclerView.smoothScrollToPosition(1));
                                ((Chat)getActivity()).hideLoader();
                            }
                        }
                        else {
                            Log.w("Error", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chat_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.chat_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "submit chat", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
}
