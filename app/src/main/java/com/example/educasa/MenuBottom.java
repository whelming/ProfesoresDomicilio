package com.example.educasa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.educasa.Perfil.PerfilFragments.PerfilEjm;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.educasa.Chat.Chat;
import com.example.educasa.Inicio.Inicio;
import com.example.educasa.MisClases.MisClases;
import com.example.educasa.Perfil.Perfil;
import com.example.educasa.Solicitudes.Solicitudes;

public class MenuBottom extends AppCompatActivity {

    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuxml);
    }

    public void ListenerMiMenu(final Context contexxto, int numeroactivity) {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();

        MenuItem menuItem = menu.getItem(numeroactivity);
        menuItem.setChecked(true);



        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_inicio:
                        Intent intent0 = new Intent(contexxto, Inicio.class);
                        intent0.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent0);
                        return true;
                    case R.id.navigation_misclases:
                        Intent intent1 = new Intent(contexxto, MisClases.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_chat:
                        Intent intent2 = new Intent(contexxto, Chat.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_solicitudes:
                        Intent intent3 = new Intent(contexxto, Solicitudes.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        return true;
                    case R.id.navigation_perfil:
                        Intent intent4 = new Intent(contexxto,Perfil.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        };
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
