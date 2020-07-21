package com.tdlzgroup.educasa.Solicitudes.SolicitudesFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.MisClases.MisClasesModels.ContentMisClases;
import com.tdlzgroup.educasa.Solicitudes.Solicitudes;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesAdapters.AdaptadorProfesoresInteresados;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesModels.ContentProfesoresInteresados;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesModels.ContentSolicitudes;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SolicitudesDetalle extends Fragment {
    private RecyclerView recyclerView;
    private String IDSolicitud;
    private String titulo;
    private Button btnEliminarSolicitud;
    RecyclerView.LayoutManager mlayoutManager;
    LinearLayoutManager layoutManager;

    private TextView solicitudes_descripcion;
    private TextView solicitudes_fechahora;
    private TextView solicitudes_precio;
    private ImageView solicitudes_foto1;
    private ImageView solicitudes_foto2;
    private ImageView solicitudes_foto3;

    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    List<ContentProfesoresInteresados> milista;
    private Context context;

    public SolicitudesDetalle() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_solicitudes_detalle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        btnEliminarSolicitud = v.findViewById(R.id.btn_eliminar_solicitud);

        solicitudes_descripcion = v.findViewById(R.id.solicitudes_descripcion);
        solicitudes_fechahora = v.findViewById(R.id.solicitudes_fechahora);
        solicitudes_precio = v.findViewById(R.id.solicitudes_precio);
        solicitudes_foto1 = v.findViewById(R.id.solicitudes_foto1);
        solicitudes_foto2 = v.findViewById(R.id.solicitudes_foto2);
        solicitudes_foto3 = v.findViewById(R.id.solicitudes_foto3);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        List<ImageView> view_array_fotos = new ArrayList<>();
        view_array_fotos.add(solicitudes_foto1);
        view_array_fotos.add(solicitudes_foto2);
        view_array_fotos.add(solicitudes_foto3);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            IDSolicitud = bundle.getString("idsolicitud");
            titulo = bundle.getString("titulosolicitud");
            ContentSolicitudes objetoSolicitud = (ContentSolicitudes) bundle.getSerializable("ObjetoSolicitud");
            solicitudes_descripcion.setText(objetoSolicitud.getDescripcion());
            solicitudes_fechahora.setText(objetoSolicitud.getFechahora()+"");
            solicitudes_precio.setText(objetoSolicitud.getPrecio()+"");
            for (int i = 0; i < objetoSolicitud.getUrlsfotos().size() ; i++) {
                view_array_fotos.get(i).setVisibility(View.VISIBLE);
                GlideApp.with(context).load(storageReference.child("adjuntos/"+objetoSolicitud.getUrlsfotos().get(i))).centerCrop().placeholder(R.drawable.placeholder_img).into(view_array_fotos.get(i));
//                Glide.with(getContext()).load(objetoSolicitud.getUrlsfotos().get(i)).centerCrop().placeholder(R.drawable.user).into(view_array_fotos.get(i));
            }
        }
        solicitudes_foto1.setOnClickListener((View foto1) -> {
            Toast.makeText(context, "foto 111", Toast.LENGTH_SHORT).show();
        });
        solicitudes_foto2.setOnClickListener((View foto2) -> {
            Toast.makeText(context, "foto 222", Toast.LENGTH_SHORT).show();
        });
        solicitudes_foto3.setOnClickListener((View foto3) -> {
            Toast.makeText(context, "foto 333", Toast.LENGTH_SHORT).show();
        });


        Toolbar toolbar = v.findViewById(R.id.solicitudes_toolbar_detalle);
        toolbar.setTitle("Detalle de "+ titulo);
        db = FirebaseFirestore.getInstance();

        //LlenaDatosSolicitud();

        recyclerView = v.findViewById(R.id.solicitudes_profesores_interesados_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        milista = new ArrayList<>();

        LlenaMIListaInteresados();

        if (getActivity() != null) {
            ((Solicitudes) getActivity()).setSupportActionBar(toolbar);
            ((Solicitudes) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Solicitudes) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icons_back_24dp);
            ((Solicitudes) getActivity()).getSupportActionBar().show();
        }

        btnEliminarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Seguro...")
                        .setMessage("que deseas eliminar esta solicitud.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "Eliminaste la Solicitud", Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void LlenaDatosSolicitud() {
        db.collection("alumnos").document(IDSolicitud).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        solicitudes_descripcion.setText(document.getString("nombres"));
                        solicitudes_fechahora.setText((document.getTimestamp("fechahora")).toDate()+"");
                        solicitudes_precio.setText(document.getString("direccion"));
                        List<String> listaarray = (List<String>) document.get("urlsfoto");
                        Toast.makeText(context, "GG: "+listaarray.toString(), Toast.LENGTH_SHORT).show();

                        //Glide.with(getContext()).load(listaarray[0]).centerCrop().placeholder(R.drawable.user).into(solicitudes_foto);
                    } else {
                        Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void LlenaMIListaInteresados() {
        if (milista.size() > 0){
            milista.clear();
        }

        ((Solicitudes)getActivity()).showLoader();

        db.collection("clases/"+IDSolicitud+"/interesados")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()){
                                ((Solicitudes)getActivity()).hideLoader();
                                Toasty.warning(context, "No hay interesados aún...", Toast.LENGTH_SHORT, true).show();
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentProfesoresInteresados profesoresinteresados = new ContentProfesoresInteresados();

                                profesoresinteresados.setId(document.getId());
                                profesoresinteresados.setDescripcion(document.getString("mensaje"));
                                profesoresinteresados.setPrecio(document.getDouble("precio"));
                                DocumentReference profesorRef = document.getDocumentReference("idProf");

                                profesorRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot docProfesor = task.getResult();
                                            if (docProfesor.exists()) {
                                                profesoresinteresados.setNombres(docProfesor.getString("nombre"));
                                                profesoresinteresados.setPuntuacion(docProfesor.getDouble("puntaje"));
                                                profesoresinteresados.setUrl_foto(docProfesor.getString("urlfoto"));
                                                milista.add(profesoresinteresados);
                                                recyclerView.setAdapter(new AdaptadorProfesoresInteresados(context, milista, new AdaptadorProfesoresInteresados.OnItemClickListener() {
                                                    @Override public void onItemClick(ContentProfesoresInteresados item) {
                                                        //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onAceptarClick(ContentProfesoresInteresados item) {
                                                        Toast.makeText(context, "Aceptar "+item.getId(), Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onEliminarClick(ContentProfesoresInteresados item) {
                                                        Toast.makeText(context, "Eliminar "+ item.getId(), Toast.LENGTH_SHORT).show();
                                                    }

                                                }));

                                                ((Solicitudes)getActivity()).hideLoader();

                                            } else {
                                                Toast.makeText(context, "Algo salió mal", Toast.LENGTH_SHORT).show();

                                                //Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            //Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });

                            }


                        }
                        else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
