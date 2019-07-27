package com.example.educasa.Inicio.InicioFragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.educasa.Inicio.Inicio;
import com.example.educasa.R;

public class InicioSolicitarCurso extends Fragment {
    private String bundlerecibido;
    private Button btn_confirmarclase;
    private Button btn_cancelar;


    public InicioSolicitarCurso() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio_solicitar_curso, container, false);
        btn_confirmarclase = v.findViewById(R.id.btn_confirmar_solicitar_curso);
        btn_cancelar = v.findViewById(R.id.btn_cancelar_curso);


        bundlerecibido = getArguments().getString("datosolicitarcurso");
        Toolbar toolbar = v.findViewById(R.id.inicio_toolbar_solicitar_curso);
        toolbar.setTitle("Solicitud de "+ bundlerecibido);
        if (getActivity() != null) {
            ((Inicio) getActivity()).setSupportActionBar(toolbar);
            ((Inicio) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Inicio) getActivity()).getSupportActionBar().show();
        }

        btn_confirmarclase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Confirmaste clase de "+ bundlerecibido, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return v;
    }

}
