package com.example.educasa.Perfil;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.educasa.Bienvenida.Bienvenida;
import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Perfil extends MenuBottom {
    private Button btn_cerrar_sesion;
    //private Button btn_editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // INICIO ACTIVIDAD PERFIL
        btn_cerrar_sesion = findViewById(R.id.btn_cerrar_sesion);
        //btn_editar = findViewById(R.id.btn_editar_perfil);

        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Bienvenida.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // FIN ACTIVIDAD PERFIL
    }
    @Override
    protected void onStart()  {
        super.onStart();
        Toast.makeText(this, "START PERFIL", Toast.LENGTH_SHORT).show();
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
}
