package com.tdlzgroup.educasa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Perfil.Perfil;
import com.tdlzgroup.educasa.VProfInicio.VProfInicio;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends MenuBottom {
    Button btn_tipoprofesor;
    Button btn_tipoalumno;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        db = FirebaseFirestore.getInstance();

        // INICIO ACTIVIDAD MAINACTIVITY

        btn_tipoprofesor = findViewById(R.id.main_button_profesor);
        btn_tipoalumno = findViewById(R.id.main_button_alumno);

        btn_tipoprofesor.setOnClickListener((View v) -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirmar Perfil: Profesor")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            seleccionPerfil(2);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

        });
        btn_tipoalumno.setOnClickListener((View v) -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirmar Perfil: Alumno")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            seleccionPerfil(3);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {

        }

        // FIN ACTIVIDAD MAINACTIVITY

    }

    private void seleccionPerfil(int tipoperfil){

        String idUser = user.getUid();
        DocumentReference miperfil = db.collection("usuarios").document(idUser);

        Map<String,Object> updates = new HashMap<>();

        // profesor
        if (tipoperfil== 2){
            updates.put("sexo", 1);
            updates.put("descripcion", "");
            updates.put("profesion", "");
            updates.put("materias", Arrays.asList());
            updates.put("puntaje", 5);
            updates.put("votos", 1);
            updates.put("urlcv", "");
            updates.put("fechanac", new Timestamp(new Date()));
            updates.put("medallas", Arrays.asList());
            updates.put("categorias", Arrays.asList());
        }
        // alumno
        else if (tipoperfil== 3){
            GeoPoint puntomapa = new GeoPoint(-15.51515, -16.1212);
            updates.put("direccion", "");
            updates.put("mapa", puntomapa);
        }

        updates.put("tipo", tipoperfil);

        miperfil.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (tipoperfil == 2){
                            isTipoUsuario("profesores");
                        }
                        else if (tipoperfil == 3) {
                            isTipoUsuario("alumnos");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w("EEEEEEUPDATE", "Error updating document", e);
                        Toast.makeText(MainActivity.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                    }
                });
/*        Map<String, Object> newuser = new HashMap<>();
        newuser.put("nombres", user.getDisplayName());
        newuser.put("usuario", user.getEmail());
        newuser.put("urlfoto", "https://firebasestorage.googleapis.com/v0/b/profesoresdomicilio-f9c7f.appspot.com/o/iconos%2Ffoto-user.png?alt=media&token=e80b91d9-da7f-4582-ba9d-921d814010e0");

        db.collection(tipoperfil)
                .document(user.getUid())
                .set(newuser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void thisVoid) {
                        isTipoUsuario(tipoperfil);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("NOLES", "Error adding document", e);
                    }
                });*/
    }

    private void isTipoUsuario(String tipouser){
        ((Globales) getApplicationContext()).setTipoUsuario(tipouser);
        Class activ = null;
        if (tipouser.equals("profesores"))
            activ = VProfInicio.class;
        else if (tipouser.equals("alumnos"))
            activ = Inicio.class;
        Intent intent = new Intent(getApplicationContext(), activ);
        startActivity(intent);
    }

}
