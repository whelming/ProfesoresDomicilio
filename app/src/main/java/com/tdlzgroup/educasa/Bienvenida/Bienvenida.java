package com.tdlzgroup.educasa.Bienvenida;

import android.app.AlertDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.MainActivity;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.VProfInicio.VProfInicio;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class Bienvenida extends AppCompatActivity {
    private Button btn_iniciar_sesion;
    private Button btn_crear_cuenta;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private static final int MY_REQUEST_CODE = 7100;
    private FirebaseAuth mAuth;
    List<AuthUI.IdpConfig> providers;
    public AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // INICIO ACTIVIDAD BIENVENIDA

        if (getIntent().getBooleanExtra("EXIT", false)) {
            //finish();
            //finishAffinity();
            //Toast.makeText(this, "exit toast", Toast.LENGTH_SHORT).show();
        }

        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        btn_iniciar_sesion = findViewById(R.id.btn_iniciar_sesion);
        //btn_crear_cuenta = findViewById(R.id.btn_crear_cuenta);

        user = FirebaseAuth.getInstance().getCurrentUser();


        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Cargando Perfil...")
                .setCancelable(false)
                .build();

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (user != null) {
                    dialog.show();
                    getTipoUsuario(user);
                }
                else {
                    showSignInOptions();
                }
            }
        });
        // FIN ACTIVIDAD BIENVENIDA
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = FirebaseFirestore.getInstance();
    }

    private void CerrarSesion() {
        AuthUI.getInstance()
                .signOut(Bienvenida.this)
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
    private void isTipoUsuario(String tipouser){
        dialog.hide();
        ((Globales) getApplicationContext()).setTipoUsuario(tipouser);
        Class activ = null;
        if (tipouser.equals("profesores"))
            activ = VProfInicio.class;
        else if (tipouser.equals("alumnos"))
            activ = Inicio.class;
        Intent intent = new Intent(getApplicationContext(), activ);
        startActivity(intent);
    }

    private void getTipoUsuario(FirebaseUser user){
//        String emailuser = user.getEmail();
//        CollectionReference usuarios = db.collection("usuarios");
//        Query queryusuarios = usuarios.whereEqualTo("email", emailuser);
        String userID = user.getUid();
        DocumentReference docRef = db.collection("usuarios").document(userID);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    int tipouser = document.getLong("tipo").intValue();
                    String urlFoto = document.getString("urlfoto");

                    ((Globales) getApplicationContext()).setUrlFotoUser(urlFoto);

                    if(tipouser == 2){
                        isTipoUsuario("profesores");
                    }
                    else if(tipouser == 3){
                        isTipoUsuario("alumnos");
                    }
                    else if (tipouser == 1){
                        dialog.hide();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

                else {
                    registrarUser();
                }
            } else {
                Log.d("Error", "Ocurrió un error.", task.getException());
            }
        });

        /*queryusuarios.get().addOnCompleteListener((new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().isEmpty()){
                        registrarUser();
                    }
                    else {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            double number = document.getDouble("tipo");
                            int tiponum = (int) number;
                            if (tiponum == 1){
                                // CREAR TIPO PERFIL
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                            else if (tiponum == 2){
                                isTipoUsuario("profesores");
                            }
                            else if (tiponum == 3){
                                isTipoUsuario("alumnos");
                            }
                            //Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    }
                }
            }


        }));*/
                        /*queryprofesor.get().addOnCompleteListener((new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> taask) {
                                if (taask.isSuccessful()) {
                                    if(!taask.getResult().isEmpty()){
                                        isTipoUsuario("profesores");
                                    }
                                    else {

                                    }
                                }}
                        }));*/
    }

    private void registrarUser() {
        Map<String, Object> newuser = new HashMap<>();
        newuser.put("tipo", 1);
        newuser.put("nombre", user.getDisplayName());
        newuser.put("email", user.getEmail());
        newuser.put("dni", "");
        newuser.put("celular", "");
        newuser.put("creacion", new Timestamp(new Date()));
        newuser.put("urlfoto", "foto_user.png");
        newuser.put("departamento", "");
        newuser.put("provincia", "");
        newuser.put("distrito", "");

        db.collection("usuarios")
                    .document(user.getUid())
                    .set(newuser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void thisVoid) {
                            dialog.hide();
                            ((Globales) getApplicationContext()).setUrlFotoUser("foto_user.png");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("NOLES", "Error adding document", e);
                        }
                    });
    }

    private void showSignInOptions(){
        //dialog.show();
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
        if(requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK) {
//          IdpResponse response = IdpResponse.fromResultIntent(data);
            dialog.show();
            user = FirebaseAuth.getInstance().getCurrentUser();
            getTipoUsuario(user);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }
        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bienvenida_ejm, container, false);
            TextView textView  = rootView.findViewById(R.id.texto_detalle);

            if (getArguments().getInt(ARG_SECTION_NUMBER)==1){
                textView.setText(R.string.bienvenida_text1);
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER)==2){
                textView.setText(R.string.bienvenida_text2);
            } else{

            }
            return rootView;
        }
    }
}
