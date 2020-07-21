package com.tdlzgroup.educasa.VProfMisClases;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.VProfMisClases.VProfMisClasesFragments.VProfMisClasesEjm;

public class VProfMisClases extends MenuBottom {

    public LinearLayout loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vprof_mis_clases);
        loader = findViewById(R.id.vprof_misclases_loader_container);
        // INICIO ACTIVIDAD MISCLASES

        VProfMisClasesEjm fragmentprincipal = new VProfMisClasesEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.vprof_frag_misclases_principal, fragmentprincipal);
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
