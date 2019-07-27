package com.example.educasa.Inicio;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

import com.example.educasa.Inicio.InicioFragments.InicioEjm;
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

        InicioEjm fragmentprincipal = new InicioEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_inicio_principal, fragmentprincipal);
        transaction.commit();
        // FIN ACTIVIDAD INICIO
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
