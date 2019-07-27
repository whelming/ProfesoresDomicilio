package com.example.educasa.Solicitudes;

import android.content.Context;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;

import com.example.educasa.MenuBottom;
import com.example.educasa.R;
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
