package com.example.educasa.Inicio;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v7.widget.SearchView;

import com.example.educasa.Inicio.InicioAdapters.AdaptadorMaterias;
import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Inicio extends MenuBottom {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // INICIO ACTIVIDAD INICIO

        Toolbar toolbar = findViewById(R.id.inicio_toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.inicio_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"matematica","fisica","comunicacion","historia","computacion","lengua","literatura","quimica","trigonometria","geometria","matematica","fisica","comunicacion","historia"};
        mAdapter = new AdaptadorMaterias(this, myDataset);
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        // FIN ACTIVIDAD INICIO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inicio_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.inicio_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //fragment.FiltrarAlumnos(s);
                Toast.makeText(Inicio.this, "submit", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //fragment.FiltrarAlumnos(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "START INICIO", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListenerMiMenu(this, 0);
    }

    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }
}
