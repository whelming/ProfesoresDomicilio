package com.example.educasa;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends MenuBottom {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListenerMiMenu(this, 0);

        // INICIO ACTIVIDAD MAINACTIVITY

        // FIN ACTIVIDAD MAINACTIVITY

    }

    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }
}
