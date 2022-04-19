package com.example.jatekhaboru6;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

public class Bejelent extends AppCompatActivity {

    private Button bejelentkez;
    private EditText jelszo;
    private EditText felhasznalonev;
    private CardView felhasznalokeret;
    private View ablak;
    private ScrollView gorgo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bejelent);
        bejelentkez = findViewById(R.id.button);
        felhasznalonev = findViewById(R.id.editTextTextPersonName);
        felhasznalokeret = findViewById(R.id.felhasznaloborder);
        jelszo = findViewById(R.id.editTextTextPassword);
        ablak = findViewById(R.id.ablak);
        gorgo = findViewById(R.id.gorgo);



        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(R.attr.colorSecondary, typedValue, true);
        @ColorInt int szin = typedValue.data;

        TypedValue typedValue2 = new TypedValue();
        Resources.Theme theme2 = this.getTheme();
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        @ColorInt int szin2 = typedValue.data;

        TypedValue typedValue3 = new TypedValue();
        Resources.Theme theme3 = this.getTheme();
        theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true);
        @ColorInt int szin3 = typedValue.data;

        GradientDrawable background = (GradientDrawable) felhasznalonev.getBackground();

        GradientDrawable background2 = (GradientDrawable) jelszo.getBackground();

       /*ablak.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               billentyuzetelrejt(view);

           }
       });

       felhasznalonev.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               gorgo.smoothScrollTo(0,700);
           }
       });

        gorgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billentyuzetelrejt(view);
                ablak.requestFocus();
            }
        });

        */

        gorgo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                billentyuzetelrejt(view);
                felhasznalonev.clearFocus();
                jelszo.clearFocus();
                ablak.requestFocus();
                return false;
            }
        });

        bejelentkez.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(bejelentkez.hasFocus()!=true){

                }else{gorgo.smoothScrollTo(0,0);}
            }
        });
        felhasznalonev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (felhasznalonev.hasFocus()==true) {
                    //erint();
                    felhasznalonev.setBackgroundColor(szin2);
                    felhasznalonev.setTextColor(szin3);

                }else{
                    //erint2();

                    felhasznalonev.setBackground(background);
                }
            }
        });

        jelszo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (jelszo.hasFocus()==true) {
                    //erint();
                    jelszo.setBackgroundColor(szin2);
                    jelszo.setTextColor(szin3);
                    //gorgo.smoothScrollTo(0,1000);
                    jelszo.requestFocus();
                }else{
                    //erint2();
                    jelszo.setBackground(background2);
                }
            }
        });

        bejelentkez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bejelentkezo();
            }
        });
    }

    private void bejelentkezo(){
        this.finish();
        Intent intent = new Intent(this, Fomenu.class);
        startActivity(intent);
    }


    /*

    public void erint(){
        felhasznalonev.setBackgroundColor(this.getResources().getColor(R.color.teal_200));
        //felhasznalonev.setBackgroundColor(szin);
    }
    public void erint2(){
        felhasznalonev.setBackgroundColor(this.getResources().getColor(R.color.zold));

    }

     */
    public void billentyuzetelrejt(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}