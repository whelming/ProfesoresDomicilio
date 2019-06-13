package com.example.educasa.Inicio;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.educasa.MenuBottom;
import com.example.educasa.R;

public class Inicio extends MenuBottom {
    private Context micontexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        micontexto = this;
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        ListenerMiMenu(micontexto);
    }

    @Override
    public void ListenerMiMenu(Context cont) {
        super.ListenerMiMenu(cont);
    }
}
