package com.example.tfg.ui.main.placas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.IOnBackPressed;
import com.example.tfg.R;
import com.example.tfg.ui.main.utils.Contador;

public class PlacaIshihara74 extends Fragment implements IOnBackPressed {

    private Button bRtaNormal, bRtaDalt, bRtaAcro;

    @Override
    public boolean onBackPressed() {
        return true;
    }

    //constructor
    public PlacaIshihara74(){

    }

    //esto parece que se usa para pasar par{ametros de un fragmento (trozo de pantalla) a otro
    public static PlacaIshihara74 newInstance(String param1, String param2){
        PlacaIshihara74 fragment = new PlacaIshihara74();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_placa_74, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bRtaAcro=view.findViewById(R.id.button74Option1);
        bRtaNormal=view.findViewById(R.id.button74Option2);
        bRtaDalt=view.findViewById(R.id.button74Option3);

        bRtaAcro.setText(R.string.opcion_nada);
        bRtaAcro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(0);
                siguientePlaca();
            }
        });

        bRtaDalt.setText("74");
        bRtaDalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(10);
                siguientePlaca();
            }
        });

        bRtaNormal.setText("21");
        bRtaNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(5);
                siguientePlaca();
            }
        });

    }

    private void siguientePlaca(){
        Fragment placa35= new PlacaIshihara35();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main,placa35);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
