package com.tdlzgroup.educasa.VProfPerfil.VProfPerfilFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdlzgroup.educasa.Bienvenida.Bienvenida;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.VProfPerfil.VProfPerfil;

import de.hdodenhof.circleimageview.CircleImageView;

public class VProfPerfilEjm extends Fragment {

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
    private DocumentReference miperfil;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Context context;

    public VProfPerfilEjm() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vprof_perfil_ejm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        editar = v.findViewById(R.id.vprof_btn_editar_perfil);
        cerrarsesion= v.findViewById(R.id.vprof_btn_cerrar_sesion);
        perfil_nombres = v.findViewById(R.id.vprof_perfil_nombres);
        perfil_dni = v.findViewById(R.id.vprof_perfil_dni);
        perfil_telefono = v.findViewById(R.id.vprof_perfil_telefono);
        perfil_correo = v.findViewById(R.id.vprof_perfil_correo);
        perfil_direccion = v.findViewById(R.id.vprof_perfil_direccion);

        circleimage = v.findViewById(R.id.vprof_perfil_foto_usuario);

        user = FirebaseAuth.getInstance().getCurrentUser();
        IDUser = user.getUid();
        tipoUsuario = ((Globales) context.getApplicationContext()).getTipoUsuario();

        if (user != null) {

            db = FirebaseFirestore.getInstance();

            miperfil = db.collection("usuarios").document(IDUser);

            loadDataPerfil(miperfil);
        }

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AuthUI.getInstance()
                        .signOut(context)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(context, Bienvenida.class);
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

        editar.setOnClickListener(vedit -> {
            if (getActivity() != null){
                Bundle bundle = new Bundle();
                bundle.putString("editarperfilalumno", "ewfef");
                VProfPerfilEditar editarperfilalumno = new VProfPerfilEditar();
                editarperfilalumno.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.vprof_frag_perfil_principal, editarperfilalumno, "fragmentPerfilEditar")
                        .addToBackStack(null).commit();
            }
        });

    }

    private void loadDataPerfil(DocumentReference miperfil){
        ((VProfPerfil)getActivity()).showLoader();
        miperfil.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        perfil_nombres.setText(document.getString("nombre"));
                        perfil_dni.setText(document.getString("dni"));
                        perfil_telefono.setText(document.getString("celular"));
                        perfil_correo.setText(user.getEmail());
                        perfil_direccion.setText(document.getString("direccion"));
                        GlideApp.with(context).load(storageReference.child("perfiles/"+document.getString("urlfoto"))).centerCrop().placeholder(R.drawable.user).into(circleimage);
                    } else {
                        Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show();
                    }
                    ((VProfPerfil)getActivity()).hideLoader();

                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}
