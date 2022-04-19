package com.example.jatekdoboz;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

public class BevittAdat {

    private String hircim;
    private String datum;
    private String hir;

    public BevittAdat(String hircim, String datum, String hir) {
        this.hircim = hircim;
        this.datum = datum;
        this.hir = hir;
    }

    public String getHircim() { return hircim; }

    public String getDatum() { return datum; }

    public String getHir() { return hir; }

}
