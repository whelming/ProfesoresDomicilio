package com.example.educasa.Solicitudes;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.educasa.BottomNavigationViewHelper;
import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Solicitudes extends MenuBottom {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);
        //ListenerMiMenu(this,3);

        // INICIO ACTIVIDAD SOLICITUDES

        // FIN ACTIVIDAD SOLICITUDES
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListenerMiMenu(this, 3);
        Toast.makeText(this, "START SOLICITUD", Toast.LENGTH_SHORT).show();
    }
    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }
}
