package com.example.jatekhaboru6;

import com.example.jatekhaboru6.ui.dashboard.Hirek;

public class HIrekThread implements Runnable{


    private void valami(){new Hirek().HirLekerdezes();}

    @Override
    public void run() {
        valami();
    }
}
