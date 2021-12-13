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

public class PlacaIshihara26 extends Fragment implements IOnBackPressed {

    private Button bRtaNormal, bRtaRojo, bRtaVerde,bRtaAcro;

    public PlacaIshihara26() {
        // Required empty public constructor
    }
    public static PlacaIshihara26 newInstance(String param1, String param2) {
        PlacaIshihara26 fragment = new PlacaIshihara26();
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
        return inflater.inflate(R.layout.fragment_placa_26, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bRtaAcro=view.findViewById(R.id.button26Option2);
        bRtaNormal=view.findViewById(R.id.button26Option3);
        bRtaRojo=view.findViewById(R.id.button26Option4);
        bRtaVerde=view.findViewById(R.id.button26Option1);

        bRtaAcro.setText("Nada");
        bRtaAcro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguientePlaca();
            }
        });
        bRtaRojo.setText("6");
        bRtaRojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(3);
                siguientePlaca();
            }
        });
        bRtaVerde.setText("2");
        bRtaVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(6);
                siguientePlaca();
            }
        });
        bRtaNormal.setText("26");
        bRtaNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contador.actualizarContador(9);
                siguientePlaca();
            }
        });
    }

    private void siguientePlaca() {
        Fragment placa74= new PlacaIshihara74();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main,placa74);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}

