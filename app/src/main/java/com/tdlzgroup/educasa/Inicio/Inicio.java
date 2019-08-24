package com.tdlzgroup.educasa.Inicio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Inicio.InicioFragments.InicioEjm;
import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;

public class Inicio extends MenuBottom {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        // INICIO ACTIVIDAD INICIO
        Intent intent = getIntent();
        String extratipouser = intent.getStringExtra("TIPOUSUARIO");
        //((Globales) this.getApplication()).settipoUsuario(intent.getStringExtra("TIPOUSUARIO"));
        //Globales.tipoUsuario = intent.getStringExtra("TIPOUSUARIO");
        if (extratipouser != null)
            ((Globales) this.getApplication()).setTipoUsuario(extratipouser);

        InicioEjm fragmentprincipal = new InicioEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_inicio_principal, fragmentprincipal);
        transaction.commit();

//        String tipoUsuario = ((Globales) this.getApplication()).gettipoUsuario();
        //String tipoUsuario = Globales.tipoUsuario;
        String tipoUsuario = ((Globales) this.getApplication()).getTipoUsuario();
        Toast.makeText(this, "TIPO: "+tipoUsuario, Toast.LENGTH_SHORT).show();

        // FIN ACTIVIDAD INICIO
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "START INICIO", Toast.LENGTH_SHORT).show();
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
