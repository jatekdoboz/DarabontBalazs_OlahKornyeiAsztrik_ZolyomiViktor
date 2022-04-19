package com.example.jatekhaboru6;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

public class HirLista {

    private String hircim;
    private String datum;
    private String hir;

    public HirLista(String hircim, String datum, String hir) {
        this.hircim = hircim;
        this.datum = datum;
        this.hir = hir;
    }

    public String getHircim() { return hircim; }

    public String getDatum() { return datum; }

    public String getHir() { return hir; }

}
