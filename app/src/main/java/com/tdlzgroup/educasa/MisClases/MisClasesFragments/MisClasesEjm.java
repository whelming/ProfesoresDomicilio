package com.tdlzgroup.educasa.MisClases.MisClasesFragments;

import android.app.AlertDialog;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Inicio.Class.DetalleProfesoresDialog;
import com.tdlzgroup.educasa.Inicio.InicioAdapters.AdaptadorProfesores;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentListaProfesores;
import com.tdlzgroup.educasa.MisClases.MisClases;
import com.tdlzgroup.educasa.MisClases.MisClasesAdapters.AdaptadorMisClases;
import com.tdlzgroup.educasa.MisClases.MisClasesModels.ContentMisClases;
import com.tdlzgroup.educasa.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MisClasesEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentMisClases> milista;

    private FirebaseFirestore db;
    public AlertDialog dialog;
    private String IDAlumno;
    //RecyclerView.LayoutManager mlayoutManager;

    public MisClasesEjm() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mis_clases_ejm, container, false);
        Toolbar toolbar = v.findViewById(R.id.misclases_toolbar);
        toolbar.setTitle("Mis Clases General");
        if (getActivity() != null) {
            ((MisClases)getActivity()).setSupportActionBar(toolbar);
        }
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        //IDAlumno = ((Globales) getActivity().getApplicationContext()).getIDUsuario();
        IDAlumno = currentFirebaseUser.getUid();
        Toast.makeText(getContext(), ""+IDAlumno, Toast.LENGTH_SHORT).show();

        db = FirebaseFirestore.getInstance();
        milista = new ArrayList<>();
       /* Toolbar toolbar = v.findViewById(R.id.inicio_toolbar);
        toolbar.setTitle("Inicio General");
        if (getActivity() != null) {
          ((Inicio)getActivity()).setSupportActionBar(toolbar);
        }*/
        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("Cargando...")
                .setCancelable(false)
                .build();
        dialog.show();

        recyclerView = v.findViewById(R.id.misclases_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        milista = new ArrayList<>();
        LlenaMILista();
/*
        recyclerView.setAdapter(new AdaptadorMisClases(getActivity(), milista, new AdaptadorMisClases.OnItemClickListener() {
            @Override public void onItemClick(ContentMisClases item) {
                Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();

                if (getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("datousuario", item.getTextodemo());
                    MisClasesDetalle fragmentdetalle = new MisClasesDetalle();
                    fragmentdetalle.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_misclases_principal, fragmentdetalle, "fragmentDetalle")
                            .addToBackStack(null).commit();

            }
        }));}*/

        return v;
    }

    private void LlenaMILista() {

        if (milista.size() > 0){
            milista.clear();
        }
        db.collection("alumnos/"+IDAlumno+"/misclases")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentMisClases misclases = new ContentMisClases(
                                        document.getId(),
                                        //document.getDocumentReference("materia"),
                                        document.getTimestamp("fechahora")
                                );

                                DocumentReference materiaRef = document.getDocumentReference("materia");
                                materiaRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                misclases.setNombremateria(document.getString("nombre"));
                                                //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                            } else {
                                                //Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            //Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });
                                DocumentReference profesorRef = document.getDocumentReference("profesor");
                                profesorRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                misclases.setUrlfoto(document.getString("urlfoto"));
                                                misclases.setNombreprofesor(document.getString("nombres"));
                                                //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                            } else {
                                                //Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            //Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });

                                milista.add(misclases);
                                //Log.d("CHAAAAAA",  + " => " + document.getData());
                            }
                            recyclerView.setAdapter(new AdaptadorMisClases(getActivity(), milista, new AdaptadorMisClases.OnItemClickListener() {
                                @Override public void onItemClick(ContentMisClases item) {
                                    Toast.makeText(getActivity(), item.getFechahora()+"", Toast.LENGTH_SHORT).show();
                                    /*DetalleProfesoresDialog dialog = new DetalleProfesoresDialog();
                                    Bundle args = new Bundle();
                                    args.putString("idprofesor", item.getId());
                                    args.putString("nombresprofesor", item.getUrlfoto());
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    dialog.setArguments(args);
                                    dialog.show(ft, DetalleProfesoresDialog.TAG);*/
                                }
                            }));
                            //Log.d("DATOS CARGADOS",  "DATOS CARGADOS OK ");

                            dialog.dismiss();

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