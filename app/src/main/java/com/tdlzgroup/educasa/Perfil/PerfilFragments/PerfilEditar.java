package com.tdlzgroup.educasa.Perfil.PerfilFragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Perfil.Perfil;
import com.tdlzgroup.educasa.R;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class PerfilEditar extends Fragment {
    private Button btn_guardar_cambios;
    private Button cambiarfoto;
    private static final int CAMERA_REQUEST1 = 1886;
    private ImageView foto;
    private EditText perfil_nombres;
    private EditText perfil_dni;
    private EditText perfil_telefono;
    private EditText perfil_correo;
    private EditText perfil_direccion;

    private FirebaseUser user;
    private FirebaseFirestore db;
    private String IDAlumno;
    private String tipoUsuario;
    private DocumentReference miperfil;
    private View v;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    public PerfilEditar() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_editar_perfil, container, false);
        perfil_nombres = v.findViewById(R.id.perfil_nombres);
        perfil_dni = v.findViewById(R.id.perfil_dni);
        perfil_telefono = v.findViewById(R.id.perfil_telefono);
        perfil_correo = v.findViewById(R.id.perfil_correo);
        perfil_direccion = v.findViewById(R.id.perfil_direccion);

        perfil_correo.setFocusable(false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        IDAlumno = user.getUid();
        tipoUsuario = ((Globales) getActivity().getApplicationContext()).getTipoUsuario();

        cambiarfoto = v.findViewById(R.id.perfil_editar_btn_foto);
        btn_guardar_cambios = v.findViewById(R.id.btn_guardar_cambios);
        foto = v.findViewById(R.id.perfil_editarfoto);
        Toolbar toolbar = v.findViewById(R.id.perfil_editar_toolbar);
        toolbar.setTitle("Editar Perfil");
        if (getActivity() != null) {
            ((Perfil) getActivity()).setSupportActionBar(toolbar);
            ((Perfil) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Perfil) getActivity()).getSupportActionBar().show();
        }

        if (user != null) {
            db = FirebaseFirestore.getInstance();
            if (tipoUsuario.equals("alumnos")) {
                miperfil = db.collection("alumnos").document(IDAlumno);
            } else if (tipoUsuario.equals("profesores")) {
                miperfil = db.collection("profesores").document(IDAlumno);
            }

            loadDataPerfil(miperfil);

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

        btn_guardar_cambios.setOnClickListener((View v) -> {
            //miperfil = db.collection("alumnos").document(IDAlumno);
            //btn_guardar_cambios.setEnabled(false);
            String nombres = perfil_nombres.getText().toString();
            String dni = perfil_dni.getText().toString();
            String telefono = perfil_telefono.getText().toString();
            String direccion = perfil_direccion.getText().toString();
            updateChanges(nombres,dni,telefono,direccion);
        });
        return v;
    }

    private void loadDataPerfil(DocumentReference miperfil) {
        ((Perfil)getActivity()).showLoader();
        miperfil.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        perfil_nombres.setText(document.getString("nombres"));
                        perfil_dni.setText(document.getString("dni"));
                        perfil_telefono.setText(document.getString("telefono"));
                        perfil_correo.setText(user.getEmail());
                        perfil_direccion.setText(document.getString("direccion"));
                        //Glide.with(getContext()).load(document.getString("urlfoto")).centerCrop().placeholder(R.drawable.user).into(circleimage);
                        ((Perfil)getActivity()).hideLoader();
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
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

    private void updateChanges(String nombres, String dni, String telefono, String direccion ){
        ((Perfil)getActivity()).showLoader();
        miperfil
                .update("nombres", nombres,
                        "dni", dni,
                        "telefono", telefono,
                        "direccion", direccion)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d("UPDATEOK", "DocumentSnapshot successfully updated!");

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nombres)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            ((Perfil)getActivity()).hideLoader();
                                            Toast.makeText(getContext(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                                            getActivity().onBackPressed();
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w("EEEEEEUPDATE", "Error updating document", e);
                        Toast.makeText(getContext(), "Ocurrió un error", Toast.LENGTH_SHORT).show();
                    }
                });



    }
    private void saveChanges(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("stringExample", "Hello world!");
        docData.put("booleanExample", true);
        docData.put("numberExample", 3.14159265);
        docData.put("dateExample", new Timestamp(new Date()));
        docData.put("listExample", Arrays.asList(1, 2, 3));
        docData.put("nullExample", null);

        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("a", 5);
        nestedData.put("b", true);

        docData.put("objectExample", nestedData);

        db.collection("chats").document("Zow7It9jY6MNB8L1DXI4")
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("OKOKOKOK", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("EEEE", "Error writing document", e);
                    }
                });
    }


}
