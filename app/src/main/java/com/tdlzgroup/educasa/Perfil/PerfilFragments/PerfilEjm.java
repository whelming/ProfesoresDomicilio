package com.tdlzgroup.educasa.Perfil.PerfilFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Bienvenida.Bienvenida;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.MainActivity;
import com.tdlzgroup.educasa.Perfil.Perfil;
import com.tdlzgroup.educasa.R;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilEjm extends Fragment {

    private Button cerrarsesion;
    private FloatingActionButton editar;
    private TextView perfil_nombres;
    private TextView perfil_telefono;
    private TextView perfil_correo;
    private TextView perfil_dni;
    private TextView perfil_direccion;

    private CircleImageView circleimage;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private String IDUser;
    private String tipoUsuario;
    private View v;
    private DocumentReference miperfil;

    private void setContentView(int activity_perfil) {   }

    public PerfilEjm() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_perfil_ejm, container, false);
        editar = v.findViewById(R.id.btn_editar_perfil);
        cerrarsesion= v.findViewById(R.id.btn_cerrar_sesion);
        perfil_nombres = v.findViewById(R.id.perfil_nombres);
        perfil_dni = v.findViewById(R.id.perfil_dni);
        perfil_telefono = v.findViewById(R.id.perfil_telefono);
        perfil_correo = v.findViewById(R.id.perfil_correo);
        perfil_direccion = v.findViewById(R.id.perfil_direccion);

        circleimage = v.findViewById(R.id.perfil_foto_usuario);

        user = FirebaseAuth.getInstance().getCurrentUser();
        IDUser = user.getUid();
        tipoUsuario = ((Globales) getActivity().getApplicationContext()).getTipoUsuario();


        if (user != null) {

            //CollectionReference alumnos = db.collection("alumnos");
            //DocumentReference materiaRef = document.getDocumentReference("materia");
            db = FirebaseFirestore.getInstance();

            if(tipoUsuario.equals("alumnos")){
                miperfil = db.collection("alumnos").document(IDUser);
            }
            else if(tipoUsuario.equals("profesores")){
                miperfil = db.collection("profesores").document(IDUser);
            }

            loadDataPerfil(miperfil);

            Map<String, Object> user = new HashMap<>();
            user.put("first", "Ada");
            user.put("last", "Lovelace");
            user.put("born", 1815);
/*            db.collection("alumnos")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("OKIDOKI", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("NOLES", "Error adding document", e);
                        }
                    });*/
        }

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //FirebaseAuth.getInstance().signOut();
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //getActivity().finish();
                                //getActivity().moveTaskToBack(true);
                                //System.exit(0);
                                Intent intent = new Intent(getContext(), Bienvenida.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("editarperfilalumno", "ewfef");
                    PerfilEditar editarperfilalumno = new PerfilEditar();
                    editarperfilalumno.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_perfil_principal, editarperfilalumno, "fragmentPerfilEditar")
                            .addToBackStack(null).commit();
                }

            }
        });

        return v;
    }

    private void loadDataPerfil( DocumentReference miperfil){
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
                        Glide.with(getContext()).load(document.getString("urlfoto")).centerCrop().placeholder(R.drawable.user).into(circleimage);
                        ((Perfil)getActivity()).hideLoader();
                    } else {
                        Toast.makeText(getContext(), "Algo salió mal", Toast.LENGTH_SHORT).show();
                        ((Perfil)getActivity()).hideLoader();
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void getAllChat() {
        db.collection("chats/wlDLGBNJD2N6ZUjcEK69/mensajes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("CHAAAAAA", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
