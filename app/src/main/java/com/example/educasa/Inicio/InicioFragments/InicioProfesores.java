package com.example.educasa.Inicio.InicioFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.educasa.Inicio.Class.DetalleProfesoresDialog;
import com.example.educasa.Inicio.Inicio;
import com.example.educasa.Inicio.InicioAdapters.AdaptadorProfesores;
import com.example.educasa.Inicio.InicioModels.ContentListaProfesores;
import com.example.educasa.R;

import java.util.ArrayList;
import java.util.List;

public class InicioProfesores extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentListaProfesores> milista;
    private String bundlerecibido;
    private Button btnSolicitarCurso;

    public InicioProfesores() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio_profesores, container, false);
        btnSolicitarCurso = v.findViewById(R.id.btn_solicitar_curso);

        bundlerecibido = getArguments().getString("datousuario");
        Toolbar toolbar = v.findViewById(R.id.inicio_toolbar_profesores);
        toolbar.setTitle("Profesores de "+ bundlerecibido);
        if (getActivity() != null) {
            ((Inicio) getActivity()).setSupportActionBar(toolbar);
            ((Inicio) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Inicio) getActivity()).getSupportActionBar().show();
        }

        recyclerView = v.findViewById(R.id.inicio_profesores_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        milista = new ArrayList<>();
        LlenaMILista();

        recyclerView.setAdapter(new AdaptadorProfesores(getActivity(), milista, new AdaptadorProfesores.OnItemClickListener() {
            @Override public void onItemClick(ContentListaProfesores item) {
                Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                //R.id.TXT_Exit:
                DetalleProfesoresDialog dialog = new DetalleProfesoresDialog();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, DetalleProfesoresDialog.TAG);
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
        }));

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
        milista.add(new ContentListaProfesores(1,"Matias Diez Canseco"));
        milista.add(new ContentListaProfesores(2,"Alberto Fujimori Huaman"));
        milista.add(new ContentListaProfesores(3,"Pedro Pica Piedra"));
        milista.add(new ContentListaProfesores(4,"Maria Juana de los Arcos"));
        milista.add(new ContentListaProfesores(5,"Humberto Padilla Flores"));
        milista.add(new ContentListaProfesores(6,"Ximena Zarate Cardenas"));
        milista.add(new ContentListaProfesores(7,"Juan Pablo Diaz Medina"));
        milista.add(new ContentListaProfesores(8,"Pablo Heredia Manzanales"));
    }

}
