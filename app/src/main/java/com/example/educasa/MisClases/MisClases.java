package com.example.educasa.MisClases;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import android.widget.Toast;

import com.example.educasa.MenuBottom;
import com.example.educasa.MisClases.MisClasesFragments.MisClasesEjm;
import com.example.educasa.R;

public class MisClases extends MenuBottom {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_clases);

        // INICIO ACTIVIDAD MISCLASES

        Toast.makeText(this, "CREATE", Toast.LENGTH_SHORT).show();

        MisClasesEjm fragmentprincipal = new MisClasesEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_misclases_principal, fragmentprincipal);
        transaction.commit();

        // FIN ACTIVIDAD MISCLASES
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("message", "Pagina recargada");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String myString = savedInstanceState.getString("message");
        //mitexto.setText(myString);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "START MIS CLASES", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "STOP", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "RESUME", Toast.LENGTH_SHORT).show();
        ListenerMiMenu(this, 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "PAUSE", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "DESTROY", Toast.LENGTH_SHORT).show();
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
