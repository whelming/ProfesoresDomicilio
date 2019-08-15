package com.example.educasa.Inicio.InicioFragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.util.LocaleData;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;


import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;

import static android.app.Activity.RESULT_OK;

public class InicioSolicitarCurso extends Fragment implements View.OnClickListener {
    private String bundlerecibido;
    private Button btn_confirmarclase;
    private Button btn_cancelar;
    private Spinner materia;
    private Button bfecha, bhora;
    private TextView fecha, horaa;
    private int dia, mes, ano, hora, minutos;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;
    private View v;
    private Button bsubirimg1, bsubirimg2, bsubirimg3,direccion;

    private static final int CAMERA_REQUEST1 = 1887;
    private static final int CAMERA_REQUEST2 = 1888;
    private static final int CAMERA_REQUEST3 = 1889;
    private ImageView imagen1;
    private ImageView imagen2;
    private ImageView imagen3;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    public InicioSolicitarCurso() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_inicio_solicitar_curso, container, false);
        btn_confirmarclase = v.findViewById(R.id.btn_confirmar_solicitar_curso);
        //btn_cancelar = v.findViewById(R.id.btn_cancelar_curso);
        materia = (Spinner) v.findViewById(R.id.inicio_solicitud_curso_spinner);
        horaa = v.findViewById(R.id.inicio_solicitar_curso_hora);
        fecha = v.findViewById(R.id.solicitar_curso_fecha);
        bfecha = v.findViewById(R.id.solicitar_curso_fecha_boton);
        bhora = v.findViewById(R.id.inicio_solicitar_curso_hora_boton);
        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);
        direccion=v.findViewById(R.id.inicio_solicitar_curso_direccion);
        bsubirimg1 = v.findViewById(R.id.inicio_solicitud_btn_foto1);
        bsubirimg2 = v.findViewById(R.id.inicio_solicitud_btn_foto2);
        bsubirimg3 = v.findViewById(R.id.inicio_solicitud_btn_foto3);
        imagen1 = v.findViewById(R.id.inicio_solicitud_foto1);
        imagen2 = v.findViewById(R.id.inicio_solicitud_foto2);
        imagen3 = v.findViewById(R.id.inicio_solicitud_foto3);

        bsubirimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFoto(CAMERA_REQUEST1);
            }
        });
        bsubirimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFoto(CAMERA_REQUEST2);
            }
        });
        bsubirimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFoto(CAMERA_REQUEST3);
            }
        });

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getContext(),InicioMapaFragmen.class);
               startActivity(intent);            }
        });



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Materia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materia.setPrompt("Seleciona una materia");
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
        //btn_cancelar.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        getActivity().onBackPressed();
        //    }
        //});

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v) {
        //Toast.makeText(getActivity(), "holaa", Toast.LENGTH_SHORT).show();
        if (v == bfecha) {
            final Calendar c = Calendar.getInstance();
            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);

            //Toast.makeText(getActivity(), "holaa", Toast.LENGTH_SHORT).show();
            DatePickerDialog datePickerDialog = new DatePickerDialog
                    (v.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            fecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        }
                    },
                            ano, mes, dia);
            datePickerDialog.show();


        }
        if (v == bhora) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog
                    (v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            horaa.setText(hourOfDay + ":" + minute);

                        }
                    },
                            hora, minutos, false);
            timePickerDialog.show();

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAMERA_REQUEST1);
            } else {
                Toast.makeText(getContext(), "camera permission", Toast.LENGTH_LONG).show();
            }
        }


    }

    public void TomarFoto (int codigocamara){
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, codigocamara);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST1) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagen1.setImageBitmap(photo);
            bsubirimg1.setVisibility(View.INVISIBLE);
        }


        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST2) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagen2.setImageBitmap(photo);
            bsubirimg2.setVisibility(View.INVISIBLE);
        }

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST3) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagen3.setImageBitmap(photo);
            bsubirimg3.setVisibility(View.INVISIBLE);
        }

    }
}




