package com.example.educasa.Solicitudes.SolicitudesFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.educasa.Solicitudes.Solicitudes;
import com.example.educasa.R;

public class SolicitudesDetalle extends Fragment {
    private String bundlerecibido;
    private Button btnEliminarSolicitud;

    public SolicitudesDetalle() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitudes_detalle, container, false);
        btnEliminarSolicitud = v.findViewById(R.id.btn_eliminar_solicitud);
        bundlerecibido = getArguments().getString("datodetallesolicitud");
        Toolbar toolbar = v.findViewById(R.id.solicitudes_toolbar_detalle);
        toolbar.setTitle("Detalle de "+ bundlerecibido);
        if (getActivity() != null) {
            ((Solicitudes) getActivity()).setSupportActionBar(toolbar);
            ((Solicitudes) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Solicitudes) getActivity()).getSupportActionBar().show();
        }
        btnEliminarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Seguro...")
                        .setMessage("que deseas eliminar esta solicitud.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Eliminaste la Solicitud", Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        return v;
    }

}
