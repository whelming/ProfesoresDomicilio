package com.example.educasa.Perfil.PerfilFragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.educasa.Bienvenida.Bienvenida;
import com.example.educasa.Chat.Chat;
import com.example.educasa.Perfil.Perfil;
import com.example.educasa.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilEditar extends Fragment {
    private Button btn_guardar_cambios;
    private Button cambiarfoto;
    private static final int CAMERA_REQUEST1 = 1886;
    private ImageView foto;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    public PerfilEditar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        cambiarfoto = v.findViewById(R.id.perfil_editar_btn_foto);
        btn_guardar_cambios = v.findViewById(R.id.btn_guardar_cambios);
        foto = v.findViewById(R.id.perfil_editarfoto);
        Toolbar toolbar = v.findViewById(R.id.perfil_editar_toolbar);
        toolbar.setTitle("Editar Perfil ");
        if (getActivity() != null) {
            ((Perfil) getActivity()).setSupportActionBar(toolbar);
            ((Perfil) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Perfil) getActivity()).getSupportActionBar().show();
        }

        cambiarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST1);
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST1) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            foto.setImageBitmap(photo);
            cambiarfoto.setVisibility(View.INVISIBLE);
        }
    }


}
