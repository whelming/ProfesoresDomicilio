package com.tdlzgroup.educasa.Inicio.Class;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdlzgroup.educasa.Chat.Chat;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioFragments.InicioSolicitarCurso;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentListaProfesores;
import com.tdlzgroup.educasa.R;
import com.tooltip.Tooltip;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class DetalleProfesoresDialog extends DialogFragment implements android.view.View.OnClickListener {
    public static final String TAG = "TAGCITO";
    public Activity c;
    public Dialog d;
    public Button cerrar, solicitarClases;
    public String nombre;
    public TextView nombre_profesor;
    public String idProfesor;
    public String txt_nombres_profesor;
    public String txt_descripcion;
    public String txt_profesion;
    public int txt_votos;
    public TextView descripcion;
    public TextView profesion;
    public TextView votos;
    public RatingBar estrellitas;
    public String urlFoto;
    public double puntaje;
    public List<String> materiasArray;
    public List<String> medallasArray;
    public List<String> categoriasArray;
    public CircleImageView foto;
    public LinearLayout linearlayoutMaterias;
    public LinearLayout linearlayoutCategorias;
    public LinearLayout linearlayoutMedallas;
    //public Tooltip tooltip;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    public FirebaseFirestore db;
    public FirebaseUser user;
    public String IDUser;
    public Bundle bundle;
    public Context context;

    public DetalleProfesoresDialog(){}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dialog_inicio_detalle_profesores,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser() ;
        IDUser = user.getUid();

        cerrar = v.findViewById(R.id.inicio_profesores_dialog_cerrar);
        solicitarClases = v.findViewById(R.id.inicio_profesores_dialog_solicitar_clase);
        nombre_profesor = v.findViewById(R.id.inicio_profesores_dialog_nombre);
        foto = v.findViewById(R.id.inicio_profesores_dialog_foto);
        descripcion = v.findViewById(R.id.inicio_profesores_dialog_descripcion);
        profesion = v.findViewById(R.id.inicio_profesores_dialog_profesion);
        votos = v.findViewById(R.id.inicio_profesores_dialog_votos);
        estrellitas = v.findViewById(R.id.inicio_profesores_dialog_rating);

        linearlayoutMaterias = v.findViewById(R.id.inicio_profesores_dialog_materias_container);
        linearlayoutCategorias = v.findViewById(R.id.inicio_profesores_dialog_categorias_container);
        linearlayoutMedallas = v.findViewById(R.id.inicio_profesores_dialog_medallass_container);

        bundle = this.getArguments();
        if (bundle != null) {
            idProfesor = bundle.getString("idProfesor");
            ContentListaProfesores objetoProfesor = (ContentListaProfesores) bundle.getSerializable("ObjetoProfesor");
            if(objetoProfesor != null) {
                txt_nombres_profesor = objetoProfesor.getNombre();
                txt_descripcion = objetoProfesor.getDescripcion();
                txt_profesion = objetoProfesor.getProfesion();
                txt_votos = (int) objetoProfesor.getVotos();
                materiasArray = objetoProfesor.getMaterias();
                categoriasArray = objetoProfesor.getCategorias();
                medallasArray = objetoProfesor.getMedallas();
                urlFoto = objetoProfesor.getUrlfoto();
                puntaje = objetoProfesor.getPuntaje();
            }

            //for (int i = 0; i < objetoProfesor.getMaterias().size() ; i++) {
                //view_array_fotos.get(i).setVisibility(View.VISIBLE);
                //GlideApp.with(getContext()).load(storageReference.child("adjuntos/"+objetoSolicitud.getUrlsfotos().get(i))).centerCrop().placeholder(R.drawable.placeholder_img).into(view_array_fotos.get(i));
//                Glide.with(getContext()).load(objetoSolicitud.getUrlsfotos().get(i)).centerCrop().placeholder(R.drawable.user).into(view_array_fotos.get(i));
            //}

//            LinearLayout linearLayout = new LinearLayout(getContext());
//            getActivity().setContentView(linearLayout);
//            linearLayout.setOrientation(LinearLayout.VERTICAL);


            for (int i = 0; i < materiasArray.size(); i++){
                View divider = new View(context);
                divider.setBackgroundColor(Color.rgb(222,222,222));
                LinearLayout.LayoutParams paramsDividerVert = new LinearLayout.LayoutParams(2,112);
                paramsDividerVert.setMargins(28,22,28,10);
                divider.setLayoutParams(paramsDividerVert);

/*                LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.views_iconos_materias, null);*/
                //View inflatedLayout = inflater.inflate(R.layout.views_iconos_materias, (ViewGroup) view, false);
                //linearlayoutMaterias.addView(view);

                View badge = LayoutInflater.from(context).inflate(R.layout.views_iconos_materias, null);
                ImageView iconoMat = badge.findViewById(R.id.views_icono_materia);
                TextView textoMat = badge.findViewById(R.id.views_texto_materia);

                //LinearLayout.LayoutParams tamanoMateria = new LinearLayout.LayoutParams(120,160);
                //badge.setLayoutParams(tamanoMateria);

                String urltexto = Globales.cleanTextSpecial(materiasArray.get(i).toLowerCase());
                GlideApp.with(context).load(storageReference.child("iconos/icono-"+urltexto+"167.png")).centerCrop().placeholder(R.drawable.placeholder_materia).into(iconoMat);
                textoMat.setText(materiasArray.get(i));

                linearlayoutMaterias.addView(badge);

                if (i != materiasArray.size()-1)
                    linearlayoutMaterias.addView(divider);

/*                ImageView imageView = new ImageView(getContext());
                imageView.setImageResource(R.drawable.icono_geografia);
                LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(132,152);
                paramsImg.setMargins(0,10,0,5);
                imageView.setLayoutParams(paramsImg);*/

//                TextView textView = new TextView(getContext());
//                textView.setText("› "+materiasArray.get(i));

            }

            for (int i = 0; i < categoriasArray.size(); i++){
                View dividerHoriz = new View(context);
                dividerHoriz.setBackgroundColor(Color.rgb(222,222,222));
                LinearLayout.LayoutParams paramsDividerHoriz = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2);
                paramsDividerHoriz.setMargins(0,20,0,20);
                dividerHoriz.setLayoutParams(paramsDividerHoriz);

                TextView textView = new TextView(context);
                LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                paramsImg.setMargins(10,0,10,0);
                textView.setLayoutParams(paramsImg);
                textView.setText("› "+categoriasArray.get(i));
                linearlayoutCategorias.addView(textView);

                if (i != categoriasArray.size()-1)
                    linearlayoutCategorias.addView(dividerHoriz);
            }

            for (int i = 0; i < medallasArray.size(); i++){
                final int cont = i;
                View divider = new View(context);
                divider.setBackgroundColor(Color.rgb(222,222,222));
                LinearLayout.LayoutParams paramsDividerVert = new LinearLayout.LayoutParams(2,100);
                paramsDividerVert.setMargins(32,22,32,20);
                divider.setLayoutParams(paramsDividerVert);

                ImageView imageView = new ImageView(context);

                if(medallasArray.get(cont).equals("DNI")){
                    imageView.setImageResource(R.drawable.ic_icons_medalla1);
                }
                else if(medallasArray.get(cont).equals("Foto")){
                    imageView.setImageResource(R.drawable.ic_icons_medalla2);
                }
                else if(medallasArray.get(cont).equals("CV")){
                    imageView.setImageResource(R.drawable.ic_icons_medalla3);
                }
                else if(medallasArray.get(cont).equals("Test")){
                    imageView.setImageResource(R.drawable.ic_icons_medalla4);
                }
                LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(124,124);
                paramsImg.setMargins(0,10,0,5);
                imageView.setLayoutParams(paramsImg);

                linearlayoutMedallas.addView(imageView);
                imageView.setOnClickListener(view -> {
                    Tooltip tooltip = new Tooltip.Builder(imageView)
                            .setText(medallasArray.get(cont)+" Verified")
                            .setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                            .setTextColor(Color.WHITE)
                            .setGravity(Gravity.BOTTOM)
                            .setCornerRadius(8f)
                            .show();

                    final Handler handler = new Handler();
                    //handler.postDelayed(() -> tooltip.dismiss(), 1000);
                    handler.postDelayed(tooltip::dismiss, 1000);

                });

                if (i != medallasArray.size()-1)
                    linearlayoutMedallas.addView(divider);
            }
        }

        nombre_profesor.setText(txt_nombres_profesor);
        estrellitas.setRating( (float) puntaje);
        descripcion.setText(txt_descripcion);
        profesion.setText(txt_profesion);
        votos.setText(getString(R.string.inicio_profesores_conteo_votos, txt_votos));
        GlideApp.with(context).load(storageReference.child("perfiles/"+urlFoto)).centerCrop().placeholder(R.drawable.user).into(foto);

/*        ObjectAnimator animation = ObjectAnimator.ofFloat(foto, "translationY", 60f);
        animation.setDuration(200);
        animation.start();*/

        cerrar.setOnClickListener(this);
        solicitarClases.setOnClickListener(this);
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
            case R.id.inicio_profesores_dialog_cerrar:
                dismiss();
                break;
            case R.id.inicio_profesores_dialog_solicitar_clase:
                dismiss();
                //addChatFire();
/*                if (getActivity() != null){
                    InicioSolicitarCurso fragmentSolicitarCurso = new InicioSolicitarCurso();
                    Bundle mibundle = bundle;
                    fragmentSolicitarCurso.setArguments(mibundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_inicio_principal, fragmentSolicitarCurso, "fragmentSolicitarCurso")
                            .addToBackStack(null).commit();
                }*/
                break;
            default:
                break;
        }
        dismiss();
    }

    public void addChatFire(){

        ((Inicio)getActivity()).showLoader();
        String urlPerfil = ((Globales) getActivity().getApplicationContext()).getUrlFotoUser();
        Map<String, Object> newChat = new HashMap<>();
        newChat.put("fechahora", new Timestamp(new Date()));
        newChat.put("idAlum", IDUser);
        newChat.put("idProf", idProfesor);
        newChat.put("nombreAlum", user.getDisplayName());
        newChat.put("nombreProf", txt_nombres_profesor);
        newChat.put("urlfotoAlum", urlPerfil);
        newChat.put("urlfotoProf", urlFoto);

        db.collection("chats")
                .add(newChat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //getActivity().moveTaskToBack(true);
                        //Toasty.success(getContext(), "Chat creado...", Toast.LENGTH_SHORT, true).show();
                        //Intent intentChat = new Intent(context, Chat.class);
                        //context.startActivity(intentChat);
                        ((Inicio)getActivity()).hideLoader();
                        dismiss();
                        //documentReference.getId()
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toasty.warning(getContext(), "Ocurrió un error...", Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

}