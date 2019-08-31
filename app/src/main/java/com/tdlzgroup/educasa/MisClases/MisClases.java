package com.tdlzgroup.educasa.MisClases;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.MisClases.MisClasesFragments.MisClasesEjm;
import com.tdlzgroup.educasa.R;

public class MisClases extends MenuBottom {
    public LinearLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_clases);
        loader = findViewById(R.id.misclases_loader_container);
        // INICIO ACTIVIDAD MISCLASES

        MisClasesEjm fragmentprincipal = new MisClasesEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_misclases_principal, fragmentprincipal);
        transaction.commit();

        // FIN ACTIVIDAD MISCLASES
    }
    public void showLoader(){
        loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", "Pagina recargada");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListenerMiMenu(this, 1);
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
