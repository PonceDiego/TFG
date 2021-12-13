package com.example.tfg.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.IOnBackPressed;
import com.example.tfg.R;


public class PlacaIshihara42 extends Fragment {

public class PlacaIshihara42 extends Fragment {

public class PlacaIshihara42 extends Fragment implements IOnBackPressed {

    private Button bRtaNormal, bRtaRojo, bRtaVerde,bRtaAcro;
    public static int contador;

    public PlacaIshihara42() {
        // Required empty public constructor
    }
    public static PlacaIshihara42 newInstance(String param1, String param2) {
        PlacaIshihara42 fragment = new PlacaIshihara42();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        contador=0;
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_placa_42, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bRtaAcro=view.findViewById(R.id.button42Option1);
        bRtaNormal=view.findViewById(R.id.button42Option2);
        bRtaRojo=view.findViewById(R.id.button42Option3);
        bRtaVerde=view.findViewById(R.id.button42Option4);

        bRtaAcro.setText("Nada");
        bRtaAcro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguientePlaca();
            }
        });
        bRtaRojo.setText("2");
        bRtaRojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador=contador+3;
                siguientePlaca();
            }
        });
        bRtaVerde.setText("4");
        bRtaVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador=contador+6;
                siguientePlaca();
            }
        });
        bRtaNormal.setText("42");
        bRtaNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador=contador+9;
                siguientePlaca();
            }
        });
    }

    private void siguientePlaca() {
        Fragment placa26= new PlacaIshihara26();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main,placa26);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

}
