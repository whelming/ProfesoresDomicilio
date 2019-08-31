package com.tdlzgroup.educasa.Inicio.InicioFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioAdapters.AdaptadorInicio;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentInicio;
import com.tdlzgroup.educasa.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class InicioEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<ContentInicio> milista;
    private FirebaseFirestore db;
    private RecyclerView.LayoutManager mlayoutManager;
    private View v;
    private String Direccion = "DIRECCION";
    private float initialX,initialY;
    static final int MIN_DISTANCE = 125;

    public InicioEjm() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_inicio_ejm, container, false);
        db = FirebaseFirestore.getInstance();
        milista = new ArrayList<>();
       /* Toolbar toolbar = v.findViewById(R.id.inicio_toolbar);
        toolbar.setTitle("Inicio General");
        if (getActivity() != null) {
          ((Inicio)getActivity()).setSupportActionBar(toolbar);
        }*/

        recyclerView = v.findViewById(R.id.inicio_recycler);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(getContext());
        //recyclerView.setLayoutManager(layoutManager);
        mlayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mlayoutManager);
        LlenaMILista();
        return v;
    }

    private void LlenaMILista() {

        if (milista.size() > 0){
            milista.clear();
        }
        ((Inicio)getActivity()).showLoader();
        db.collection("materias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentInicio materia = new ContentInicio(
                                        document.getId(),
                                        document.getString("nombre"),
                                        document.getString("urlimagen")
                                );
                                milista.add(materia);
                                //Log.d("CHAAAAAA",  + " => " + document.getData());
                            }
                            AdaptadorInicio miadaptador = new AdaptadorInicio(getContext(), milista, new AdaptadorInicio.OnItemClickListener() {
                                @Override public void onItemClick(ContentInicio item) {
                                    //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                                    if (getActivity() != null){
                                        Bundle bundle = new Bundle();
                                        bundle.putString("IDMateria", item.getId());
                                        bundle.putString("NombreMateria", item.getNombre_materia());
                                        InicioProfesores fragmentListaProfesores = new InicioProfesores();
                                        fragmentListaProfesores.setArguments(bundle);
                                        ((Inicio) getActivity()).getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.frag_inicio_principal, fragmentListaProfesores, "fragmentListaProfesores")
                                                .addToBackStack(null).commit();
                                    }
                                }
                            });

                            //miadaptador.notifyDataSetChanged();
                            //recyclerView.invalidate();
                            recyclerView.setAdapter(miadaptador);
                            //update vista recycler
                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.smoothScrollToPosition(1);
                                }
                            });
                            ((Inicio)getActivity()).hideLoader();

                            Log.d("DATOS CARGADOS",  "DATOS CARGADOS OK ");



                        }
                        else {
                            ((Inicio)getActivity()).hideLoader();

                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });

        //milista.add(new ContentInicio("SDFDSfdf454df4","Comunicación",""));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

 /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inicio_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.inicio_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "submit inicio", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }*/
}
