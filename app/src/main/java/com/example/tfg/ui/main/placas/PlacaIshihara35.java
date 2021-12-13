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

import com.example.tfg.R;
import com.example.tfg.ui.main.utils.Contador;

public class PlacaIshihara35  extends Fragment {

    private Button bRtaNormal, bRtaRojo, bRtaVerde,bRtaAcro;

    public PlacaIshihara35() {
        // Required empty public constructor
    }
    public static PlacaIshihara35 newInstance(String param1, String param2) {
        PlacaIshihara35 fragment = new PlacaIshihara35();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_placa_35, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bRtaAcro=view.findViewById(R.id.button35Option1);
        bRtaNormal=view.findViewById(R.id.button35Option2);
        bRtaRojo=view.findViewById(R.id.button35Option3);
        bRtaVerde=view.findViewById(R.id.button35Option4);

        bRtaAcro.setText("Nada");
        bRtaAcro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(0);
                siguientePlaca();
            }
        });
        bRtaRojo.setText("5");
        bRtaRojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(3);
                siguientePlaca();
            }
        });
        bRtaVerde.setText("3");
        bRtaVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(6);
                siguientePlaca();
            }
        });
        bRtaNormal.setText("35");
        bRtaNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(9);
                siguientePlaca();
            }
        });
    }

    private void siguientePlaca() {
        Fragment placa96= new PlacaIshihara96();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main,placa96);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}

