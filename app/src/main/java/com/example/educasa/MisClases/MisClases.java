package com.example.educasa.MisClases;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educasa.Inicio.Inicio;
import com.example.educasa.Inicio.InicioAdapters.AdaptadorMaterias;
import com.example.educasa.MenuBottom;
import com.example.educasa.MisClases.MisClasesAdapters.AdaptadorMisClases;
import com.example.educasa.R;

public class MisClases extends MenuBottom {
    TextView mitexto;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_clases);


        //mitexto = findViewById(R.id.texto_prueba);

        // INICIO ACTIVIDAD MISCLASES

        Toast.makeText(this, "CREATE", Toast.LENGTH_SHORT).show();

        Toolbar toolbar = findViewById(R.id.misclases_toolbar);
        toolbar.setTitle("Mis Clases");
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.misclases_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"matematica","fisica","comunicacion","historia","computacion"};
        mAdapter = new AdaptadorMisClases(this, myDataset);
        recyclerView.setAdapter(mAdapter);

        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        //recyclerView.setLayoutManager(mLayoutManager);

        // FIN ACTIVIDAD MISCLASES
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.misclases_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.misclases_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //fragment.FiltrarAlumnos(s);
                Toast.makeText(MisClases.this, "submit", Toast.LENGTH_SHORT).show();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", "Pagina recargada");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String myString = savedInstanceState.getString("message");
        //mitexto.setText(myString);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "START MIS CLASES", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "STOP", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "RESUME", Toast.LENGTH_SHORT).show();
        ListenerMiMenu(this, 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "PAUSE", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "DESTROY", Toast.LENGTH_SHORT).show();
    }

    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }


}
