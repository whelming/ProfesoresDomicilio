package com.example.educasa.Inicio.Class;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.educasa.R;

public class DetalleProfesoresDialog extends DialogFragment implements
        android.view.View.OnClickListener {
    public static  String TAG = "adssad";
    public Activity c;
    public Dialog d;
    public Button cerrar, solicitarClases, descargarDoc;
    public String nombre;
    public TextView nombre_profesor;
    public  DetalleProfesoresDialog(){


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        nombre_profesor.setText(nombre);
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
                c.finish();
                break;
            case R.id.Solicitar_clases:
                dismiss();
                break;
            case R.id.Descarga_documento:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}