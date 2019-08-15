package com.example.educasa.Perfil.PerfilFragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.educasa.Bienvenida.Bienvenida;
import com.example.educasa.Inicio.InicioFragments.InicioSolicitarCurso;
import com.example.educasa.Perfil.Perfil;
import com.example.educasa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilEjm extends Fragment {

    private Button  cerrarsesion;
    private Button editar;


    private void setContentView(int activity_perfil) {
    }


    public PerfilEjm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil_ejm, container, false);
        editar = v.findViewById(R.id.btn_editar_perfil);
        cerrarsesion= v.findViewById(R.id.btn_cerrar_sesion);

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Bienvenida.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

}
