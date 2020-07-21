package com.tdlzgroup.educasa.VProfSolicitudes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.VProfSolicitudes.VProfFragments.VProfSolicitudesEjm;

public class VProfSolicitudes extends MenuBottom {

    public LinearLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vprof_solicitudes);
        loader = findViewById(R.id.vprof_solicitudes_loader_container);

        // INICIO ACTIVIDAD SOLICITUDES
        VProfSolicitudesEjm fragmentprincipal = new VProfSolicitudesEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.vprof_frag_solicitudes_principal, fragmentprincipal);
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
