package com.example.jatekhaboru6;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

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

public class Regisztral extends AppCompatActivity {

    private Button regisztralo;
    private EditText felhasznalonev;
    private EditText email;
    private EditText jelszo1;
    private EditText jelszo2;
    private ScrollView gorgo;
    private View ablak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_regisztral);
        regisztralo = findViewById(R.id.button);
        felhasznalonev = findViewById(R.id.felhasznalo);
        email = findViewById(R.id.email);
        jelszo1 = findViewById(R.id.jelszo1);
        jelszo2 = findViewById(R.id.jelszo2);
        gorgo = findViewById(R.id.gorgo);
        ablak = findViewById(R.id.ablak);


        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(R.attr.colorSecondary, typedValue, true);
        @ColorInt int szin = typedValue.data;

        TypedValue typedValue2 = new TypedValue();
        Resources.Theme theme2 = this.getTheme();
        theme.resolveAttribute(R.attr.colorPrimaryVariant, typedValue, true);
        @ColorInt int szin2 = typedValue.data;

        TypedValue typedValue3 = new TypedValue();
        Resources.Theme theme3 = this.getTheme();
        theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true);
        @ColorInt int szin3 = typedValue.data;

        TypedValue typedValue4 = new TypedValue();
        Resources.Theme theme4 = this.getTheme();
        theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        @ColorInt int szin4 = typedValue.data;

        GradientDrawable background = (GradientDrawable) felhasznalonev.getBackground();

        gorgo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                billentyuzetelrejt(view);
                felhasznalonev.clearFocus();
                email.clearFocus();
                jelszo1.clearFocus();
                jelszo2.clearFocus();
                ablak.requestFocus();
                return false;
            }
        });

        regisztralo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regisztralas();

            }
        });

        felhasznalonev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
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
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (email.hasFocus()==true) {
                    //erint();
                    email.setBackgroundColor(szin2);
                    email.setTextColor(szin3);
                }else{
                    //erint2();
                    email.setBackground(background);
                }
            }
        });
        jelszo1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (jelszo1.hasFocus()==true) {
                    //erint();
                    jelszo1.setBackgroundColor(szin2);
                    jelszo1.setTextColor(szin3);
                }else{
                    //erint2();

                    jelszo1.setBackground(background);
                }
            }
        });
        jelszo2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (jelszo2.hasFocus()==true) {
                    //erint();
                    jelszo2.setBackgroundColor(szin2);
                    jelszo2.setTextColor(szin3);

                }else{
                    //erint2();

                    jelszo2.setBackground(background);
                }
            }
        });
    }

    private void regisztralas(){
        this.finish();
        Intent intent = new Intent(this, Bejelent.class);
        startActivity(intent);
    }
    public void billentyuzetelrejt(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}