package com.example.educasa.Inicio.Class;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educasa.R;

public class DetalleProfesoresDialog extends DialogFragment implements
        android.view.View.OnClickListener {
    public static  String TAG = "adssad";
    public Activity c;
    public Dialog d;
    public Button cerrar, solicitarClases, descargarDoc;
    public String nombre;
    public TextView nombre_profesor;
    public String id_profesor;
    public  DetalleProfesoresDialog(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_profesor = getArguments().getString("idprofesor");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dialog_inicio_detalle_profesores,container,false);
        cerrar = (Button) v.findViewById(R.id.Cerrar);
        solicitarClases = (Button) v.findViewById(R.id.Solicitar_clases);
        descargarDoc = (Button) v.findViewById(R.id.Descarga_documento);
        nombre_profesor = v.findViewById(R.id.nombre_profesor);
        Toast.makeText(getContext(), id_profesor, Toast.LENGTH_SHORT).show();

        nombre_profesor.setText(id_profesor);
        cerrar.setOnClickListener(this);
        solicitarClases.setOnClickListener(this);
        descargarDoc.setOnClickListener(this);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Cerrar:
                dismiss();
                break;
            case R.id.Solicitar_clases:
                dismiss();
                break;
            case R.id.Descarga_documento:
                Toast.makeText(getActivity(), "Descargar documento", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        dismiss();
    }
}