package com.example.educasa.Chat;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
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
import com.example.educasa.Inicio.Inicio;
import com.example.educasa.Inicio.InicioAdapters.AdaptadorMaterias;
import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Chat extends MenuBottom {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        ListenerMiMenu(this, 2);

        // INICIO ACTIVIDAD CHAT

        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        toolbar.setTitle("Chat");
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.chat_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"Juan Perez","Humberto Aguilar","Maria Sotelo","Edwin Cardenas","Daniela Avila"};
        mAdapter = new AdaptadorChat(this, myDataset);
        recyclerView.setAdapter(mAdapter);

        // FIN ACTIVIDAD CHAT
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.chat_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //fragment.FiltrarAlumnos(s);
                Toast.makeText(Chat.this, "submit chat", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                //fragment.FiltrarAlumnos(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
