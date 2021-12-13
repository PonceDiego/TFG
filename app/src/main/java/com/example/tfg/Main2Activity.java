package com.example.tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.ui.main.MainFragment;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class Main2Activity extends AppCompatActivity {
    private Button destacar;
    private View viewColor;
    private TextView nombreColor;
    private ColorPickerView colorPickerView;
    private Bitmap bitmap;
    private ProgressDialog progresdialoglistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        progresdialoglistview=new ProgressDialog(Main2Activity.this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        destacar=findViewById(R.id.buttonDestacar);
        viewColor=findViewById(R.id.viewColorPicked);
        viewColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelector();
            }
        });
        nombreColor=findViewById(R.id.textViewColor);
        colorPickerView=findViewById(R.id.imageViewReconocer);
        View view=findViewById(R.id.view);
        Window window= getWindow();
        switch (MainActivity.currentTheme){
            default:
            case "VioletaTheme":
                window.setStatusBarColor(getResources().getColor(R.color.primarioVioletaDark));
                window.setNavigationBarColor(getResources().getColor(R.color.primarioVioletaLight));
                view.setBackgroundColor(getColor(R.color.primarioVioletaLight));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primarioVioleta)));
                break;
            case "AzulTheme":
                window.setStatusBarColor(getResources().getColor(R.color.primarioAzulDark));
                window.setNavigationBarColor(getResources().getColor(R.color.primarioAzulLight));
                view.setBackgroundColor(getColor(R.color.primarioAzulLight));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primarioAzul)));
                break;
            case "RojoTheme":
                window.setStatusBarColor(getResources().getColor(R.color.primarioRojoDark));
                window.setNavigationBarColor(getResources().getColor(R.color.primarioRojoLight));
                view.setBackgroundColor(getColor(R.color.primarioRojoLight));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primarioRojo)));
                break;
            case "GreenTheme":
                window.setStatusBarColor(getResources().getColor(R.color.primarioVerdeDark));
                window.setNavigationBarColor(getResources().getColor(R.color.primarioVerdeLight));
                view.setBackgroundColor(getColor(R.color.primarioVerdeLight));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primarioVerde)));
                break;
        }


        final Bundle b = getIntent().getExtras();
        if (!b.isEmpty()) {
            Main2Activity.this.runOnUiThread(new Runnable() {
                public void run() {
                    if (b.get("photoUri")==null){
                        try {
                            final Uri imageUri = (Uri)b.get("fileUri");
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            Drawable drawable = new BitmapDrawable(getResources(), imageStream);
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                            if(bitmapDrawable.getBitmap() != null) {
                                bitmap= Bitmap.createBitmap(bitmapDrawable.getBitmap());
                            }
                            colorPickerView.setPaletteDrawable(drawable);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            final Uri imageUri = Uri.parse((String) b.get("photoUri"));
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            Matrix matrix = new Matrix();
                            matrix.preRotate(90);
                            final Bitmap rotado= Bitmap.createBitmap(selectedImage,0,0,selectedImage.getWidth(),selectedImage.getHeight(),matrix,true);
                            bitmap=rotado;
                            Drawable drawable = new BitmapDrawable(getResources(), rotado);
                            colorPickerView.setPaletteDrawable(drawable);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    colorPickerView.setColorListener(new ColorEnvelopeListener() {
                        @Override
                        public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                            viewColor.setBackgroundColor(envelope.getColor());
                            nombreColor.setText("#"+envelope.getHexCode());
                        }
                    });
                }
            });
        destacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String nombreSin= nombreColor.getText().toString();
                        destacarColor(bitmap,nombreSin.substring(1));
                    }
                });
            }
        });
        }
    }
    private void destacarColor(Bitmap b, String hex){
        MyTaskParams myTaskParams= new MyTaskParams(b,hex);
        MyTaskParams[] pum = new MyTaskParams[1];
        pum[0]=myTaskParams;
        AsyncTask run= new DetectarColor();
        run.execute( (Object[])pum);
    }
    private void abrirSelector() {
        new ColorPickerDialog.Builder(this, R.style.Theme_AppCompat_Dialog)
                .setTitle("Seleccione un color")
                .setPreferenceName("ColorPickerDialog")
                .setPositiveButton(getString(R.string.confirm),
                        new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                viewColor.setBackgroundColor(envelope.getColor());
                                nombreColor.setText("#"+envelope.getHexCode());

                            }
                        })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true)  // default is true. If false, do not show the BrightnessSlideBar.
                .show();
    }
    private final class DetectarColor extends AsyncTask<MyTaskParams,Void,int[][]>{
        @Override
        protected void onPreExecute() {
            progresdialoglistview.setMessage("Procesando");
            progresdialoglistview.show();
        }

        @Override
        protected int[][] doInBackground(MyTaskParams... myTaskParams) {
            Bitmap b=myTaskParams[0].foo;
            String hex=myTaskParams[0].bar;
            long value = Long.parseLong(hex, 16);
            int redR = (int) (0xFF & ( value >> 16));
            int greenR = (int) (0xFF & (value >> 8 ));
            int blueR = (int) (0xFF & (value >> 0 ));
            int ancho=b.getWidth();
            int altura=b.getHeight();
            int nuevoARGB;
            int redDivision,blueDivision,greenDivision;
            int [][] arrayPixelexColores= new int[ancho][altura];

            for(int i=0;i<ancho;i++){
                for (int j=0;j<altura;j++){
                    int argb=b.getPixel(i,j);
                    int alpha;
                    int red = 0xFF & ( argb >> 16);
                    int green = 0xFF & (argb >> 8 );
                    int blue = 0xFF & (argb >> 0 );

                    arrayPixelexColores[i][j]=b.getPixel(i,j);
                    redDivision=Math.abs(redR-red);
                    blueDivision=Math.abs(blueR-blue);
                    greenDivision=Math.abs(greenR-green);

                    if (redDivision>35||blueDivision>35||greenDivision>35){
                        if(red<105){
                            red=red+150;
                        }else if (red==255){
                            red=200;
                        }else red=255;
                        if (green<105){
                            green=green+150;
                        }else if (green==255){
                            green=200;
                        }else green=255;
                        if (blue<105){
                            blue=blue+150;
                        }else if (blue==255){
                            blue=200;
                        }else blue=255;
                        alpha=100;

                        nuevoARGB=(alpha << 24) | (red << 16 ) | (green<<8) | blue;
                        arrayPixelexColores[i][j]=nuevoARGB;
                    }
                }
            }
            return arrayPixelexColores;
        }

        @Override
        protected void onPostExecute(int[][] pixeles) {

            int ancho = pixeles.length;
            int alto= pixeles[0].length;
            Bitmap bitDestacado= Bitmap.createBitmap
                    (ancho,alto, Bitmap.Config.RGB_565);
            for (int i=0;i<ancho;i++){
                for (int j=0;j<alto;j++){
                    bitDestacado.setPixel(i,j,pixeles[i][j]);
                }
            }

            Dialog builder = new Dialog(Main2Activity.this);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //nothing;
                }
            });
            ImageView imageView = new ImageView(Main2Activity.this);
            imageView.setImageBitmap(bitDestacado);
            imageView.setBackgroundColor(Color.DKGRAY);
            builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            if (progresdialoglistview.isShowing()) {
                progresdialoglistview.dismiss();
            }
            builder.show();
        }
    }
    private static class MyTaskParams {
        Bitmap foo;
        String bar;

        MyTaskParams(Bitmap foo, String bar) {
            this.foo = foo;
            this.bar = bar;
        }
    }
}
