package com.example.jatekhaboru6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Valaszto extends AppCompatActivity {

    private Button bejelentkez;
    private Button regisztracio;

    public static int k = 0;

    /*int sotetzold = ContextCompat.getColor(this,R.color.zold2);
    int zold = ContextCompat.getColor(this,R.color.zold);
    int sotetbezs = ContextCompat.getColor(this,R.color.bezs2);
    int bezs = ContextCompat.getColor(this,R.color.bezs);
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //az alkalmazas cimsoranak elrejtese
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_valaszto);


        //a kijelzo mereteinek a lekerese (ezt fogjuk hasznalni az xml fajlban)
        DisplayMetrics kijelzoTulajdonsagok = getResources().getDisplayMetrics();

        int kijelzoMagas = kijelzoTulajdonsagok.heightPixels;
        int kijelzoSzeles = kijelzoTulajdonsagok.widthPixels;


        //elemek hozzarendelese a layoutban meghatarozott gombokhoz
        bejelentkez = findViewById(R.id.button3);
        regisztracio = findViewById(R.id.button2);

        //gombok funkcioinak meghatarozasa

        bejelentkez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bejelent();
            }
        });

        regisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regisztral();
            }
        });

        /*
        bejelentkez.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                erint();
                return false;
            }
        });

        regisztracio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                erint2();
                return false;
            }
        });
        */

    }

    public void bejelent(){
        // bejelentkez.setBackgroundColor(this.getResources().getColor(R.color.zold));
        Intent intent = new Intent(this, Bejelent.class);
        startActivity(intent);
    }

    public void regisztral(){
        //  regisztracio.setBackgroundColor(this.getResources().getColor(R.color.bezs));
        Intent intent = new Intent(this, Regisztral.class);
        startActivity(intent);
    }
    public void erint(){
        bejelentkez.setBackgroundColor(this.getResources().getColor(R.color.zold2));
    }
    public void erint2(){
        regisztracio.setBackgroundColor(this.getResources().getColor(R.color.bezs2));

    }

}