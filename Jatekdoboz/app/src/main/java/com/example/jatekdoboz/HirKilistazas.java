package com.example.jatekdoboz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HirKilistazas {

    Connection kapcsolat;
    String kapcsolatEredmeny="";
    Boolean sikereskapcs = false;

    public List <Map<String,String>> hirLekerdezes() {

        List<Map<String,String>> adat = null;
        adat = new ArrayList<Map<String,String>>();

        try{

            SqlKapcsolat sqlkapcsolas = new SqlKapcsolat();
            kapcsolat = sqlkapcsolas.kapcsolatclass();

            if (kapcsolat != null){


                String sqlparancs = "select * from uzenofal";
                Statement parancs = kapcsolat.createStatement();
                parancs.execute("use madar");
                ResultSet eredmeny = parancs.executeQuery(sqlparancs);

                while(eredmeny.next()){

                    Map<String,String> adatok = new HashMap<String,String>();
                    adatok.put("hir",eredmeny.getString("hirek"));
                    //adatok.put("kepek",eredmeny.getBlob("kepek"));

                    adat.add(adatok);
                }
                kapcsolatEredmeny = "Sikeres kapcsolodas";
                sikereskapcs = true;
                kapcsolat.close();
            }else{
                kapcsolatEredmeny = "Sikertelen kapcsolodas";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return adat;
    }

}
