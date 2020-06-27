package com.example.tfg.ui.main;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tfg.IOnBackPressed;
import com.example.tfg.Main2Activity;
import com.example.tfg.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainFragment extends Fragment implements IOnBackPressed {

    private MainViewModel mViewModel;
    private Button bRealizarTest, bReconocerColor;
    private ImageButton bCam, bFile, bPerfiles, bPaleta;
    private boolean permisos;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {


        bRealizarTest=(Button)getView().findViewById(R.id.buttonRealizarTest);
        bReconocerColor=(Button)getView().findViewById(R.id.buttonReconocerColor);
        bPaleta=(ImageButton)getView().findViewById(R.id.buttonPaleta);
        bPerfiles=(ImageButton)getView().findViewById(R.id.buttonPerfiles);

        bCam=(ImageButton)getView().findViewById(R.id.button3);
        bFile = (ImageButton) getView().findViewById(R.id.button4);


        bReconocerColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bCam.getVisibility()==View.VISIBLE){
                    bCam.setVisibility(View.INVISIBLE);
                    bFile.setVisibility(View.INVISIBLE);
                }else{

                    bCam.setVisibility(View.VISIBLE);
                    bFile.setVisibility(View.VISIBLE);
                }
            }
        });

        bPaleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"flaco esto es paleta",Toast.LENGTH_SHORT).show();
            }
        });

        bPerfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"flaco esto es perfiles",Toast.LENGTH_SHORT).show();
            }
        });
        bCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Cámara!",Toast.LENGTH_SHORT).show();
                takePhoto();
            }
        });
        bFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Archivo!",Toast.LENGTH_SHORT).show();
                fileChoose();


            }
        });

        bRealizarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment placaIshihara = new PlacaIshihara();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.main,placaIshihara);
                transaction.addToBackStack(null);
                bRealizarTest.setEnabled(false);
                bReconocerColor.setEnabled(false);
                bPaleta.setEnabled(false);
                bPerfiles.setEnabled(false);
                bFile.setEnabled(false);
                bCam.setEnabled(false);
                transaction.commit();
            }
        });
    }

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.v("permiso","no concedido");
            permisos = false;
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                    },35);
        }else{
            permisos=true;
            Log.v("permiso", "YA CONCEDIDOOOOOOO");
        }
        if (permisos){

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent,41);
            }else Toast.makeText(getContext(),"No posee instalada una cámara", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onBackPressed() {
        bRealizarTest.setEnabled(true);
        bReconocerColor.setEnabled(true);
        bPaleta.setEnabled(true);
        bPerfiles.setEnabled(true);
        bCam.setEnabled(true);
        bFile.setEnabled(true);
        return false;
    }
    private void fileChoose(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 42);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode >40 && resultCode == getActivity().RESULT_OK) {

            Log.v("onActRes", "pasó");
            if (data == null) {
                //Display an error
                Log.e("onActResult", "data= null");
                return;
            }

            Intent intent = new Intent(getActivity(), Main2Activity.class);
            if (requestCode ==42){
                intent.putExtra("uriData",data.getData());

            }else if(requestCode==41){

                intent.putExtras(data.getExtras());
            }
            startActivityForResult(intent,1);
        }
    }
}
