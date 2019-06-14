package com.example.educasa.Chat;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.educasa.BottomNavigationViewHelper;
import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Chat extends MenuBottom {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ListenerMiMenu(this, 2);

        // INICIO ACTIVIDAD CHAT

        // FIN ACTIVIDAD CHAT
    }

    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }
}
