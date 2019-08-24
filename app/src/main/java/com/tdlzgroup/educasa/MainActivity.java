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
    private static final int MY_REQUEST_CODE = 7100;
    private FirebaseAuth mAuth;
    List<AuthUI.IdpConfig> providers;
    TextView textobienven;
    Button btn_tipoprofesor;
    Button btn_tipoalumno;
    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        //ListenerMiMenu(this, 0);

        // INICIO ACTIVIDAD MAINACTIVITY

        textobienven = findViewById(R.id.texto_bienvenida);

        btn_tipoalumno = findViewById(R.id.main_button_alumno);
        btn_tipoprofesor = findViewById(R.id.main_button_profesor);

        btn_tipoalumno.setVisibility(View.INVISIBLE);
        btn_tipoprofesor.setVisibility(View.INVISIBLE);

        btn_tipoalumno.setOnClickListener((View v) -> seleccionPerfil("alumnos"));
        btn_tipoprofesor.setOnClickListener((View v) -> seleccionPerfil("profesores"));

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        showSignInOptions();

        if (user != null) {
            Toast.makeText(this, "SESSION OK", Toast.LENGTH_SHORT).show();


        }
        else {
            Toast.makeText(this, "NO SESSION", Toast.LENGTH_SHORT).show();
        }

        // FIN ACTIVIDAD MAINACTIVITY

    }

    private void seleccionPerfil(String tipoperfil){

        if (tipoperfil.equals("alumnos")){

        }
        else{

        }

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

    private void CerrarSesion() {
        AuthUI.getInstance()
                .signOut(MainActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        showSignInOptions();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void getTipoUsuario(FirebaseUser user){


        if (user != null) {

            String emailuser = user.getEmail();

            CollectionReference alumnos = db.collection("alumnos");
            CollectionReference profesores = db.collection("profesores");

            Query queryalumno = alumnos.whereEqualTo("usuario", emailuser);
            Query queryprofesor = profesores.whereEqualTo("usuario", emailuser);

            queryalumno.get().addOnCompleteListener((new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if(!task.getResult().isEmpty()){
                            isTipoUsuario("alumnos");
                        }
                        else {
                            queryprofesor.get().addOnCompleteListener((new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> taask) {
                                if (taask.isSuccessful()) {
                                    if(!taask.getResult().isEmpty()){
                                        isTipoUsuario("profesores");
                                    }
                                    else {
                                        VisibleSeleccionTipoUsuario();
                                    }
                                }}
                            }));
                        }
                    }
                }


            }));

/*            queryprofesor.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if(task.getResult().size()>0){
                        Toast.makeText(this, "PROFESOR", Toast.LENGTH_SHORT).show();
                        tipo[0] = "profesor";
                    }
                } else {
                    Log.w("EEEEEE", "Error getting documents.", task.getException());
                }
            });*/

/*            db.collection("alumnos")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("OKOKOKOK", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    });*/
        }

    }
    private void isTipoUsuario(String tipouser){
        //((Globales) getApplicationContext()).settipoUsuario(tipouser);
        Intent intent = new Intent(getApplicationContext(), Inicio.class);
        intent.putExtra("TIPOUSUARIO", tipouser);
        startActivity(intent);

    }
    private void VisibleSeleccionTipoUsuario(){
        btn_tipoalumno.setVisibility(View.VISIBLE);
        btn_tipoprofesor.setVisibility(View.VISIBLE);
        textobienven.setVisibility(View.INVISIBLE);
    }

    private void showSignInOptions(){
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.MyThemeFireAuth)
                .setIsSmartLockEnabled(false)
                .build(), MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                textobienven.setText(user.getDisplayName());
                //Toast.makeText(this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();
                getTipoUsuario(user);
            }

            else {
                //Toast.makeText(this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // LISTENER MENU BOTTOM NAVIGATION
/*    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }*/
}
