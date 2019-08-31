package com.tdlzgroup.educasa.Perfil;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.Perfil.PerfilFragments.PerfilEjm;
import com.tdlzgroup.educasa.R;

public class Perfil extends MenuBottom {
    public LinearLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        loader = findViewById(R.id.perfil_loader_container);

        PerfilEjm fragmentprincipal = new PerfilEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_perfil_principal, fragmentprincipal);
        transaction.commit();

        // INICIO ACTIVIDAD PERFIL
       /* btn_cerrar_sesion = findViewById(R.id.btn_cerrar_sesion);
        btn_editar = findViewById(R.id.btn_editar_perfil);

        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Bienvenida.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });*/

        // FIN ACTIVIDAD PERFIL


    }

    public void showLoader(){
        loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        loader.setVisibility(View.GONE);
    }

    @Override
    protected void onStart()  {
        super.onStart();

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
