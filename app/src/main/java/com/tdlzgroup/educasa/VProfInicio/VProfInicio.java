package com.tdlzgroup.educasa.VProfInicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.VProfInicio.VProfInicioFragments.VProfInicioEjm;

public class VProfInicio extends MenuBottom {

    public LinearLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vprof_inicio);
        loader = findViewById(R.id.vprof_inicio_loader_container);

        // INICIO ACTIVIDAD VPROFINICIO
        VProfInicioEjm fragmentprincipal = new VProfInicioEjm();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.vprof_frag_inicio_principal, fragmentprincipal).commit();

        // FIN ACTIVIDAD VPROFINICIO
    }

    public void showLoader(){
        loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        loader.setVisibility(View.GONE);
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
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_inicio_principal);
        fragment.onActivityResult(requestCode, resultCode, data);
    }*/

}