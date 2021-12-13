package com.example.tfg.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.IOnBackPressed;
import com.example.tfg.MainActivity;
import com.example.tfg.R;
import com.example.tfg.ui.main.utils.Contador;

import java.util.ArrayList;

public class ResultadoFragment extends Fragment implements IOnBackPressed {
    private Button confirmar;
    private ArrayList<String> arrayList;



    public ResultadoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            arrayList= savedInstanceState.getStringArrayList("arrayList");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putStringArrayList("arrayList", arrayList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resultado, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tw=view.findViewById(R.id.textViewResultado);
        if(Contador.total ==0){
            //Acromatismo
            MainActivity.mapaResultado.put(MainActivity.perfil,5);
            tw.setText(R.string.acromatismo);
        }else if(Contador.total==56){
            //Normal
            MainActivity.mapaResultado.put(MainActivity.perfil,1);
            tw.setText(R.string.normal);
        }else if (Contador.total==34){
            //Verde
            MainActivity.mapaResultado.put(MainActivity.perfil,3);
            tw.setText(R.string.deuteranopia);
        }else if (Contador.total==28) {
            //Rojo
            MainActivity.mapaResultado.put(MainActivity.perfil, 4);
            tw.setText(R.string.protanopia);
        }else{
            //Problablemente Daltónico
            MainActivity.mapaResultado.put(MainActivity.perfil,2);
            tw.setText(R.string.daltonico);
        }
        confirmar= view.findViewById(R.id.buttonConfirmarResultado);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.reiniciarContador();
                Fragment mainFragment= new MainFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main,mainFragment);
                transaction.addToBackStack("resultado");
                transaction.commit();
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        Fragment mainFragment = new MainFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main,mainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return false;
    }
}