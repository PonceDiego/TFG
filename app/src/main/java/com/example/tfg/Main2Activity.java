package com.example.tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import android.net.Uri;


public class Main2Activity extends AppCompatActivity {
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }

    @Override
    protected void onResume() {
        super.onResume();
        imagen = findViewById(R.id.imageViewReconocer);
        final Bundle b = getIntent().getExtras();
        if (!b.isEmpty()) {

            Main2Activity.this.runOnUiThread(new Runnable() {
                public void run() {
                    imagen.setImageURI((Uri) b.get("data"));
                    Log.v("thread", "cargó");
                }
            });
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }
    }
}
