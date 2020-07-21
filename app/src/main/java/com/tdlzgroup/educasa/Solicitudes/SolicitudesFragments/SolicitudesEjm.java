package com.tdlzgroup.educasa.Solicitudes.SolicitudesFragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.tdlzgroup.educasa.Solicitudes.Solicitudes;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesAdapters.AdaptadorSolicitudes;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesModels.ContentSolicitudes;
import com.tdlzgroup.educasa.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SolicitudesEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentSolicitudes> milista;
    FirebaseUser user;
    private FirebaseFirestore db;
    private String IDUser;
    private Context context;

    public SolicitudesEjm() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_solicitudes_ejm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Toolbar toolbar = v.findViewById(R.id.solicitudes_toolbar);
        toolbar.setTitle("Mis Solicitudes");
        if (getActivity() != null) {
            ((Solicitudes)getActivity()).setSupportActionBar(toolbar);
        }
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser() ;
        IDUser = user.getUid();
        recyclerView = v.findViewById(R.id.solicitudes_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        milista = new ArrayList<>();
        LlenaMILista();
    }

    private void LlenaMILista() {
        if (milista.size() > 0){
            milista.clear();
        }

        ((Solicitudes)getActivity()).showLoader();

        db.collection("clases")
                .whereEqualTo("idAlum", IDUser)
                .whereEqualTo("tipo", 1)
                .whereEqualTo("estado", 1)
                .orderBy("fechahora", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()){
                                Toasty.info(context, "Aún no tienes Solicitudes", Toast.LENGTH_SHORT, true).show();
                                recyclerView.setBackground(ContextCompat.getDrawable(context, R.drawable.placeholder_nosolicitudes_alumno));
                                ((Solicitudes)getActivity()).hideLoader();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentSolicitudes missolicitudes = new ContentSolicitudes();
                                missolicitudes.setId(document.getId());
                                missolicitudes.setTitulo(document.getString("titulo"));
                                missolicitudes.setInteresados(document.getDouble("interesados"));
                                missolicitudes.setFechahora((document.getTimestamp("fechahora")).toDate());
                                //detalles
                                missolicitudes.setDescripcion(document.getString("descripcion"));
                                missolicitudes.setPrecio(document.getDouble("precio"));
                                missolicitudes.setUrlsfotos((List<String>) document.get("urlfotos"));
                                milista.add(missolicitudes);
                            }

                            recyclerView.setAdapter(new AdaptadorSolicitudes(context, milista, (ContentSolicitudes item) -> {
                                if (getActivity() != null){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("idsolicitud", item.getId());
                                    bundle.putString("titulosolicitud", item.getTitulo());
                                    // SERIALIZABLE
                                    bundle.putSerializable("ObjetoSolicitud", item);
                                    SolicitudesDetalle fragmentSolicitudDetalle = new SolicitudesDetalle();
                                    fragmentSolicitudDetalle.setArguments(bundle);
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.frag_solicitudes_principal, fragmentSolicitudDetalle, "fragdetallesolicitud")
                                            .addToBackStack(null).commit();
                                }
                            }));
                            //recyclerView.post(() -> recyclerView.smoothScrollToPosition(1));
                            //recyclerView.requestLayout();
                            ((Solicitudes)getActivity()).hideLoader();
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
        inflater.inflate(R.menu.solicitudes_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.solicitudes_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "submit solicitudes", Toast.LENGTH_SHORT).show();
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
