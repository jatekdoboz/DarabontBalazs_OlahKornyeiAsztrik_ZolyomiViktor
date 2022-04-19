package com.example.jatekhaboru6;

import android.text.Layout;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kilistazas extends jatekok2{

    Connection kapcsolat;
    String kapcsolatEredmeny="";
    Boolean sikereskapcs = false;

    public List <Map<String,String>> listaLekerdezes() {

        List<Map<String,String>> adatok = null;
        List<Map<Blob,Blob>> kepek = null;
        adatok = new ArrayList<Map<String,String>>();
        kepek = new ArrayList<Map<Blob,Blob>>();

        try{

            SqlKapcsolat sqlkapcsolas = new SqlKapcsolat();
            kapcsolat = sqlkapcsolas.kapcsolatclass();

            if (!kapcsolat.equals(null)){


                String sqlparancs = "select * from jatekok3";

                Statement parancs = kapcsolat.createStatement();
                parancs.execute("use jatekdoboz1");

                ResultSet eredmeny = parancs.executeQuery(sqlparancs);

                while(eredmeny.next()){

                    Map<String,String> adat = new HashMap<String,String>();
                    adat.put("jateknev",eredmeny.getString("jateknev"));
                    adat.put("kategori",eredmeny.getString("kategoria"));
                    adat.put("korhata",eredmeny.getString("korhatar"));
                    adat.put("varo",eredmeny.getString("varos"));
                    adat.put("leira",eredmeny.getString("leiras"));

                    Map<Blob, Blob> kep = new HashMap<Blob,Blob>();
                    //kepek.put("kepek",eredmeny.getBlob("kepek"));

                    adatok.add(adat);
                    kepek.add(kep);
                }
                kapcsolatEredmeny = "Sikeres kapcsolodas";
                sikereskapcs = true;
                kapcsolat.close();
            }else{
                System.out.println("nincs kapcsolat");
                kapcsolatEredmeny = "Sikertelen kapcsolodas";

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return adatok;

    }

    public void kilistazo(){
        listaLekerdezes();
    }
}
