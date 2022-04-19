package com.example.jatekhaboru6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button gomb;
    private Handler handler;

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
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        );



    }

    @Override
    protected void onResume(){
        super.onResume();

        // 2.3 masodperces kesleltetes utan nyitja meg a fooldalt
        final Handler kezelo = new Handler(Looper.myLooper());
        kezelo.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Intent intent = new Intent(getApplicationContext(), Weboldal.class);

                Intent intent = new Intent(MainActivity.this, Fomenu.class);
                startActivity(intent);
                finish();


            }
        },2300);

    }

    public void kovioldal(View view){
        Intent intent = new Intent(this, Valaszto.class);
        startActivity(intent);
    }
}