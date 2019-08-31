package com.tdlzgroup.educasa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Perfil.Perfil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends MenuBottom {
    Button btn_tipoprofesor;
    Button btn_tipoalumno;
    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        // INICIO ACTIVIDAD MAINACTIVITY


        btn_tipoalumno = findViewById(R.id.main_button_alumno);
        btn_tipoprofesor = findViewById(R.id.main_button_profesor);

        btn_tipoalumno.setOnClickListener((View v) -> seleccionPerfil("alumnos"));
        btn_tipoprofesor.setOnClickListener((View v) -> seleccionPerfil("profesores"));

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {

        }

        // FIN ACTIVIDAD MAINACTIVITY

    }

    private void seleccionPerfil(String tipoperfil){
//        if (tipoperfil.equals("alumnos")){
//        }

        Map<String, Object> newuser = new HashMap<>();
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
                });
    }

    private void isTipoUsuario(String tipouser){
        //((Globales) getApplicationContext()).settipoUsuario(tipouser);
        Intent intent = new Intent(getApplicationContext(), Inicio.class);
        intent.putExtra("TIPOUSUARIO", tipouser);
        startActivity(intent);
    }

}
