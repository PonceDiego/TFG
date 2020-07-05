package com.example.tfg.ui.main;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
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

import java.io.File;

public class MainFragment extends Fragment implements IOnBackPressed {

    private MainViewModel mViewModel;
    private Button bRealizarTest, bReconocerColor;
    private ImageButton bCam, bFile, bPerfiles, bPaleta;
    private boolean permisos;
    private final int CAMERA_PIC = 41;
    private final int FILE_PIC=42;
    private String extra;
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.ponce.tfg";
    private Context context;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context=getActivity().getApplicationContext();

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


        bRealizarTest= getView().findViewById(R.id.buttonRealizarTest);
        bReconocerColor= getView().findViewById(R.id.buttonReconocerColor);
        bPaleta= getView().findViewById(R.id.buttonPaleta);
        bPerfiles= getView().findViewById(R.id.buttonPerfiles);

        bCam= getView().findViewById(R.id.button3);
        bFile = getView().findViewById(R.id.button4);


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
            File path = new File(getActivity().getFilesDir(), "tfg/camera/");
            if (!path.exists()) path.mkdirs();
            File image = new File(path, "image.jpg");
            Uri imageUri = FileProvider.getUriForFile(getActivity(), CAPTURE_IMAGE_FILE_PROVIDER, image);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            extra=imageUri.toString();

            Log.v("uriString",extra);


            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent,CAMERA_PIC);
            }else Toast.makeText(getContext(),"No posee instalada una cámara", Toast.LENGTH_SHORT).show();
        }
    }

    private void fileChoose(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, FILE_PIC);

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==CAMERA_PIC || requestCode==FILE_PIC && resultCode == getActivity().RESULT_OK) {
            Intent intent2 = new Intent(getActivity(), Main2Activity.class);
            if (requestCode == FILE_PIC){
                if (data == null) {
                    Log.e("onActResult", "data= null");
                    Toast.makeText(getContext(),"Ocurrió un error, por favor inténtelo nuevamente.",Toast.LENGTH_LONG).show();
                    return;
                }
                intent2.putExtra("fileUri",data.getData());
            }else{
                intent2.putExtra("photoUri",extra);
            }
            startActivityForResult(intent2,1);
        }
    }
}
