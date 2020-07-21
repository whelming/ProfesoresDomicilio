package com.tdlzgroup.educasa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tdlzgroup.educasa.Chat.Chat;
import com.tdlzgroup.educasa.Inicio.Inicio;
import com.tdlzgroup.educasa.MisClases.MisClases;
import com.tdlzgroup.educasa.Perfil.Perfil;
import com.tdlzgroup.educasa.Solicitudes.Solicitudes;
import com.tdlzgroup.educasa.VProfInicio.VProfInicio;
import com.tdlzgroup.educasa.VProfMisClases.VProfMisClases;
import com.tdlzgroup.educasa.VProfPerfil.VProfPerfil;
import com.tdlzgroup.educasa.VProfSolicitudes.VProfSolicitudes;

import java.util.ArrayList;
import java.util.List;

public class MenuBottom extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavItemSelecListener;
    public TextView notificacionmenu;
    public BottomNavigationView navView;
    public Context globalcontex;
    public int numnotifi;
    public boolean isActiveServicio;
    public Intent notificaciones;
    public FirebaseFirestore db;
    public ListenerRegistration registration;
    public Query query;
    public String tipoUser;
    public Class[] activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuxml);
    }

    public void showMenuBottom(){
        navView.setVisibility(View.VISIBLE);
    }
    public void hideMenuBottom(){
        navView.setVisibility(View.GONE);
    }

    public void setNumberNotificacion(int num) {
        if(num < 1){
            notificacionmenu.setVisibility(View.INVISIBLE);
        }
        else {
            notificacionmenu.setText(""+num);
            notificacionmenu.setVisibility(View.VISIBLE);
        }
    }

    public void ListenerMiMenu(final Context contexxto, int numeroactivity) {
        globalcontex = contexxto;

        tipoUser = ((Globales) getApplicationContext()).getTipoUsuario();

        activities = new Class[5];

        if (tipoUser.equals("profesores")){
            activities[0] = VProfInicio.class;
            activities[1] = VProfMisClases.class;
            activities[2] = Chat.class;
            activities[3] = VProfSolicitudes.class;
            activities[4] = VProfPerfil.class;
        }
        else if (tipoUser.equals("alumnos")){
            activities[0] = Inicio.class;
            activities[1] = MisClases.class;
            activities[2] = Chat.class;
            activities[3] = Solicitudes.class;
            activities[4] = Perfil.class;
        }

        db = FirebaseFirestore.getInstance();
        //final DocumentReference docRef = db.collection("chats").document("Zow7It9jY6MNB8L1DXI4");
        query = db.collection("chats/wlDLGBNJD2N6ZUjcEK69/mensajes");

        navView = findViewById(R.id.nav_view);
        //BottomNavigationViewHelper.disableShiftMode(navView);
        Menu menu = navView.getMenu();

        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) navView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        View badge = LayoutInflater.from(contexxto).inflate(R.layout.notification_badge, itemView, true);
        notificacionmenu = findViewById(R.id.menu_notificacion_solicitudes);
        //int random = 1 + (int)(Math.random() * ((5 - 1) + 1));
        numnotifi = ((Globales) getApplicationContext()).getNumberNotifSolicitu();
        setNumberNotificacion(numnotifi);
        startListenerChat();

        /*View baddge = itemView.findViewById(R.id.menu_notificacion_solicitudes);
        ((ViewGroup)baddge.getParent()).removeView(badge);*/

        MenuItem menuItem = menu.getItem(numeroactivity);
        menuItem.setChecked(true);

        mOnNavItemSelecListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_inicio:
                        Intent intent0 = new Intent(contexxto, activities[0]);
                        startActivity(intent0);
                        return true;
                    case R.id.navigation_misclases:
                        Intent intent1 = new Intent(contexxto, activities[1]);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_chat:
                        Intent intent2 = new Intent(contexxto, activities[2]);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_solicitudes:
                        Intent intent3 = new Intent(contexxto, activities[3]);
                        startActivity(intent3);
                        return true;
                    case R.id.navigation_perfil:
                        Intent intent4 = new Intent(contexxto, activities[4]);
                        //intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        };
        navView.setOnNavigationItemSelectedListener(mOnNavItemSelecListener);
    }

    private void startListenerChat(){

        registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(globalcontex, "error", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<String> cities = new ArrayList<>();
                int i=0;
                for (QueryDocumentSnapshot doc : value) {
                    i++;
                    /*if (doc.getString("mensaje") != null) {
                        cities.add(doc.getString("mensaje"));
                    }*/
                }
                //((Globales) getApplication()).setNumberNotifSolicitu(i);
                //int numbernotifsolic = ((Globales) getApplicationContext()).getNumberNotifSolicitu();
                setNumberNotificacion(i);
                //((MenuBottom)getApplicationContext()).setNumberNotif(numbernotifsolic);
                //Log.d("COUNT MSJ", "-------------------------------------> " + i);
            }
        });
    }

    public void startService(){
/*        isActiveServicio = ((Globales) this.getApplicationContext()).isServicioNotificacion();
        if (!isActiveServicio){
            notificaciones = new Intent(this, Notificaciones.class);
            startService(notificaciones);
            ((Globales) this.getApplicationContext()).setServicioNotificacion(true);
        }*/

        /*isActiveServicio = ((Globales) getActivity().getApplicationContext()).isServicioNotificacion();
        if (!isActiveServicio){
            Intent notificaciones = new Intent(getActivity(), Notificaciones.class);
            getActivity().startService(notificaciones);
            ((Globales) getActivity().getApplicationContext()).setServicioNotificacion(true);
        }*/
    }

    public void stopService(){
//        stopService(notificaciones);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //registration.remove();
        //stopService();
        // FINALIZAR LISTENER
    }

}
