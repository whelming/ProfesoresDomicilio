package com.example.educasa.Solicitudes;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.educasa.BottomNavigationViewHelper;
import com.example.educasa.MenuBottom;
import com.example.educasa.MisClases.MisClases;
import com.example.educasa.MisClases.MisClasesAdapters.AdaptadorMisClases;
import com.example.educasa.R;
import com.example.educasa.Solicitudes.SolicitudesAdapters.AdaptadorSolicitudes;

public class Solicitudes extends MenuBottom {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);
        //ListenerMiMenu(this,3);

        // INICIO ACTIVIDAD SOLICITUDES
        Toast.makeText(this, "CREATE SOLICITUDES", Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.solicitudes_toolbar);
        toolbar.setTitle("Solicitudes de Cursos");
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.solicitudes_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"matematica","fisica","comunicacion"};
        mAdapter = new AdaptadorSolicitudes(this, myDataset);
        recyclerView.setAdapter(mAdapter);
        // FIN ACTIVIDAD SOLICITUDES
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.solicitudes_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.solicitudes_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //fragment.FiltrarAlumnos(s);
                Toast.makeText(Solicitudes.this, "submit solicitudes", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "START SOLICITUD", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListenerMiMenu(this, 3);
    }

    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }
}
