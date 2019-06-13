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
    private Context micontexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        micontexto = this;
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        ListenerMiMenu(micontexto);
    }

    @Override
    public void ListenerMiMenu(Context cont) {
        super.ListenerMiMenu(cont);
    }
}
