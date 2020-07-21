package com.tdlzgroup.educasa.Inicio.InicioFragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;


import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.Inicio.InicioModels.ContentInicio;
import com.tdlzgroup.educasa.R;
import com.tdlzgroup.educasa.Solicitudes.Solicitudes;
import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class InicioSolicitarCurso extends Fragment implements View.OnClickListener {
    private static final int COD_MAPA = 1992;
    private ImageView icontoolbar;
    private Button btn_confirmarclase;
    private Button btn_cancelar;
    private Spinner materia;
    private CardView bfecha, bhorafinal, bhorainicio, btn_mapa;
    private TextView fecha, horaafinal, horaainicio;
    private int dia, mes, ano, hora, minutos;
    private Button bsubirimg1, bsubirimg2, bsubirimg3;

    private SeekBar simpleSeekBar;

    private static final int CAMERA_REQUEST1 = 1887;
    private static final int CAMERA_REQUEST2 = 1888;
    private static final int CAMERA_REQUEST3 = 1889;
    private ImageView imagen1;
    private ImageView imagen2;
    private ImageView imagen3;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private Bundle bundle;
    private String IDMateria;
    private String nombreMateria;
    private String urlImgMateria;
    private String tipoUsuario;

    private TextView tv_titulo;
    private TextView tv_descripcion;
    private TextView ed_direccion;
    private TextView tv_coordenadas;
    private TextView tv_costo_hora;
    private List<TextView> tv_array;

    private String txt_titulo;
    private String txt_descripcion;
    private String txt_precio;
    private String txt_direccion;
    private int preciofinal;
    private String[] urlsfotos;

    public FirebaseStorage firebaseStorage;
    public StorageReference storageReference;
    public FirebaseFirestore db;
    public FirebaseUser user;
    public String IDUser;
    public Tooltip tooltip;
    private ScrollView scrollView;

    private double latitude;
    private double longitude;

    private Calendar calendarFechaSoliciInicio;
    private Calendar calendarFechaSoliciFinal;

    private Context context;

    public InicioSolicitarCurso() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio_solicitar_curso, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        icontoolbar = v.findViewById(R.id.inicio_solicitud_icono_toolbar);
        btn_confirmarclase = v.findViewById(R.id.btn_confirmar_solicitar_curso);
        simpleSeekBar = v.findViewById(R.id.inicio_solicitar_curso_seekbar);
        urlsfotos = new String[3];
        //btn_cancelar = v.findViewById(R.id.btn_cancelar_curso);

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser() ;
        IDUser = user.getUid();

        //materia = v.findViewById(R.id.inicio_solicitud_curso_spinner);
        scrollView = v.findViewById(R.id.inicio_solicitud_scrollViewForm);
        horaainicio = v.findViewById(R.id.inicio_solicitar_curso_hora_inicio);
        horaafinal = v.findViewById(R.id.inicio_solicitar_curso_hora_final);
        fecha = v.findViewById(R.id.inicio_solicitar_curso_fecha);
        bfecha = v.findViewById(R.id.inicio_solicitar_curso_fecha_boton);
        bhorainicio = v.findViewById(R.id.inicio_solicitar_curso_hora_inicio_boton);
        bhorafinal = v.findViewById(R.id.inicio_solicitar_curso_hora_final_boton);
        btn_mapa = v.findViewById(R.id.inicio_solicitar_curso_btn_mapa);

        tv_titulo = v.findViewById(R.id.inicio_solicitar_curso_titulo);
        tv_descripcion = v.findViewById(R.id.inicio_solicitar_curso_descripcion);
        //tv_precio = v.findViewById(R.id.inicio_solicitar_curso_precio);
        ed_direccion = v.findViewById(R.id.inicio_solicitar_curso_direccion);
        tv_coordenadas = v.findViewById(R.id.inicio_solicitar_curso_coordenadas);
        tv_costo_hora = v.findViewById(R.id.inicio_solicitar_curso_costo_hora);
        tv_array = new ArrayList<>();

        latitude = 0;
        longitude = 0;

        calendarFechaSoliciInicio = Calendar.getInstance();
        calendarFechaSoliciFinal = Calendar.getInstance();

        bfecha.setOnClickListener(this);
        bhorainicio.setOnClickListener(this);
        bhorafinal.setOnClickListener(this);

        bsubirimg1 = v.findViewById(R.id.inicio_solicitud_btn_foto1);
        bsubirimg2 = v.findViewById(R.id.inicio_solicitud_btn_foto2);
        bsubirimg3 = v.findViewById(R.id.inicio_solicitud_btn_foto3);
        imagen1 = v.findViewById(R.id.inicio_solicitud_foto1);
        imagen2 = v.findViewById(R.id.inicio_solicitud_foto2);
        imagen3 = v.findViewById(R.id.inicio_solicitud_foto3);

        bsubirimg1.setOnClickListener((View v1) -> TomarFoto(CAMERA_REQUEST1));
        bsubirimg2.setOnClickListener((View v2) -> TomarFoto(CAMERA_REQUEST2));
        bsubirimg3.setOnClickListener((View v3) -> TomarFoto(CAMERA_REQUEST3));

        simpleSeekBar.setMax(80);

        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int minimo = 10;
                i+=minimo;
                preciofinal = i;
                tv_costo_hora.setText("S/"+i + " x Hora");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {            }
        });

        tv_titulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        tv_titulo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                } else {
                    if (tooltip != null)
                        if(tooltip.isShowing())
                            tooltip.dismiss();
                }
            }
        });

        btn_mapa.setOnClickListener(mibtn -> {
            Intent activityfragmapa = new Intent(context, InicioMapaFragmen.class);
            activityfragmapa.putExtra("latitude", latitude);
            activityfragmapa.putExtra("longitude", longitude);
            startActivityForResult(activityfragmapa, COD_MAPA);
        });

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.Materia, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //materia.setPrompt("Seleccione una Materia");
        //materia.setAdapter(adapter);

        bundle = this.getArguments();
        if (bundle != null) {
            tipoUsuario = bundle.getString("tipoUsuario");
            ContentInicio objetoMateria = (ContentInicio) bundle.getSerializable("ObjetoMateria");
            IDMateria = objetoMateria.getId();
            urlImgMateria = objetoMateria.getUrl_imagen_materia();
            nombreMateria = objetoMateria.getNombre_materia();
            GlideApp.with(context).load(storageReference.child("iconos/"+urlImgMateria)).centerCrop().placeholder(R.drawable.placeholder_materia).into(icontoolbar);
/*            if (tipoUsuario.equals("alumnos")) {
            }
            else if (tipoUsuario.equals("profesores")) {
            }*/
        }

        Toolbar toolbar = v.findViewById(R.id.inicio_toolbar_solicitar_curso);
        toolbar.setTitle("Solicitud de Clases: "+ nombreMateria);

        if (getActivity() != null) {
            ((Inicio) getActivity()).setSupportActionBar(toolbar);
            ((Inicio) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Inicio) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icons_back_24dp);
            ((Inicio) getActivity()).getSupportActionBar().show();
        }

        tv_array.add(tv_titulo);
        tv_array.add(tv_descripcion);
        tv_array.add(fecha);
        tv_array.add(horaainicio);
        tv_array.add(horaafinal);
        tv_array.add(ed_direccion);
        tv_array.add(tv_coordenadas);

        btn_confirmarclase.setOnClickListener((View vista) -> {
            if (tooltip != null)
                if(tooltip.isShowing())
                    tooltip.dismiss();
            // validation form
            for (int i = 0; i < tv_array.size() ; i++) {
                if (tv_array.get(i).getText().toString().equals("")){
                    final int position = i;
                    tooltip = new Tooltip.Builder(tv_array.get(i))
                            .setText("Falta este campo !")
                            .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                            .setTextColor(Color.BLACK)
                            .setGravity(Gravity.BOTTOM)
                            .setCornerRadius(8f)
                            .show();
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.scrollTo(0, tv_array.get(position).getBottom());
                        }
                    });
                    //hide tooltip after 2 seconds
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tooltip.dismiss();
                        }
                    }, 2000);
                    //tv_titulo.requestFocus();
                    Globales.showSoftKeyboard(context,tv_array.get(i));
                    return;
/*                if(tv_titulo.requestFocus()) {
                    getActivity().getWindow().setSoftInputMode(getActivity().WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }*/
                }
            }
            //Toast.makeText(getContext(), "LISTO ENVIAR", Toast.LENGTH_SHORT).show();
            addSolicitudFire();

            //getActivity().onBackPressed();
        });

        /*btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });*/
    }

    public void addSolicitudFire(){

        ((Inicio)getActivity()).showLoader();

        GeoPoint puntomapa = new GeoPoint(latitude, longitude);
        String titulo = tv_titulo.getText().toString();
        String descripcion = tv_descripcion.getText().toString();
        String direccion = ed_direccion.getText().toString();
        Date fechaCurso = calendarFechaSoliciInicio.getTime();
        Date fechaFinalCurso = calendarFechaSoliciFinal.getTime();

        urlsfotos[0] = "tarea1.jpg";
        urlsfotos[1] = "tarea1.jpg";
        urlsfotos[2] = "tarea1.jpg";
        //List<String> listurlsfotos = Arrays.asList(urlsfotos);

        Map<String, Object> newsolicitud = new HashMap<>();
        newsolicitud.put("creacion", new Timestamp(new Date()));
        newsolicitud.put("descripcion", descripcion);
        newsolicitud.put("direccion", direccion);
        newsolicitud.put("estado", 1);
        newsolicitud.put("fechahora", new Timestamp(fechaCurso));
        newsolicitud.put("fechahorafin", new Timestamp(fechaFinalCurso));
        newsolicitud.put("idAlum", IDUser);
        newsolicitud.put("idProf", "");
        newsolicitud.put("interesados", 0);
        newsolicitud.put("mapa", puntomapa);
        newsolicitud.put("materia", nombreMateria);
        newsolicitud.put("nombreAlum", user.getDisplayName());
        newsolicitud.put("nombreProf", "");
        newsolicitud.put("precio", preciofinal);
        newsolicitud.put("tipo", 1);
        newsolicitud.put("titulo", titulo);
        newsolicitud.put("urlfotoProf", "");
        //newsolicitud.put("urlfotos", Arrays.asList("tarea1.jpg", "tarea1.jpg", "tarea1.jpg"));
        newsolicitud.put("urlfotos", Arrays.asList(urlsfotos));
        newsolicitud.put("urlimagen", urlImgMateria);

        db.collection("clases")
                .add(newsolicitud)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //getActivity().moveTaskToBack(true);
                        Toasty.success(context, "Solicitud exitosa...", Toast.LENGTH_SHORT, true).show();
                        Intent intentSolicitudes = new Intent(context, Solicitudes.class);
                        startActivity(intentSolicitudes);
                        getActivity().finish();
                        //documentReference.getId()
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.warning(context, "Ocurrió un error...", Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v) {
        if (tooltip != null)
            if(tooltip.isShowing())
                tooltip.dismiss();
        if (v == bfecha) {
            final Calendar c = Calendar.getInstance();

            ano = c.get(Calendar.YEAR);
            mes = c.get(Calendar.MONTH);
            dia = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog
                    (v.getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            fecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            calendarFechaSoliciInicio.set(Calendar.YEAR, year);
                            calendarFechaSoliciInicio.set(Calendar.MONTH, monthOfYear);
                            calendarFechaSoliciInicio.set(Calendar.DATE, dayOfMonth);
                            calendarFechaSoliciFinal.set(Calendar.YEAR, year);
                            calendarFechaSoliciFinal.set(Calendar.MONTH, monthOfYear);
                            calendarFechaSoliciFinal.set(Calendar.DATE, dayOfMonth);
                        }
                    },
                            ano, mes, dia);
            datePickerDialog.show();
        }
        if (v == bhorainicio) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog
                    (v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            horaainicio.setText(hourOfDay + ":" + minute);
                            calendarFechaSoliciInicio.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendarFechaSoliciInicio.set(Calendar.MINUTE, minute);
                        }
                    },
                            hora, minutos, false);
            timePickerDialog.show();

        }
        if (v == bhorafinal) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog
                    (v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            horaafinal.setText(hourOfDay + ":" + minute);
                            calendarFechaSoliciFinal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendarFechaSoliciFinal.set(Calendar.MINUTE, minute);
                        }
                    },
                            hora, minutos, false);
            timePickerDialog.show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAMERA_REQUEST1);
            } else {
                Toast.makeText(context, "camera permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void TomarFoto (int codigocamara){
        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, codigocamara);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST1) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagen1.setImageBitmap(photo);
            bsubirimg1.setVisibility(View.INVISIBLE);
        }

        else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST2) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagen2.setImageBitmap(photo);
            bsubirimg2.setVisibility(View.INVISIBLE);
        }

        else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST3) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imagen3.setImageBitmap(photo);
            bsubirimg3.setVisibility(View.INVISIBLE);
        }

        if (resultCode == RESULT_OK && requestCode == COD_MAPA) {
            latitude = data.getDoubleExtra("latitude",0);
            longitude = data.getDoubleExtra("longitude",0);
            tv_coordenadas.setText(latitude+"/"+ longitude);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null)
            Globales.hideSoftKeyboard(getActivity());

    }

}




