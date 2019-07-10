package com.example.educasa.Inicio.InicioFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.educasa.Inicio.Inicio;
import com.example.educasa.Inicio.InicioAdapters.AdaptadorInicio;
import com.example.educasa.Inicio.InicioModels.ContentInicio;
import com.example.educasa.R;

import java.util.ArrayList;
import java.util.List;

public class InicioEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentInicio> milista;

    public InicioEjm() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inicio_ejm, container, false);
       /* Toolbar toolbar = v.findViewById(R.id.inicio_toolbar);
        toolbar.setTitle("Inicio General");
        if (getActivity() != null) {
          ((Inicio)getActivity()).setSupportActionBar(toolbar);
        }*/
        recyclerView = v.findViewById(R.id.inicio_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        milista = new ArrayList<>();
        LlenaMILista();

        recyclerView.setAdapter(new AdaptadorInicio(getActivity(), milista, new AdaptadorInicio.OnItemClickListener() {
            @Override public void onItemClick(ContentInicio item) {
                //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                if (getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("datousuario", item.getTextodemo());
                    InicioProfesores fragmentListaProfesores = new InicioProfesores();
                    fragmentListaProfesores.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_inicio_principal, fragmentListaProfesores, "fragmentListaProfesores")
                            .addToBackStack(null).commit();
                }
            }
        }));

        return v;
    }

    private void LlenaMILista() {
        milista.add(new ContentInicio(1,"Matemática",1,R.drawable.matematica));
        milista.add(new ContentInicio(2,"Física",2,R.drawable.matematica));
        milista.add(new ContentInicio(3,"Programación",3,R.drawable.matematica));
        milista.add(new ContentInicio(4,"Computación",4,R.drawable.matematica));
        milista.add(new ContentInicio(5,"Trigonometría",5,R.drawable.matematica));
        milista.add(new ContentInicio(6,"Química Básica",6,R.drawable.matematica));
        milista.add(new ContentInicio(7,"Cálculo",7,R.drawable.matematica));
        milista.add(new ContentInicio(8,"Geometría",8,R.drawable.matematica));
        milista.add(new ContentInicio(9,"Comunicación",9,R.drawable.matematica));
        milista.add(new ContentInicio(10,"Literatura",10,R.drawable.matematica));
        milista.add(new ContentInicio(11,"Historia",11,R.drawable.matematica));
        milista.add(new ContentInicio(12,"Ciencia",12,R.drawable.matematica));
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
