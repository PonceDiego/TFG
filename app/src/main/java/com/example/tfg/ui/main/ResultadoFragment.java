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
import android.widget.Toast;

import com.example.tfg.IOnBackPressed;
import com.example.tfg.MainActivity;
import com.example.tfg.R;

public class ResultadoFragment extends Fragment implements IOnBackPressed {
    private Button confirmar;



    public ResultadoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if(PlacaIshihara42.contador<=1){
            //Acromatismo
            tw.setText(R.string.acromatismo);
        }else if (PlacaIshihara42.contador<=9){
            //Verde
            tw.setText(R.string.deuteranopia);
        }else if (PlacaIshihara42.contador<=15){
            //Rojo
            tw.setText(R.string.protanopia);
        }else if(PlacaIshihara42.contador==27)//Normal
            tw.setText(R.string.normal);
        else //Problablemente Daltónico
            tw.setText(R.string.daltonico);
        confirmar= view.findViewById(R.id.buttonConfirmarResultado);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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