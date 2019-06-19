package com.example.educasa.Solicitudes;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
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
import com.example.educasa.Solicitudes.SolicitudesFragments.SolicitudesEjm;

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

        SolicitudesEjm fragmentprincipal = new SolicitudesEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_solicitudes_principal, fragmentprincipal);
        transaction.commit();
        // FIN ACTIVIDAD SOLICITUDES
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
