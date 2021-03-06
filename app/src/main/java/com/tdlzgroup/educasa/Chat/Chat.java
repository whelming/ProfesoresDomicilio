package com.tdlzgroup.educasa.Chat;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdlzgroup.educasa.Chat.ChatFragments.ChatEjm;
import com.tdlzgroup.educasa.MenuBottom;
import com.tdlzgroup.educasa.R;

public class Chat extends MenuBottom {
    public LinearLayout loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        loader = findViewById(R.id.chat_loader_container);

        // INICIO ACTIVIDAD CHAT
        ChatEjm fragmentprincipal = new ChatEjm();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_chat_principal, fragmentprincipal);
        transaction.commit();
        // FIN ACTIVIDAD CHAT
    }

    public void showLoader(){
        loader.setVisibility(View.VISIBLE);
    }
    public void hideLoader(){
        loader.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "START CHAT", Toast.LENGTH_SHORT).show();
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
