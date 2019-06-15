package com.example.educasa.Perfil;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.educasa.Bienvenida.Bienvenida;
import com.example.educasa.MainActivity;
import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Perfil extends MenuBottom {
    private Button btn_cerrar_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        ListenerMiMenu(this,4);

        // INICIO ACTIVIDAD PERFIL
        btn_cerrar_sesion = findViewById(R.id.btn_cerrar_sesion);
        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Bienvenida.class);
                startActivity(intent);
            }
        });
        // FIN ACTIVIDAD PERFIL
    }

    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }
}
