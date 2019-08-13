package com.example.educasa.Solicitudes.SolicitudesFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.educasa.Solicitudes.Solicitudes;
import com.example.educasa.R;
import com.example.educasa.Solicitudes.SolicitudesAdapters.AdaptadorSolicitudes;
import com.example.educasa.Solicitudes.SolicitudesAdapters.Solicitud_detalle_Adapter;
import com.example.educasa.Solicitudes.SolicitudesModels.ContentSolicitudes;
import com.example.educasa.Solicitudes.SolicitudesModels.ContentSolicitudesDetalle;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesDetalle extends Fragment {
    private String bundlerecibido;
    private Button btnEliminarSolicitud;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentSolicitudesDetalle> Lista;
    private Button  btnAceptarSolicitud;


    public SolicitudesDetalle() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitudes_detalle, container, false);
        //btnEliminarSolicitud = v.findViewById(R.id.btn_eliminar_solicitud);
        bundlerecibido = getArguments().getString("datodetallesolicitud");
        Toolbar toolbar = v.findViewById(R.id.solicitudes_toolbar_detalle);
        toolbar.setTitle("Detalle de " + bundlerecibido);
        if (getActivity() != null) {
            ((Solicitudes) getActivity()).setSupportActionBar(toolbar);
            ((Solicitudes) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Solicitudes) getActivity()).getSupportActionBar().show();
        }
        recyclerView = v.findViewById(R.id.recycler_detalle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Lista = new ArrayList<>();
        LlenaMILista();
        recyclerView.setAdapter(new Solicitud_detalle_Adapter(getActivity(), Lista, new Solicitud_detalle_Adapter.OnItemClickListener() {

            @Override
            public void onItemClick(ContentSolicitudes item) {

            }
        }));


        return v;
    }

    private void LlenaMILista() {
        Lista.add(new ContentSolicitudesDetalle("Juan pedrito", "20 soles", "en el puente bolognesi ",R.drawable.fotoperfiljuego));
        Lista.add(new ContentSolicitudesDetalle("Elsa Broson", "2000 soles", "en tu casa",R.drawable.fotoperfiljuego));
        Lista.add(new ContentSolicitudesDetalle("Alan Brito", "20 soles", "en mi taller a las 6:00",R.drawable.fotoperfiljuego));
        Lista.add(new ContentSolicitudesDetalle("Jose Masinga", "1 soles", "en el puente bolognesi ",R.drawable.fotoperfiljuego));
        Lista.add(new ContentSolicitudesDetalle("Elver Galarg", "9990 soles", "en el puente bolognesi ",R.drawable.fotoperfiljuego));
        Lista.add(new ContentSolicitudesDetalle("xiomara Junior" ,"32 soles", "en el puente bolognesi ",R.drawable.fotoperfiljuego));

    }

    public void Eliminar(int pos){
        //eliminas de tu lista;
        // Refrescas el adaptador;
    }

}
