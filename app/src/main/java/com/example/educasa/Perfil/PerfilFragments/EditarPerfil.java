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


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPerfil extends Fragment {
    private Button btn_editar;
    public EditarPerfil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        //btn_editar = v.findViewById(R.id.btn_editar_perfil);


        return v;
    }

}
