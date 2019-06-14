package com.example.educasa.Bienvenida.BienvenidaFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.educasa.R;

//import educasa.example.com.educasa.R;

public class BienvenidaFragment1 extends Fragment {


    public BienvenidaFragment1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bienvenida_fragment1, container, false);
    }

}
