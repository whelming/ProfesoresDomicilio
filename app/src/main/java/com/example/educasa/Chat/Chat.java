package com.example.educasa.Chat;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.educasa.BottomNavigationViewHelper;
import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Chat extends MenuBottom {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        ListenerMiMenu(this, 2);

        // INICIO ACTIVIDAD CHAT

        // FIN ACTIVIDAD CHAT
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListenerMiMenu(this, 2);
        Toast.makeText(this, "START CHAT", Toast.LENGTH_SHORT).show();
    }
    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }
}
