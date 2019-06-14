package com.example.educasa.Bienvenida;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.educasa.MainActivity;
import com.example.educasa.R;

public class Bienvenida extends AppCompatActivity {
    Button btn_iniciar_sesion;
    Button btn_crear_cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        // INICIO ACTIVIDAD BIENVENIDA

        btn_iniciar_sesion = findViewById(R.id.btn_iniciar_sesion);
        btn_crear_cuenta = findViewById(R.id.btn_crear_cuenta);

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent0 = new Intent(Bienvenida.this, MainActivity.class);
                startActivity(intent0);
            }
        });

        btn_crear_cuenta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Bienvenida.this, "CRAR CUENTA", Toast.LENGTH_SHORT).show();
            }
        });

        // FIN ACTIVIDAD BIENVENIDA

    }
}
