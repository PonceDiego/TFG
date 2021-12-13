package com.example.tfg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.tfg.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static String currentTheme;
    public static int perfil;
    public static ArrayList<String> arrayList;
    public static Map<Integer, Integer> mapaResultado = new HashMap<>();
    public static Map<Integer, String> mapaPaleta = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        currentTheme= "VioletaTheme";
        perfil = 0;
        mapaResultado.put(0,0);
        mapaPaleta.put(0,"VioletaTheme");
        arrayList = new ArrayList<String>();
        setContentView(R.layout.main_activity);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }

    }
}
