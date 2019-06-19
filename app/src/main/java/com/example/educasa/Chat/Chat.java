package com.example.educasa.Chat;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.educasa.BottomNavigationViewHelper;
import com.example.educasa.Chat.ChatAdapters.AdaptadorChat;
import com.example.educasa.Chat.ChatFragments.ChatEjm;
import com.example.educasa.Chat.ChatModels.ContentChat;
import com.example.educasa.Inicio.Inicio;
import com.example.educasa.Inicio.InicioAdapters.AdaptadorMaterias;
import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Chat extends MenuBottom {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // INICIO ACTIVIDAD CHAT

        ChatEjm fragmentprincipal = new ChatEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_chat_principal, fragmentprincipal);
        transaction.commit();

        // FIN ACTIVIDAD CHAT
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "START CHAT", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListenerMiMenu(this, 2);
    }

    // LISTENER MENU BOTTOM NAVIGATION
    @Override
    public void ListenerMiMenu(Context cont, int numberactivity) {
        super.ListenerMiMenu(cont,numberactivity);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
