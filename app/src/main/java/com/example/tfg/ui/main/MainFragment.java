package com.example.tfg.ui.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tfg.IOnBackPressed;
import com.example.tfg.Main2Activity;
import com.example.tfg.MainActivity;
import com.example.tfg.R;

import java.io.File;

public class MainFragment extends Fragment implements IOnBackPressed {

    private Button bRealizarTest, bReconocerColor;
    private ImageButton bCam, bFile, bPerfiles, bPaleta;
    private boolean permisos;
    private final int CAMERA_PIC = 41;
    private final int FILE_PIC=42;
    private String extra;
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.ponce.tfg";
    private int checkedItem = 0; // Diego


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
        bReconocerColor.getBackground().setAlpha(200);
        bRealizarTest.getBackground().setAlpha(200);
        bPerfiles.getBackground().setAlpha(225);
        bPerfiles.getBackground().setAlpha(225);

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
                cambiarPaleta();
            }
        });

        bPerfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("Selección de perfil.");

                // add a radio button list
                String[] perfiles = {"Diego", "Alumno1", "Alumno2", "Alumno3"};

                builder.setSingleChoiceItems(perfiles, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem=which;
                        // user checked an item
                    }
                });

                // add OK and Cancel buttons
                builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked OK
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.cancel), null);

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        bCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        bFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                bRealizarTest.setVisibility(View.INVISIBLE);
                bReconocerColor.setVisibility(View.INVISIBLE);
                bCam.setVisibility(View.INVISIBLE);
                bFile.setVisibility(View.INVISIBLE);
                bPaleta.setVisibility(View.INVISIBLE);
                bPerfiles.setVisibility(View.INVISIBLE);
                transaction.commit();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        Window window = getActivity().getWindow();
        switch (MainActivity.currentTheme){
            default:
            case "VioletaTheme":
               violetaSettings(window,mActionBar);
                break;
            case "AzulTheme":
                azulSettings(window,mActionBar);
                break;
            case "RojoTheme":
               rojoSettings(window,mActionBar);
                break;
            case "GreenTheme":
                verdeSettings(window,mActionBar);
                break;
        }
    }

    private void cambiarPaleta(){
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Selección de paleta.");

        // add a radio button list
        String[] paletas = {"Violeta", "Azul", "Rojo", "Verde"};
        int elegido =0;
        switch (MainActivity.currentTheme){
            default:
            case "VioletaTheme":
                elegido=0;
                break;
            case "AzulTheme":
                elegido=1;
                break;
            case "RojoTheme":
                elegido=2;
                break;
            case "GreenTheme":
                elegido=3;
                break;

            }
            builder.setSingleChoiceItems(paletas,elegido , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                Window window = getActivity().getWindow();
                // user checked an item
                switch (which){
                    case 0:
                        violetaSettings(window,mActionBar);
                        break;
                    case 1:
                        azulSettings(window,mActionBar);
                        break;
                    case 2:
                        rojoSettings(window,mActionBar);
                        break;
                    case 3:
                        verdeSettings(window,mActionBar);
                        break;
                }
            }
            });

        // add OK and Cancel buttons
        builder.setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();


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
        bRealizarTest.setVisibility(View.VISIBLE);
        bReconocerColor.setVisibility(View.VISIBLE);
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
                if (getSize(getContext(),data.getData())>1000000){
                    Toast.makeText(getContext(),"Imagen muy grande!",Toast.LENGTH_LONG).show();
                    return;
                }else{

                Log.v("size","size: "+getSize(getContext(),data.getData()));
                intent2.putExtra("fileUri",data.getData());
                }
            }else{
                intent2.putExtra("photoUri",extra);
            }
            startActivityForResult(intent2,1);
        }
    }
    private void violetaSettings(Window window, ActionBar mActionBar){

        MainActivity.currentTheme="VioletaTheme";
        window.setStatusBarColor(getResources().getColor(R.color.primarioVioletaDark));
        window.setNavigationBarColor(getResources().getColor(R.color.primarioVioletaLight));
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primarioVioleta)));
        getView().setBackground(getResources().getDrawable(R.drawable.background_purple));
    }
    private void azulSettings(Window window, ActionBar mActionBar){
        MainActivity.currentTheme="AzulTheme";
        window.setStatusBarColor(getResources().getColor(R.color.primarioAzulDark));
        window.setNavigationBarColor(getResources().getColor(R.color.primarioAzulLight));
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primarioAzul)));
        getView().setBackground(getResources().getDrawable(R.drawable.background_blue));
    }
    private void verdeSettings(Window window, ActionBar mActionBar){
        MainActivity.currentTheme="GreenTheme";
        window.setStatusBarColor(getResources().getColor(R.color.primarioVerdeDark));
        window.setNavigationBarColor(getResources().getColor(R.color.primarioVerdeLight));
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primarioVerde)));
        getView().setBackground(getResources().getDrawable(R.drawable.background_green));
    }
    private void rojoSettings(Window window, ActionBar mActionBar){
        MainActivity.currentTheme="RojoTheme";
        window.setStatusBarColor(getResources().getColor(R.color.primarioRojoDark));
        window.setNavigationBarColor(getResources().getColor(R.color.primarioRojoLight));
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primarioRojo)));
        getView().setBackground(getResources().getDrawable(R.drawable.background_red));
    }
    private static int getSize(Context context, Uri uri) {
        String fileSize = null;
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {

                // get file size
                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                if (!cursor.isNull(sizeIndex)) {
                    fileSize = cursor.getString(sizeIndex);
                }
            }
        } finally {
            cursor.close();
        }
        return Integer.parseInt(fileSize);
    }
}
