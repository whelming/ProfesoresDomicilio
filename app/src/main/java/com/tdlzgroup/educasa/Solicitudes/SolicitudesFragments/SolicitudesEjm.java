package com.tdlzgroup.educasa.Solicitudes.SolicitudesFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class SolicitudesEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentSolicitudes> milista;
    FirebaseUser user;
    private FirebaseFirestore db;
    private String ID;

    public SolicitudesEjm() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_solicitudes_ejm, container, false);
        Toolbar toolbar = v.findViewById(R.id.solicitudes_toolbar);
        toolbar.setTitle("Mis Solicitudes");
        if (getActivity() != null) {
            ((Solicitudes)getActivity()).setSupportActionBar(toolbar);
        }
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser() ;
        ID = user.getUid();
        recyclerView = v.findViewById(R.id.solicitudes_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        milista = new ArrayList<>();
        LlenaMILista();

        return v;
    }

    private void LlenaMILista() {
        if (milista.size() > 0){
            milista.clear();
        }

        ((Solicitudes)getActivity()).showLoader();

        //db.collection("materias/"+IDMateria+"/listaprofesores")
        db.collection("alumnos/"+ID+"/solicitudes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentSolicitudes missolicitudes = new ContentSolicitudes(
                                        document.getId(),
                                        document.getString("titulo"),
                                        document.getDouble("interesados"),
                                        document.getTimestamp("fechahora")
                                );
                                milista.add(missolicitudes);

                                //Log.d("CHAAAAAA",  + " => " + document.getData());
                            }
                            recyclerView.setAdapter(new AdaptadorSolicitudes(getActivity(), milista, (ContentSolicitudes item) -> {
                                //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                                if (getActivity() != null){
                                    Bundle bundle = new Bundle();
                                    bundle.putString("datodetallesolicitud", item.getId());
                                    SolicitudesDetalle fragmentSolicitudDetalle = new SolicitudesDetalle();
                                    fragmentSolicitudDetalle.setArguments(bundle);
                                    getActivity().getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.frag_solicitudes_principal, fragmentSolicitudDetalle, "fragdetallesolicitud")
                                            .addToBackStack(null).commit();
                                }

                            }));
                            recyclerView.post(() -> recyclerView.smoothScrollToPosition(1));
                            ((Solicitudes)getActivity()).hideLoader();

                        }
                        else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });
/*        milista.add(new ContentSolicitudes("111","Tarea de Filosofia",3, "15 Julio - 05:00pm"));
        milista.add(new ContentSolicitudes("222","Tarea de Mate",2, "18 Julio - 01:00pm"));
        milista.add(new ContentSolicitudes("333","Tarea de Edu",6, "19 Julio - 02:00pm"));*/



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
