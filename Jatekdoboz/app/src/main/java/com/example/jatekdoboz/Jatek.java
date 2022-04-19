package com.example.jatekdoboz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Jatek{
    private String jatekneve;
    private String kategoriaja;
    private String korhatara;
    private String varosa;
    private String leirasa;
    private Bitmap kepe;
    private static Bitmap bitmapkep;
    private static String url = "https://jatekdoboz.xyz/imageKicsi/";


    public Jatek(String jateknev, String kategoria, String korhatar,String varos,String leiras,Bitmap kep) {
        jatekneve = jateknev;
        kategoriaja = kategoria;
        korhatara = korhatar;
        varosa = varos;
        leirasa = leiras;
        kepe = kep;
    }

    public String getName() {
        return jatekneve;
    }

    public String getKategoriaja() {
        return kategoriaja;
    }
    public String getKorhatara() {
        return korhatara;
    }
    public String getVarosa() {
        return varosa;
    }
    public String getLeirasa() {
        return leirasa;
    }
    public Bitmap getKepe() {
        return kepe;
    }

    public static ArrayList<Jatek> jatekLista() {

        ArrayList<Jatek> jatekok = new ArrayList<Jatek>();

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,image from jatekok3");

            while (eredmeny.next()) {

                String jateknev;
                String kategoria;
                String korhatar;
                String varos;
                String leiras;
                String kep;
                jateknev = eredmeny.getString("jateknev");
                kategoria = eredmeny.getString("kategoria");
                korhatar = eredmeny.getString("korhatar");
                varos = eredmeny.getString("varos");
                leiras = eredmeny.getString("leiras");
                kep = eredmeny.getString("image");

                String utvonal = url+kep;
                keplekerdezes(utvonal);

                jatekok.add(new Jatek(jateknev,kategoria,korhatar,varos,leiras,bitmapkep));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jatekok;
    }

    public static void keplekerdezes(String urlString) throws MalformedURLException, IOException {

        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        bitmapkep=bitmap;
    }

    public static ArrayList<Jatek> jatekListaDatumFel() {

        ArrayList<Jatek> jatekok = new ArrayList<Jatek>();

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,image from jatekok3");

            while (eredmeny.next()) {

                String jateknev;
                String kategoria;
                String korhatar;
                String varos;
                String leiras;
                String kep;
                jateknev = eredmeny.getString("jateknev");
                kategoria = eredmeny.getString("kategoria");
                korhatar = eredmeny.getString("korhatar");
                varos = eredmeny.getString("varos");
                leiras = eredmeny.getString("leiras");
                kep = eredmeny.getString("image");

                String utvonal = url+kep;
                keplekerdezes(utvonal);

                jatekok.add(new Jatek(jateknev,kategoria,korhatar,varos,leiras,bitmapkep));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jatekok;
    }

    public static ArrayList<Jatek> jatekListaDatumLe() {

        ArrayList<Jatek> jatekok = new ArrayList<Jatek>();

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,image from jatekok3 order by datum DESC");

            while (eredmeny.next()) {

                String jateknev;
                String kategoria;
                String korhatar;
                String varos;
                String leiras;
                String kep;
                jateknev = eredmeny.getString("jateknev");
                kategoria = eredmeny.getString("kategoria");
                korhatar = eredmeny.getString("korhatar");
                varos = eredmeny.getString("varos");
                leiras = eredmeny.getString("leiras");
                kep = eredmeny.getString("image");

                String utvonal = url+kep;
                keplekerdezes(utvonal);

                jatekok.add(new Jatek(jateknev,kategoria,korhatar,varos,leiras,bitmapkep));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jatekok;
    }

    public static ArrayList<Jatek> jatekListaVaros(String varosszuro) {

        ArrayList<Jatek> jatekok = new ArrayList<Jatek>();

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,image from jatekok3 where varos='"+ varosszuro +"'");

            while (eredmeny.next()) {

                String jateknev;
                String kategoria;
                String korhatar;
                String varos;
                String leiras;
                String kep;
                jateknev = eredmeny.getString("jateknev");
                kategoria = eredmeny.getString("kategoria");
                korhatar = eredmeny.getString("korhatar");
                varos = eredmeny.getString("varos");
                leiras = eredmeny.getString("leiras");
                kep = eredmeny.getString("image");

                String utvonal = url+kep;
                keplekerdezes(utvonal);

                jatekok.add(new Jatek(jateknev,kategoria,korhatar,varos,leiras,bitmapkep));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jatekok;
    }

    public static ArrayList<Jatek> jatekListaKorhatar(String korhatarszuro) {

        ArrayList<Jatek> jatekok = new ArrayList<Jatek>();

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,image from jatekok3 where korhatar ="+korhatarszuro);

            while (eredmeny.next()) {

                String jateknev;
                String kategoria;
                String korhatar;
                String varos;
                String leiras;
                String kep;
                jateknev = eredmeny.getString("jateknev");
                kategoria = eredmeny.getString("kategoria");
                korhatar = eredmeny.getString("korhatar");
                varos = eredmeny.getString("varos");
                leiras = eredmeny.getString("leiras");
                kep = eredmeny.getString("image");

                String utvonal = url+kep;
                keplekerdezes(utvonal);

                jatekok.add(new Jatek(jateknev,kategoria,korhatar,varos,leiras,bitmapkep));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jatekok;
    }

    public static ArrayList<Jatek> jatekListaKategoria(String kategoriaszuro) {

        ArrayList<Jatek> jatekok = new ArrayList<Jatek>();

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,image from jatekok3 where kategoria ='"+ kategoriaszuro +"'");

            while (eredmeny.next()) {

                String jateknev;
                String kategoria;
                String korhatar;
                String varos;
                String leiras;
                String kep;
                jateknev = eredmeny.getString("jateknev");
                kategoria = eredmeny.getString("kategoria");
                korhatar = eredmeny.getString("korhatar");
                varos = eredmeny.getString("varos");
                leiras = eredmeny.getString("leiras");
                kep = eredmeny.getString("image");

                String utvonal = url+kep;
                keplekerdezes(utvonal);

                jatekok.add(new Jatek(jateknev,kategoria,korhatar,varos,leiras,bitmapkep));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jatekok;
    }


}
