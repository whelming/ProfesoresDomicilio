package com.example.educasa.Inicio.InicioFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.educasa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioEjm extends Fragment {


    public InicioEjm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_ejm, container, false);
    }

}