package com.example.educasa.Inicio.InicioFragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educasa.Inicio.Inicio;
import com.example.educasa.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

public class InicioSolicitarCurso extends Fragment {
    private String bundlerecibido;
    private Button btn_confirmarclase;
    private Button btn_cancelar;
    private Spinner materia;
    private Button bfecha,bhora;
    private TextView fecha, hora;
    private int dia,mes,ano,minutos;

    public InicioSolicitarCurso() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio_solicitar_curso, container, false);
        btn_confirmarclase = v.findViewById(R.id.btn_confirmar_solicitar_curso);
        btn_cancelar = v.findViewById(R.id.btn_cancelar_curso);
        materia = (Spinner) v.findViewById(R.id.inicio_solicitud_curso_spinner);
        bfecha = v.findViewById(R.id.solicitar_curso_fecha);
        bhora = v.findViewById(R.id.inicio_solicitar_curso_hora);
        bfecha.setOnClickListener((View.OnClickListener) this);
        bhora.setOnClickListener((View.OnClickListener) this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Materia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materia.setAdapter(adapter);



        bundlerecibido = getArguments().getString("datosolicitarcurso");
        Toolbar toolbar = v.findViewById(R.id.inicio_toolbar_solicitar_curso);
        toolbar.setTitle("Solicitud de " + bundlerecibido);
        if (getActivity() != null) {
            ((Inicio) getActivity()).setSupportActionBar(toolbar);
            ((Inicio) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Inicio) getActivity()).getSupportActionBar().show();
        }

        btn_confirmarclase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Confirmaste clase de " + bundlerecibido, Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick (View v){
        Toast.makeText(getActivity(), "holaa", Toast.LENGTH_SHORT).show();
        if (v== fecha){
            final Calendar c= Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano= c.get (Calendar.YEAR);
            Toast.makeText(getActivity(), "holaadatee", Toast.LENGTH_SHORT).show();
            DatePickerDialog datePickerDialog = new DatePickerDialog
                    (v.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    fecha.setText(dayOfMonth+"/" +(monthOfYear+1)+"/"+year);

                }
            },
            dia,mes,ano);
            datePickerDialog.show();


        }if (v== hora){
            final Calendar c= Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos= c.get (Calendar.MINUTE);

        }
    }


}
