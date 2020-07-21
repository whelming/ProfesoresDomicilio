package com.tdlzgroup.educasa.Inicio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Inicio.InicioFragments.InicioEjm;
import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.Notificaciones;
import com.tdlzgroup.educasa.R;

public class Inicio extends MenuBottom {
    public LinearLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        loader = findViewById(R.id.inicio_loader_container);

        // INICIO ACTIVIDAD INICIO

        /*Intent intent = getIntent();
        String extratipouser = intent.getStringExtra("TIPOUSUARIO");*/

        //((Globales) this.getApplication()).settipoUsuario(intent.getStringExtra("TIPOUSUARIO"));
        //Globales.tipoUsuario = intent.getStringExtra("TIPOUSUARIO");

        /*if (extratipouser != null)
            ((Globales) this.getApplication()).setTipoUsuario(extratipouser);*/

        //String tipoUsuario = ((Globales) this.getApplication()).getTipoUsuario();
        //Toast.makeText(this, "TIPO: "+tipoUsuario, Toast.LENGTH_SHORT).show();

        InicioEjm fragmentprincipal = new InicioEjm();
        //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().beginTransaction()
        .add(R.id.frag_inicio_principal, fragmentprincipal).commit();

//        String tipoUsuario = ((Globales) this.getApplication()).gettipoUsuario();
        //String tipoUsuario = Globales.tipoUsuario;

        // FIN ACTIVIDAD INICIO
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_inicio_principal);
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
