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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tfg.IOnBackPressed;
import com.example.tfg.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlacaIshihara5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacaIshihara5 extends Fragment {
    private Button bRtaCorrecta, bRtaDalt, bRtaAcro;
    ImageView placaIshi;

    public PlacaIshihara5() {
        // Required empty public constructor
    }
    public static PlacaIshihara5 newInstance(String param1, String param2) {
        PlacaIshihara5 fragment = new PlacaIshihara5();
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
        return inflater.inflate(R.layout.fragment_placa_ishihara, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getView().setBackgroundColor(getResources().getColor(R.color.whiteBack));
        bRtaCorrecta=getView().findViewById(R.id.button5Option1); //TODO: implementar para que no sea siempre la primera
        bRtaDalt=getView().findViewById(R.id.button5Option2);
        bRtaAcro=getView().findViewById(R.id.button5Option3);
        placaIshi=getView().findViewById(R.id.imageViewIshihara);


        bRtaCorrecta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacaIshihara42.contador=PlacaIshihara42.contador+9;
                darResultado();
            }
        });
        bRtaDalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacaIshihara42.contador=PlacaIshihara42.contador+3;
                darResultado();
            }
        });
        bRtaAcro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darResultado();
            }
        });
    }
private void darResultado(){
    Fragment resultado = new ResultadoFragment();
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.main,resultado);
    transaction.addToBackStack(null);
    transaction.commit();
}
}
