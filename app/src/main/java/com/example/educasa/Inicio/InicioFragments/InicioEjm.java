package com.example.educasa.Inicio.InicioFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.educasa.R;

public class InicioEjm extends Fragment {

    public InicioEjm() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio_ejm, container, false);
        return v;
    }
}
