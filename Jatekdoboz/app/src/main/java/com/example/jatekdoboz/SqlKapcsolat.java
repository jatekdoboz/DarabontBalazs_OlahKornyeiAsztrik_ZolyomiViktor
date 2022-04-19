package com.example.jatekdoboz;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public class SqlKapcsolat {

    Connection kapcsolat;
    public String ip,port,adatbazis,felh,jelszo;

    @SuppressLint("NewApi")
    public Connection kapcsolatclass(){

        ip="jdbc:mysql://45.84.205.102:3306";
        //port="3306";
        //adatbazis="u121374417_jatekdoboz";
        felh="u121374417_jatekdoboz";
        jelszo="Szeretemafagyit22!";

        StrictMode.ThreadPolicy szabaly = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(szabaly);



        try{

            Class.forName("com.mysql.jdbc.Driver");
            //DriverManager.setLoginTimeout(3);
            kapcsolat = DriverManager.getConnection(ip,felh,jelszo);

        }catch (Exception exception){
            System.out.println("Ninsc kapcsolat");
            Log.e("Error:", exception.getMessage());
            return null;

        }

        return kapcsolat;
    }
}
