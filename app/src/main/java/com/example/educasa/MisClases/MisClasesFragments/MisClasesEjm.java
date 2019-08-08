package com.example.educasa.MisClases.MisClasesFragments;

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

import com.example.educasa.MisClases.MisClases;
import com.example.educasa.MisClases.MisClasesAdapters.AdaptadorMisClases;
import com.example.educasa.MisClases.MisClasesModels.ContentMisClases;
import com.example.educasa.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MisClasesEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentMisClases> milista;

    public MisClasesEjm() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mis_clases_ejm, container, false);
        Toolbar toolbar = v.findViewById(R.id.misclases_toolbar);
        toolbar.setTitle("Mis Clases General");
        if (getActivity() != null) {
            ((MisClases)getActivity()).setSupportActionBar(toolbar);
        }
        recyclerView = v.findViewById(R.id.misclases_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        milista = new ArrayList<>();
        LlenaMILista();

        recyclerView.setAdapter(new AdaptadorMisClases(getActivity(), milista, new AdaptadorMisClases.OnItemClickListener() {
            @Override public void onItemClick(ContentMisClases item) {
                Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                // LINK TO NEW FRAGMENT
/*                if (getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("datousuario", item.getTextodemo());
                    MisClasesDetalle fragmentdetalle = new MisClasesDetalle();
                    fragmentdetalle.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_misclases_principal, fragmentdetalle, "fragmentDetalle")
                            .addToBackStack(null).commit();
                }*/
            }
        }));

        return v;
    }

    private void LlenaMILista() {
        milista.add(new ContentMisClases("1","Matemática","Carla Magno","https://www.infobae.com/new-resizer/4nEEHHVs5-II9SXlh4b4DI34iU4=/750x0/filters:quality(100)/s3.amazonaws.com/arc-wordpress-client-uploads/infobae-wp/wp-content/uploads/2018/05/01181948/05-Salma-Hayek.jpg"
                ,"05 pm | 12 Agosto "));
        milista.add(new ContentMisClases("2","Geometria","Eduardo Perez","https://laverdadnoticias.com/__export/1565136077693/sites/laverdad/img/2019/08/06/mauricio_ochmann_defiende_a_aislinn_derbez_tras_ser_criticada_por_foto_de_instagram.jpg_1834093470.jpg"
                ,"03:30 pm | 30 Agosto"));

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