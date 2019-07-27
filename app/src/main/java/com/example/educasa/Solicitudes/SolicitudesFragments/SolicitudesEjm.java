package com.example.educasa.Solicitudes.SolicitudesFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.educasa.Solicitudes.Solicitudes;
import com.example.educasa.Solicitudes.SolicitudesAdapters.AdaptadorSolicitudes;
import com.example.educasa.Solicitudes.SolicitudesModels.ContentSolicitudes;
import com.example.educasa.R;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentSolicitudes> milista;

    public SolicitudesEjm() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_solicitudes_ejm, container, false);
        Toolbar toolbar = v.findViewById(R.id.solicitudes_toolbar);
        toolbar.setTitle("Solicitudes General");
        if (getActivity() != null) {
            ((Solicitudes)getActivity()).setSupportActionBar(toolbar);
        }
        recyclerView = v.findViewById(R.id.solicitudes_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        milista = new ArrayList<>();
        LlenaMILista();

        recyclerView.setAdapter(new AdaptadorSolicitudes(getActivity(), milista, new AdaptadorSolicitudes.OnItemClickListener() {
            @Override public void onItemClick(ContentSolicitudes item) {
                //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                if (getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("datodetallesolicitud", item.getTextodemo());
                    SolicitudesDetalle fragmentSolicitudDetalle = new SolicitudesDetalle();
                    fragmentSolicitudDetalle.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_solicitudes_principal, fragmentSolicitudDetalle, "fragdetallesolicitud")
                            .addToBackStack(null).commit();
                }
            }
        }));

        return v;
    }

    private void LlenaMILista() {
        milista.add(new ContentSolicitudes(1,"Tarea de Matematica"));
        milista.add(new ContentSolicitudes(2,"Proyecto de Fisica"));
        milista.add(new ContentSolicitudes(3,"Repaso para Examen de Quimica"));
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
