package com.example.jatekdoboz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button gomb;
    private Handler handler;

    private AlertDialog.Builder dbuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.nyitoanimacio);


        /*
        gomb = findViewById(R.id.button);

        gomb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kovioldal(v);
            }
        });

         */

        // hogy a "betolto" kepernyon ne jelenjen a navbar
        //getWindow().getDecorView().setSystemUiVisibility(
        //        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        //);



    }

    @Override
    protected void onResume(){
        super.onResume();

        // 2.3 masodperces kesleltetes utan nyitja meg a fooldalt
        final Handler kezelo = new Handler(Looper.myLooper());
        kezelo.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Intent intent = new Intent(getApplicationContext(), weboldal.class);


                felugroAblak();


            }
        },2000);

    }

    public void kovioldal(View view){
        //Intent intent = new Intent(this, Valaszto.class);
        //startActivity(intent);
    }

    private void felugroAblak(){

        dbuilder = new AlertDialog.Builder(MainActivity.this);
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugro_belepes , null);
        felugroablakKeret.setMinimumHeight(600);

        TextView mehet = (TextView) felugroablakKeret.findViewById(R.id.mehet);
        TextView kilepes = (TextView) felugroablakKeret.findViewById(R.id.kilépés);
        EditText adminjelszo = (EditText) felugroablakKeret.findViewById(R.id.adminjelszo);

        mehet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (adminjelszo.getText().toString().trim().equals("almafa12")) {

                    Intent intent = new Intent(MainActivity.this, fomenu.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        kilepes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        dbuilder.setView(felugroablakKeret);
        dialog = dbuilder.create();
        dialog.show();

    }
    }