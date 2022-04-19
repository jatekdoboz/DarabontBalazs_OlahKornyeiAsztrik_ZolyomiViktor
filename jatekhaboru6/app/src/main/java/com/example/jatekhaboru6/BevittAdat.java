package com.example.jatekhaboru6;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

public class BevittAdat {

    private String jateknev;
    private String kategoria;
    private String korhatar;
    private String varos;
    private String leiras;
    private Bitmap kep;

    public BevittAdat(String jateknev, String kategoria, String korhatar, String varos, String leiras, Bitmap kep) {
        this.jateknev = jateknev;
        this.kategoria = kategoria;
        this.korhatar = korhatar;
        this.varos = varos;
        this.leiras = leiras;
        this.kep = kep;
    }

    public String getJateknev() { return jateknev; }

    public String getKategoria() { return kategoria; }

    public String getKorhatar() { return korhatar; }

    public String getVaros() { return varos; }

    public String getLeiras() {return leiras; }

    public Bitmap getKep() {return kep;}

}
