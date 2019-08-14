package com.example.educasa.Perfil.PerfilFragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.educasa.Bienvenida.Bienvenida;
import com.example.educasa.Perfil.Perfil;
import com.example.educasa.R;

import java.util.regex.PatternSyntaxException;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilEjm extends Fragment {

    private Button  cerrarsesion;
    private Button editar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // INICIO ACTIVIDAD PERFIL
        editar = editar.findViewById(R.id.btn_editar_perfil);
        cerrarsesion= cerrarsesion.findViewById(R.id.btn_cerrar_sesion);

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Bienvenida.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),EditarPerfil.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // FIN ACTIVIDAD PERFIL


    }

    private void setContentView(int activity_perfil) {
    }


    public PerfilEjm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_ejm, container, false);
    }

}
