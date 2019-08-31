package com.tdlzgroup.educasa.Solicitudes.SolicitudesFragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.MisClases.MisClasesModels.ContentMisClases;
import com.tdlzgroup.educasa.Solicitudes.Solicitudes;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesAdapters.AdaptadorProfesoresInteresados;
import com.tdlzgroup.educasa.Solicitudes.SolicitudesModels.ContentProfesoresInteresados;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesDetalle extends Fragment {
    private RecyclerView recyclerView;
    private String bundlerecibido;
    private Button btnEliminarSolicitud;
    RecyclerView.LayoutManager mlayoutManager;
    LinearLayoutManager layoutManager;

    List<ContentProfesoresInteresados> milista;
    public SolicitudesDetalle() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitudes_detalle, container, false);
        btnEliminarSolicitud = v.findViewById(R.id.btn_eliminar_solicitud);
        bundlerecibido = getArguments().getString("datodetallesolicitud");
        Toolbar toolbar = v.findViewById(R.id.solicitudes_toolbar_detalle);
        toolbar.setTitle("Detalle de "+ bundlerecibido);

        recyclerView = v.findViewById(R.id.solicitudes_profesores_interesados_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//      mlayoutManager = new GridLayoutManager(getActivity(), 2);
//      recyclerView.setLayoutManager(mlayoutManager);
        milista = new ArrayList<>();
        LlenaMILista();

        if (getActivity() != null) {
            ((Solicitudes) getActivity()).setSupportActionBar(toolbar);
            ((Solicitudes) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Solicitudes) getActivity()).getSupportActionBar().show();
        }
        btnEliminarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
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
        return v;
    }

    private void LlenaMILista() {
        milista.add(new ContentProfesoresInteresados("1111","Juan Pablo", "voy a tu casa","S/50", "https://ojodepezfotografos.com/wp-content/uploads/2019/03/SESION-DE-FOTOS-PERFIL-PROFESIONAL-13.jpg",1.5));
        milista.add(new ContentProfesoresInteresados("2222","Pedro Escamoso", "en mi casa","S/38", "https://www.mundodeportivo.com/r/GODO/MD/p5/MasQueDeporte/Imagenes/2018/10/24/Recortada/img_femartinez_20181010-125104_imagenes_md_otras_fuentes_captura-kcOG-U452531892714hYG-980x554@MundoDeportivo-Web.JPG",3.5));
        milista.add(new ContentProfesoresInteresados("3333","Luna Perla", "en el colegio","S/45", "https://cdn.computerhoy.com/sites/navi.axelspringer.es/public/styles/480/public/media/image/2018/08/fotos-perfil-whatsapp_16.jpg?itok=aqeTumbO",4.5));

        recyclerView.setAdapter(new AdaptadorProfesoresInteresados(getActivity(), milista, new AdaptadorProfesoresInteresados.OnItemClickListener() {
            @Override public void onItemClick(ContentProfesoresInteresados item) {
                //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAceptarClick(ContentProfesoresInteresados item) {
                Toast.makeText(getActivity(), "Aceptar "+item.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEliminarClick(ContentProfesoresInteresados item) {
                Toast.makeText(getActivity(), "ELIMINAR "+ item.getId(), Toast.LENGTH_SHORT).show();
            }

        }));
    }

    /*private void LlenaMILista() {
        if (milista.size() > 0){
            milista.clear();
        }

        //((Solicitudes)getActivity()).showLoader();


        db.collection("profesores")
                .whereArrayContains("materias", bundlerecibido)
                .orderBy("puntuacion", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentListaProfesores materia = new ContentListaProfesores(
                                        document.getString("ID"),
                                        document.getString("urlfoto"),
                                        document.getString("nombres"),
                                        document.getDouble("puntuacion")
                                );
                                milista.add(materia);

                                //Log.d("CHAAAAAA",  + " => " + document.getData());
                            }
                            recyclerView.setAdapter(new AdaptadorProfesores(getActivity(), milista, new AdaptadorProfesores.OnItemClickListener() {
                                @Override public void onItemClick(ContentListaProfesores item) {
                                    //Toast.makeText(getActivity(), item.getId()+"", Toast.LENGTH_SHORT).show();
                                    DetalleProfesoresDialog dialog = new DetalleProfesoresDialog();
                                    Bundle args = new Bundle();
                                    args.putString("idprofesor", item.getId());
                                    args.putString("nombresprofesor", item.getNombres());
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    dialog.setArguments(args);
                                    dialog.show(ft, DetalleProfesoresDialog.TAG);
                                }
                            }));
                            Log.d("DATOS CARGADOS",  "DATOS CARGADOS OK ");

                            ((Inicio)getActivity()).hideLoader();

                        }
                        else {
                            Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });
    }*/

}
