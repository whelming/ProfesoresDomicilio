package com.example.educasa.Bienvenida;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educasa.MainActivity;
import com.example.educasa.R;

public class Bienvenida extends AppCompatActivity {
    private Button btn_iniciar_sesion;
    private Button btn_crear_cuenta;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // INICIO ACTIVIDAD BIENVENIDA

        btn_iniciar_sesion = findViewById(R.id.btn_iniciar_sesion);
        btn_crear_cuenta = findViewById(R.id.btn_crear_cuenta);

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Bienvenida.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_crear_cuenta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Bienvenida.this, "CRAR CUENTA", Toast.LENGTH_SHORT).show();
            }
        });

        // FIN ACTIVIDAD BIENVENIDA

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }
        @Override
        public int getCount() {
            return 2;
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_bienvenida_fragment1, container, false);
            TextView textView  = rootView.findViewById(R.id.texto_detalle);

            if (getArguments().getInt(ARG_SECTION_NUMBER)==1){
                textView.setText(R.string.bienvenida_text1);
            }
            else if (getArguments().getInt(ARG_SECTION_NUMBER)==2){
                textView.setText(R.string.bienvenida_text2);
            } else{

            }
            return rootView;
        }
    }
}
