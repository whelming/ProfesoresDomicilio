package com.tdlzgroup.educasa.Solicitudes;

import android.content.Context;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesFragments.SolicitudesEjm;

public class Solicitudes extends MenuBottom {
    public LinearLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);
        loader = findViewById(R.id.solicitudes_loader_container);

        // INICIO ACTIVIDAD SOLICITUDES
        SolicitudesEjm fragmentprincipal = new SolicitudesEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_solicitudes_principal, fragmentprincipal);
        transaction.commit();
        // FIN ACTIVIDAD SOLICITUDES
    }

    public void showLoader(){
        loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        loader.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
