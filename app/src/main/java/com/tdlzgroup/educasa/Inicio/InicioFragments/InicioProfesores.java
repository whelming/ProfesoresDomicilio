package com.tdlzgroup.educasa.Inicio.InicioFragments;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Inicio.Class.DetalleProfesoresDialog;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioAdapters.AdaptadorInicio;
import com.tdlzgroup.educasa.Inicio.InicioAdapters.AdaptadorProfesores;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentInicio;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentListaProfesores;
import com.tdlzgroup.educasa.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class InicioProfesores extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentListaProfesores> milista;
    private String bundlerecibido;
    private String IDMateria;

    private Button btnSolicitarCurso;

    private FirebaseFirestore db;
    public AlertDialog dialog;
    RecyclerView.LayoutManager mlayoutManager;

    public InicioProfesores() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio_profesores, container, false);
        btnSolicitarCurso = v.findViewById(R.id.btn_solicitar_curso);

        IDMateria = getArguments().getString("IDMateria");
        bundlerecibido = getArguments().getString("NombreMateria");

        Toolbar toolbar = v.findViewById(R.id.inicio_toolbar_profesores);
        toolbar.setTitle("Profesores de "+ bundlerecibido);
        if (getActivity() != null) {
            ((Inicio) getActivity()).setSupportActionBar(toolbar);
            ((Inicio) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Inicio) getActivity()).getSupportActionBar().show();
        }

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

        recyclerView = v.findViewById(R.id.inicio_profesores_recycler);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(getActivity());
        //recyclerView.setLayoutManager(layoutManager);
        mlayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mlayoutManager);
        milista = new ArrayList<>();
        LlenaMILista();



        btnSolicitarCurso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Solicitar Curso de "+ bundlerecibido, Toast.LENGTH_SHORT).show();
                if (getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("datosolicitarcurso", bundlerecibido);
                    InicioSolicitarCurso fragmentSolicitarCurso = new InicioSolicitarCurso();
                    fragmentSolicitarCurso.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_inicio_principal, fragmentSolicitarCurso, "fragmentSolicitarCurso")
                            .addToBackStack(null).commit();
                }
            }
        });

        return v;
    }

    private void LlenaMILista() {
        if (milista.size() > 0){
            milista.clear();
        }
        db.collection("materias/"+IDMateria+"/listaprofesores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentListaProfesores materia = new ContentListaProfesores(
                                        document.getString("ID"),
                                        document.getString("urlfoto"),
                                        document.getString("nombre"),
                                        document.getDouble("puntuacion")
                                );
                                milista.add(materia);
                                //Log.d("CHAAAAAA",  + " => " + document.getData());
                            }
                            recyclerView.setAdapter(new AdaptadorProfesores(getActivity(), milista, new AdaptadorProfesores.OnItemClickListener() {
                                @Override public void onItemClick(ContentListaProfesores item) {
                                    //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                                    DetalleProfesoresDialog dialog = new DetalleProfesoresDialog();
                                    Bundle args = new Bundle();
                                    args.putString("idprofesor", item.getId());
                                    args.putString("nombresprofesor", item.getNombres());
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    dialog.setArguments(args);
                                    dialog.show(ft, DetalleProfesoresDialog.TAG);
                                }
                            }));
                            Log.d("DATOS CARGADOS",  "DATOS CARGADOS OK ");

                            dialog.dismiss();

                        }
                        else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });


                //R.id.TXT_Exit:



                /*new AlertDialog.Builder(getActivity())
                        .setTitle(item.getTextodemo())
                        .setMessage("Persona responsable y con unagran trayectoria.\n\nEdad: 35\nCategorías:\n- Matemática\n- Física\n- Trigonometría")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Cerrar", Toast.LENGTH_SHORT).show();
                            }
                        })
                        //.setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_perfil_black_24dp)
                        .show();*/

    }

}
