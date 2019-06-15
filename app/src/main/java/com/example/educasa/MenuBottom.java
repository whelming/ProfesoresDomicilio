package com.example.educasa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educasa.Chat.Chat;
import com.example.educasa.Inicio.Inicio;
import com.example.educasa.MisClases.MisClases;
import com.example.educasa.Perfil.Perfil;
import com.example.educasa.Solicitudes.Solicitudes;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
                        startActivity(intent0);
                        return true;
                    case R.id.navigation_misclases:
                        Intent intent1 = new Intent(contexxto, MisClases.class);
                        startActivity(intent1);
                        return true;
                    case R.id.navigation_chat:
                        Intent intent2 = new Intent(contexxto, Chat.class);
                        startActivity(intent2);
                        return true;
                    case R.id.navigation_solicitudes:
                        Intent intent3 = new Intent(contexxto, Solicitudes.class);
                        startActivity(intent3);
                        return true;
                    case R.id.navigation_perfil:
                        Intent intent4 = new Intent(contexxto, Perfil.class);
                        startActivity(intent4);
                        return true;
                }
                return false;
            }
        };
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}