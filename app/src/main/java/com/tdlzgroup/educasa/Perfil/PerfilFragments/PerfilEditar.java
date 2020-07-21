package com.tdlzgroup.educasa.Perfil.PerfilFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tdlzgroup.educasa.GlideApp;
import com.tdlzgroup.educasa.Globales;
import com.tdlzgroup.educasa.Perfil.Perfil;
import com.tdlzgroup.educasa.R;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class PerfilEditar extends Fragment {
    private static final int RC_PHOTO_PICKER = 222;
    private Button btn_guardar_cambios;
    private Button cambiarfoto;
    private static final int CAMERA_REQUEST1 = 1886;
    private CircleImageView foto;
    private AutoCompleteTextView perfil_nombres;
    private AutoCompleteTextView perfil_dni;
    private AutoCompleteTextView perfil_telefono;
    private AutoCompleteTextView perfil_correo;
    private AutoCompleteTextView perfil_direccion;

    private FirebaseUser user;
    private FirebaseFirestore db;
    private String IDUser;
    private String tipoUsuario;
    private DocumentReference miperfil;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    public AlertDialog dialog;
    private Context context;

    public PerfilEditar() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil_editar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("perfiles");

        perfil_nombres = v.findViewById(R.id.perfil_nombres);
        perfil_dni = v.findViewById(R.id.perfil_dni);
        perfil_telefono = v.findViewById(R.id.perfil_telefono);
        perfil_correo = v.findViewById(R.id.perfil_correo);
        perfil_direccion = v.findViewById(R.id.perfil_direccion);

        perfil_correo.setFocusable(false);

        dialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Subiendo Foto...")
                .setCancelable(false)
                .build();

        user = FirebaseAuth.getInstance().getCurrentUser();
        IDUser = user.getUid();
        tipoUsuario = ((Globales) getActivity().getApplicationContext()).getTipoUsuario();

        cambiarfoto = v.findViewById(R.id.perfil_editar_btn_foto);
        btn_guardar_cambios = v.findViewById(R.id.btn_guardar_cambios);
        foto = v.findViewById(R.id.perfil_foto_usuario);
        Toolbar toolbar = v.findViewById(R.id.perfil_editar_toolbar);
        toolbar.setTitle("Editar Perfil");
        if (getActivity() != null) {
            ((Perfil) getActivity()).setSupportActionBar(toolbar);
            ((Perfil) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Perfil) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_icons_back_24dp);
            ((Perfil) getActivity()).getSupportActionBar().show();
        }

        if (user != null) {
            db = FirebaseFirestore.getInstance();
            miperfil = db.collection("usuarios").document(IDUser);
            if (tipoUsuario.equals("alumnos")) {
            } else if (tipoUsuario.equals("profesores")) {
                //miperfil = db.collection("profesores").document(IDUser);
            }

            loadDataPerfil(miperfil);

        }
        cambiarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"› Foto de la Galería", "› Foto de la Cámara"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Elige una opción:");
/*                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });*/
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*" );
                                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                                break;
                            case 1:
                                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                                } else {
                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    getActivity().startActivityForResult(cameraIntent, CAMERA_REQUEST1);
                                }
                                break;
                                default:
                                    return;
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        btn_guardar_cambios.setOnClickListener((View view) -> {
            String nombre = perfil_nombres.getText().toString();
            String dni = perfil_dni.getText().toString();
            String celular = perfil_telefono.getText().toString();
            String direccion = perfil_direccion.getText().toString();

            Map<String, Object> datosperfil = new HashMap<>();
            datosperfil.put("nombre", nombre);
            datosperfil.put("dni", dni);
            datosperfil.put("celular", celular);
            datosperfil.put("direccion", direccion);

            updateChanges(datosperfil);
        });
        //loadImgFirestoreUI();
    }

    private void loadImgFirestoreUI(){
        //StorageReference ref = FirebaseStorage.getInstance().getReference();
        final StorageReference fotoref = storageReference.child(IDUser);
        GlideApp.with(context).load(fotoref).centerCrop().placeholder(R.drawable.user).into(foto);
    }

    private void loadDataPerfil(DocumentReference miperfil) {
        ((Perfil)getActivity()).showLoader();
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
                        //Glide.with(getContext()).load(document.getString("urlfoto")).centerCrop().placeholder(R.drawable.user).into(foto);

                        try {
                            GlideApp.with(context).load(storageReference.child(document.getString("urlfoto"))).centerCrop().placeholder(R.drawable.user).into(foto);
                        }
                        catch (Exception e){}
                        try {
                            perfil_direccion.setText(document.getString("direccion"));
                        }
                        catch (Exception e){}
                        //Glide.with(getContext()).load(document.getString("urlfoto")).centerCrop().placeholder(R.drawable.user).into(circleimage);
                        ((Perfil)getActivity()).hideLoader();
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST1) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            foto.setImageBitmap(photo);
            dialog.show();
            subirFotoCamara(foto);
        }

        else if(resultCode == Activity.RESULT_OK && requestCode == RC_PHOTO_PICKER){
            dialog.show();
            Uri imagen = data.getData();
            subirFotoGaleria(imagen);
        }
    }

    private void subirFotoCamara(CircleImageView imageView) {
        Date now = new Date();
        final StorageReference fotoref = storageReference.child(now.getTime()+"");
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = fotoref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toasty.error(context, "Ocurrió un error", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getNameFileStorage(taskSnapshot);
            }
        });
    }

    private void subirFotoGaleria(Uri imagen){
        Date now = new Date();
        final StorageReference fotoref = storageReference.child(now.getTime()+"");
        fotoref.putFile(imagen).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toasty.error(context, "Ocurrió un error", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getNameFileStorage(taskSnapshot);
                    }
                });
    }

    private void getNameFileStorage(UploadTask.TaskSnapshot taskSnapshot){
        String namefile = taskSnapshot.getStorage().getName();
        updateUrlFotoPerfil(namefile);
        GlideApp.with(context).load(storageReference.child(namefile)).centerCrop().placeholder(R.drawable.user).into(foto);
    }

    private void getUrlTaskSnapshot(UploadTask.TaskSnapshot taskSnapshot) {
        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        updateUrlFotoPerfil(uri.toString());
                        Glide.with(context).load(uri.toString()).centerCrop().placeholder(R.drawable.user).into(foto);
                    }
                });
    }

    private void updateUrlFotoPerfil(String urlFoto){
        //((Perfil)getActivity()).showLoader();
        ((Globales) getActivity().getApplicationContext()).setUrlFotoUser(urlFoto);
        miperfil
                .update("urlfoto", urlFoto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.hide();
                        Toasty.success(context, "Foto Actualizada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toasty.error(context, "Ocurrió un error... Vuelve a intentarlo", Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private void updateChanges(Map<String, Object> data){
        //String nombres, String dni, String telefono, String direccion
        ((Perfil)getActivity()).showLoader();
        miperfil
                .update("nombre", data.get("nombre"),
            "dni", data.get("dni"),
                                "celular", data.get("celular"),
                                    "direccion", data.get("direccion"))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d("UPDATEOK", "DocumentSnapshot successfully updated!");

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(data.get("nombre").toString())
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            ((Perfil)getActivity()).hideLoader();
                                            Toasty.success(context, "Datos guardados correctamente", Toast.LENGTH_SHORT, true).show();
                                            getActivity().onBackPressed();
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w("EEEEEEUPDATE", "Error updating document", e);
                        Toasty.error(context, "Ocurrió un error... Vuelve a intentarlo", Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private void saveDemo(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("stringExample", "Hello world!");
        docData.put("booleanExample", true);
        docData.put("numberExample", 3.14159265);
        docData.put("dateExample", new Timestamp(new Date()));
        docData.put("listExample", Arrays.asList(1, 2, 3));
        docData.put("nullExample", null);

        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("a", 5);
        nestedData.put("b", true);

        docData.put("objectExample", nestedData);

        db.collection("chats").document("Zow7It9jY6MNB8L1DXI4")
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("OKOKOKOK", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error", "Error writing document", e);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() != null)
            Globales.hideSoftKeyboard(getActivity());
    }

}
