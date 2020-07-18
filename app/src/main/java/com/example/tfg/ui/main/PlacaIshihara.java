package com.example.tfg.ui.main;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
 * Use the {@link PlacaIshihara#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacaIshihara extends Fragment implements IOnBackPressed {
    private Button bRtaCorrecta, bRtaDalt, bRtaAcro;
    ImageView placaIshi;

    public PlacaIshihara() {
        // Required empty public constructor
    }
    public static PlacaIshihara newInstance(String param1, String param2) {
        PlacaIshihara fragment = new PlacaIshihara();
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

        getView().setBackgroundColor(Color.LTGRAY);
        bRtaCorrecta=getView().findViewById(R.id.buttonOption1); //TODO: implementar para que no sea siempre la primera
        bRtaDalt=getView().findViewById(R.id.buttonOption2);
        bRtaAcro=getView().findViewById(R.id.buttonOption3);
        placaIshi=getView().findViewById(R.id.imageViewIshihara);


        bRtaCorrecta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(getContext(),"Correcto!",Toast.LENGTH_SHORT).show();

            }
        });
        bRtaDalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Daltonismo!",Toast.LENGTH_SHORT).show();

            }
        });
        bRtaAcro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Acromatismo!",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
