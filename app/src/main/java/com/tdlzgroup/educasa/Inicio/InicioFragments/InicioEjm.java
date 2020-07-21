package com.tdlzgroup.educasa.Inicio.InicioFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Inicio.Class.AppBarStateChangeListener;
import com.tdlzgroup.educasa.Inicio.Class.ResizeAnimation;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioAdapters.AdaptadorInicio;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentInicio;
import com.tdlzgroup.educasa.Notificaciones;
import com.tdlzgroup.educasa.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class InicioEjm extends Fragment {
    private RecyclerView recyclerView;
    //private RecyclerView.LayoutManager layoutManager;
    private List<ContentInicio> milista;
    private FirebaseFirestore db;
    private SearchView searchbox;
    private ImageView portada;
    private RecyclerView.LayoutManager mlayoutManager;
    private AdaptadorInicio miadaptador;
    private String tipoUsuario;
    private Context context;
    private AppBarLayout appBarLayout;
    private int screenWidth;
    private int widthSearchBox;

    public InicioEjm() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio_ejm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        tipoUsuario = ((Globales) getActivity().getApplicationContext()).getTipoUsuario();

        //((Globales) getActivity().getApplicationContext()).getUrlFotoUser();

        db = FirebaseFirestore.getInstance();
        milista = new ArrayList<>();
        searchbox = v.findViewById(R.id.inicio_toolbar);
        appBarLayout = v.findViewById(R.id.inicio_appBarLayout);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int height = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        // get text from serachbox
/*        int id = searchbox.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textViewSearch = (TextView) searchbox.findViewById(id);*/

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) searchbox.getLayoutParams();
                //int newwidth = tamanosearch;

                widthSearchBox = screenWidth-200;

                if(state.name().equals("EXPANDED")){
                    widthSearchBox = screenWidth-200;
                    searchbox.setBackground(ContextCompat.getDrawable(context, R.drawable.background_white_rounded));
                }
                else if(state.name().equals("COLLAPSED")){
                    //searchbox.setLayoutParams(new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    //params.width = width;
                    //searchbox.setLayoutParams(params);
                    widthSearchBox = screenWidth;
                    searchbox.setBackground(ContextCompat.getDrawable(context, R.drawable.background_white_alpha_rounded));
//                    searchbox.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
//                    textViewSearch.setTextColor(Color.WHITE);
                }

                ResizeAnimation resizeAnimation = new ResizeAnimation(searchbox, widthSearchBox);
                resizeAnimation.setDuration(222);
                searchbox.startAnimation(resizeAnimation);

            }
        });
        //portada = v.findViewById(R.id.inicio_imagen_portada);

        recyclerView = v.findViewById(R.id.inicio_recycler);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(getContext());
        //recyclerView.setLayoutManager(layoutManager);
        mlayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mlayoutManager);
        LlenaMILista();

        searchbox.setQueryHint("Buscar una Materia");
        searchbox.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
                appBarLayout.setExpanded(false,true);
            }
        });
        searchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "222", Toast.LENGTH_SHORT).show();
                //searchbox.setFocusable(true);
                //searchbox.requestFocusFromTouch();
                //searchbox.setQueryHint("Buscar una Materia");

                appBarLayout.setExpanded(false,true);

                searchbox.onActionViewExpanded();

                //portada.setVisibility(View.GONE);
                //searchbox.setY(400);
                //searchbox.setIconified(false);
            }
        });
        searchbox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(context, "enter", Toast.LENGTH_SHORT).show();
                return true;
            }
            // search recyclerview filter
            @Override
            public boolean onQueryTextChange(String s) {
                String newText = s.toLowerCase();
                ArrayList<ContentInicio> newList = new ArrayList<>();
                for (ContentInicio unamateria : milista){
                    String namemateria = unamateria.getNombre_materia().toLowerCase();
                    if (Globales.cleanTextSpecial(namemateria).contains(Globales.cleanTextSpecial(newText))){
                        newList.add(unamateria);
                    }
                }
                miadaptador.setFilter(newList);
                return true;
            }
        });
    }

    private void LlenaMILista() {

        if (milista.size() > 0){
            milista.clear();
        }
        ((Inicio)getActivity()).showLoader();
        db.collection("materias")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ContentInicio materia = new ContentInicio(
                                        document.getId(),
                                        document.getString("nombre"),
                                        document.getString("urlimagen")
                                );
                                milista.add(materia);
                            }
                            miadaptador = new AdaptadorInicio(context, milista, new AdaptadorInicio.OnItemClickListener() {
                                @Override public void onItemClick(ContentInicio item) {
                                    if (getActivity() != null){
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("ObjetoMateria", item);
                                        bundle.putString("tipoUsuario", tipoUsuario);
                                        InicioProfesores fragmentListaProfesores = new InicioProfesores();
                                        fragmentListaProfesores.setArguments(bundle);
                                        ((Inicio) getActivity()).getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.frag_inicio_principal, fragmentListaProfesores, "fragmentListaProfesores")
                                                .addToBackStack(null).commit();
                                    }
                                }
                            });

                            //miadaptador.notifyDataSetChanged();
                            //recyclerView.invalidate();
                            recyclerView.setAdapter(miadaptador);
                            //recyclerView.requestLayout();
                            //update vista recycler
/*                            recyclerView.post(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView.smoothScrollToPosition(1);
                                }
                            });*/
                            ((Inicio)getActivity()).hideLoader();
                        }
                        else {
                            ((Inicio)getActivity()).hideLoader();

                            //Log.w("EEEEEE", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

/*  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.inicio_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.inicio_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "submit inicio", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }*/
}
