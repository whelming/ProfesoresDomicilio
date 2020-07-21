package com.tdlzgroup.educasa.MisClases.MisClasesFragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Inicio.Class.DetalleProfesoresDialog;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioAdapters.AdaptadorProfesores;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentListaProfesores;
import com.tdlzgroup.educasa.MisClases.MisClases;
import com.tdlzgroup.educasa.MisClases.MisClasesAdapters.AdaptadorMisClases;
import com.tdlzgroup.educasa.MisClases.MisClasesModels.ContentMisClases;
import com.tdlzgroup.educasa.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

public class MisClasesEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentMisClases> milista;

    private FirebaseFirestore db;
    public AlertDialog dialog;
    private String IDAlumno;
    private View v;
    private Context context;
    public MisClasesEjm() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mis_clases_ejm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Toolbar toolbar = v.findViewById(R.id.misclases_toolbar);
        toolbar.setTitle("Mis Clases");

        if (getActivity() != null) {
            ((MisClases)getActivity()).setSupportActionBar(toolbar);
        }

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        IDAlumno = currentFirebaseUser.getUid();

        db = FirebaseFirestore.getInstance();
        milista = new ArrayList<>();

        recyclerView = v.findViewById(R.id.misclases_recycler);
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
        ((MisClases)getActivity()).showLoader();

        db.collection("clases")
                .whereEqualTo("tipo", 2)
                .whereEqualTo("idAlum", IDAlumno)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()){

                                Toasty.info(context, "Aún no tienes Clases", Toast.LENGTH_SHORT, true).show();
                                recyclerView.setBackground(ContextCompat.getDrawable(context, R.drawable.placeholder_noclases));
                                ((MisClases)getActivity()).hideLoader();
                            }

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentMisClases misclases = new ContentMisClases(
                                        document.getId(),
                                        document.getTimestamp("fechahora")
                                );
                                misclases.setNombremateria(document.getString("materia"));
                                misclases.setUrlfoto(document.getString("urlfotoProf"));
                                misclases.setNombreprofesor(document.getString("nombreProf"));
                                milista.add(misclases);
                                recyclerView.setAdapter(new AdaptadorMisClases(context, milista, new AdaptadorMisClases.OnItemClickListener() {
                                    @Override public void onItemClick(ContentMisClases item) {
                                    }
                                }));
                                //recyclerView.post(() -> recyclerView.smoothScrollToPosition(1));
                                //recyclerView.requestLayout();
                                ((MisClases)getActivity()).hideLoader();

                                /*
                                DocumentReference materiaRef = document.getDocumentReference("materia");
                                */

                            }

                        }
                        else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
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
        inflater.inflate(R.menu.misclases_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.misclases_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "submit misclases", Toast.LENGTH_SHORT).show();
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