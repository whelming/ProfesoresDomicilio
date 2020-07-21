package com.tdlzgroup.educasa.Inicio.InicioFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Inicio.Class.DetalleProfesoresDialog;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioAdapters.AdaptadorInicio;
import com.tdlzgroup.educasa.Inicio.InicioAdapters.AdaptadorProfesores;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentInicio;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentListaProfesores;
import com.tdlzgroup.educasa.R;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class InicioProfesores extends Fragment {
    public static int TAG = 210;
    private ImageView icontoolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentListaProfesores> milista;
    private Button btnSolicitarCurso;

    private FirebaseFirestore db;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    RecyclerView.LayoutManager mlayoutManager;
    private Bundle bundle;
    private String IDMateria;
    private String nombreMateria;
    private String urlImgMateria;
    private String tipoUsuario;
    private Context context;


    public InicioProfesores() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio_profesores, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        icontoolbar = v.findViewById(R.id.inicio_profesores_icono_toolbar);
        btnSolicitarCurso = v.findViewById(R.id.btn_solicitar_curso);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        bundle = this.getArguments();
        if (bundle != null) {
            tipoUsuario = bundle.getString("tipoUsuario");
            ContentInicio objetoMateria = (ContentInicio) bundle.getSerializable("ObjetoMateria");
            IDMateria = objetoMateria.getId();
            urlImgMateria = objetoMateria.getUrl_imagen_materia();
            nombreMateria = objetoMateria.getNombre_materia();

            GlideApp.with(context).load(storageReference.child("iconos/"+urlImgMateria)).centerCrop().placeholder(R.drawable.placeholder_materia).into(icontoolbar);

        }

        Toolbar toolbar = v.findViewById(R.id.inicio_toolbar_profesores);
        toolbar.setTitle("Profesores de "+ nombreMateria);

        if(getActivity() != null) {
            ((Inicio) context).setSupportActionBar(toolbar);
            ((Inicio) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Inicio) context).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icons_back_24dp);
            ((Inicio) context).getSupportActionBar().show();
        }

        db = FirebaseFirestore.getInstance();
        milista = new ArrayList<>();

        recyclerView = v.findViewById(R.id.inicio_profesores_recycler);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(getActivity());
        //recyclerView.setLayoutManager(layoutManager);
        mlayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mlayoutManager);
        milista = new ArrayList<>();
        LlenaMILista();

        btnSolicitarCurso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (getActivity() != null){
                    InicioSolicitarCurso fragmentSolicitarCurso = new InicioSolicitarCurso();
                    fragmentSolicitarCurso.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_inicio_principal, fragmentSolicitarCurso, "fragmentSolicitarCurso")
                            .addToBackStack(null).commit();
                }
            }
        });
    }

    private void LlenaMILista() {
        if (milista.size() > 0){
            milista.clear();
        }

        ((Inicio)context).showLoader();

        db.collection("usuarios")
                .whereArrayContains("materias", nombreMateria)
                .orderBy("puntaje", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    ContentListaProfesores unprofesor = new ContentListaProfesores(document.getId());
                                    unprofesor.setUrlfoto(document.getString("urlfoto"));
                                    unprofesor.setNombre(document.getString("nombre"));
                                    unprofesor.setDescripcion(document.getString("descripcion"));
                                    unprofesor.setProfesion(document.getString("profesion"));
                                    unprofesor.setPuntaje(document.getDouble("puntaje"));
                                    unprofesor.setVotos(document.getDouble("votos"));
                                    unprofesor.setCreacion(document.getTimestamp("creacion").toDate());
                                    unprofesor.setSexo(document.getDouble("sexo"));
                                    unprofesor.setMaterias((List<String>) document.get("materias"));
                                    unprofesor.setMedallas((List<String>) document.get("medallas"));
                                    unprofesor.setCategorias((List<String>) document.get("categorias"));
                                milista.add(unprofesor);
                            }
                            recyclerView.setAdapter(new AdaptadorProfesores(context, milista, new AdaptadorProfesores.OnItemClickListener() {
                                @Override public void onItemClick(ContentListaProfesores item) {
                                    //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                                    DetalleProfesoresDialog dialog = new DetalleProfesoresDialog();
                                    Bundle args = bundle;
                                    args.putString("idProfesor", item.getId());
                                    args.putSerializable("ObjetoProfesor", item);
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    dialog.setArguments(args);
                                    dialog.show(ft, DetalleProfesoresDialog.TAG);
                                }
                            }));
                            Log.d("DATOS CARGADOS",  "DATOS CARGADOS OK ");
                            ((Inicio)context).hideLoader();
                        }
                        else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
