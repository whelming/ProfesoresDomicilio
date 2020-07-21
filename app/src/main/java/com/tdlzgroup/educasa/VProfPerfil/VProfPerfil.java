package com.tdlzgroup.educasa.VProfPerfil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.VProfPerfil.VProfPerfilFragments.VProfPerfilEjm;

public class VProfPerfil extends MenuBottom {

    public LinearLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vprof_perfil);
        loader = findViewById(R.id.vprof_perfil_loader_container);

        // INICIO ACTIVIDAD PERFIL
        VProfPerfilEjm fragmentprincipal = new VProfPerfilEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.vprof_frag_perfil_principal, fragmentprincipal);
        transaction.commit();
        // FIN ACTIVIDAD PERFIL
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
        ListenerMiMenu(this, 4);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_perfil_principal);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
