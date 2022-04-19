package com.example.jatekhaboru6;

import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Jatekok4 extends Fragment {

    private Jatekok4ViewModel mViewModel;

    public static Jatekok4 newInstance() {
        return new Jatekok4();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jatekok4_fragment, container, false);
    }

    private View kinezet;
    private ConstraintLayout sikertelen;
    private ConstraintLayout betoltes;
    private FrameLayout listakeret;
    private TextView pontok;
    private ImageView pontokmaszk;
    private Animation animacio;
    private ListView lista;
    private ListView oldalak;
    private RecyclerView ujoldalak;

    private Bitmap kep;
    private Blob kepBlob;
    private String kepnev;
    public InputStream input;
    public ByteArrayOutputStream outputstream;
    public ByteArrayInputStream inputstream;
    public byte[] bajt;
    private String url = "http://192.168.43.152/kepek/";

    private TextView betoltesszoveg;

    public boolean vankapcsolat;

    public static TextView jatekneve;
    private String telefon;
    private String email;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        sikertelen = getView().findViewById(R.id.sikertelen);
        listakeret = getView().findViewById(R.id.listakeret);
        betoltes = getView().findViewById(R.id.betoltes);
        pontok = getView().findViewById(R.id.pontok);
        pontokmaszk = getView().findViewById(R.id.pontokmaszk);
        animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
        jatekneve = getView().findViewById(R.id.jateknev);

        lista = getView().findViewById(R.id.lista);
        oldalak = getView().findViewById(R.id.oldalak);

        betoltesszoveg = getView().findViewById(R.id.keremvarjon);

        sikertelen.setVisibility(View.INVISIBLE);
        betoltes.setVisibility(View.VISIBLE);


        Jatekok4.Animacio anim = new Jatekok4.Animacio();
        Thread thread = new Thread(anim);
        thread.start();

        Jatekok4.Adatbetoltes adatbetoltes = new Jatekok4.Adatbetoltes();
        Thread thread2 = new Thread(adatbetoltes);
        thread2.start();



        oldalak.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final Handler kezelo = new Handler(Looper.myLooper());


                Jatekok4.Animacio anim = new Jatekok4.Animacio();
                Thread thread = new Thread(anim);
                thread.start();

                        betoltes.setVisibility(View.VISIBLE);
                        listakeret.setVisibility(View.INVISIBLE);



                kezelo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        betoltes.setVisibility(View.INVISIBLE);
                        listakeret.setVisibility(View.VISIBLE);

                TextView oldal = (TextView) view.findViewById(R.id.oldal);
                String oldalszam = oldal.getText().toString();
                if (oldal.hasFocus()){
                    oldal.setBackgroundColor(Color.RED);
                }
                String jateknev;
                String kategoria;
                String korhatar;
                String varos;
                String leiras;

                SqlKapcsolat ujkapcsolat = new SqlKapcsolat();

                Connection kapcsolat = ujkapcsolat.kapcsolatclass();

                ArrayList<BevittAdat> feladatok = new ArrayList<BevittAdat>();
                ListaAdapter adapter2 = new ListaAdapter(feladatok);

                int oldalFent = Integer.valueOf(oldalszam)*10;
                int oldalLent = oldalFent-9;

                for (int szam = oldalLent;szam<=oldalFent;szam++) {
                    try {
                        Statement parancs = kapcsolat.createStatement();

                        parancs.execute("USE madar");
                        ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,kep from jatekok7 where id="+szam);

                        while (eredmeny.next()) {


                            jateknev = eredmeny.getString("jateknev");
                            kategoria = eredmeny.getString("kategoria");
                            korhatar = eredmeny.getString("korhatar");
                            varos = eredmeny.getString("varos");
                            leiras = eredmeny.getString("leiras");
                            kepnev = eredmeny.getString("kep");

                            String utvonal = url + kepnev;

                            keplekerdezes(utvonal);

                            feladatok.add(new BevittAdat(jateknev, kategoria, korhatar, varos, leiras, kep));


                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                lista.setAdapter(adapter2);
                betoltes.setVisibility(View.INVISIBLE);

                    }
                },200);

            }
        });

        //a listaban szereplo elemekre kattintva ezt vegzi el a program
        //popup menuben megjelenik a telefonszam es az emailcim
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

                jatekneve = (TextView) view.findViewById(R.id.jateknev);

                String hol = jatekneve.getText().toString();

                SqlKapcsolat abkapcs = new SqlKapcsolat();

                Connection kapcsolat = abkapcs.kapcsolatclass();

                try {
                    Statement parancs = kapcsolat.createStatement();

                    parancs.execute("USE madar");
                    ResultSet eredmeny = parancs.executeQuery("select telefonszam,email from jatekok6 where jateknev='"+hol+"'");

                    while (eredmeny.next()) {

                        telefon = eredmeny.getString("telefonszam");
                        email = eredmeny.getString("email");

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                View anchor;
                anchor = getView().findViewById(R.id.anchor_menu);
                PopupMenu popup = new PopupMenu(getContext(),anchor);
                //popup.getMenu().add(1, R.id.telefonszampopup, 1, telefon);
                //popup.getMenu().add(1, R.id.emailpopup, 2, email);
                popup.setForceShowIcon(true);
                popup.getMenuInflater().inflate(R.menu.popup, popup.getMenu());

                //
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem elem) {
                        if (elem.getTitle().equals("Telefonszam")) {

                            Uri u = Uri.parse("tel:" + telefon);
                            Intent i = new Intent(Intent.ACTION_DIAL, u);

                            getActivity().startActivity(i);
                        }
                        else if (elem.getTitle().equals("Email")) {

                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("email", email);
                            clipboard.setPrimaryClip(clip);

                            Toast.makeText(getContext(),"Email masolva a vagolapra", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });


    }

    public class Animacio implements   Runnable{


        @Override
        public void run() {
            pontokmaszk = getView().findViewById(R.id.pontokmaszk);
            animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
            pontokmaszk.startAnimation(animacio);
        }
    }

    // igazabol kapcsolatellenorzes...
    public class Adatbetoltes implements Runnable{

        @Override
        public void run() {
            SqlKapcsolat abkapcs = new SqlKapcsolat();
            Connection kapcsolat = abkapcs.kapcsolatclass();
            if(kapcsolat!=null) {
                vankapcsolat=true;
            }else{
                vankapcsolat=false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //kesleltetjuk a vegleges View megjeleneset addig amig a mellekszal ellenorzi hogy van e kapcsolat
        //animacio lejatszasa ezalatt lehetseges
        final Handler kezelo = new Handler(Looper.myLooper());
        kezelo.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(vankapcsolat==true) {
                    try {
                        ListaLekerdezes2();
                    }catch(Exception ex) {}

                    sikertelen.setVisibility(View.INVISIBLE);
                    betoltes.setVisibility(View.INVISIBLE);
                }else{
                    sikertelen.setVisibility(View.VISIBLE);
                    listakeret.setVisibility(View.INVISIBLE);
                    betoltes.setVisibility(View.INVISIBLE);
                }


            }
        },600);

    }

    public void ListaLekerdezes2() throws IOException, SQLException {


        String jateknev;
        String kategoria;
        String korhatar;
        String varos;
        String leiras;


        lista = (ListView) getView().findViewById(R.id.lista);
        oldalak = (ListView) getView().findViewById(R.id.oldalak);

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        //megnezzuk hany adat van regisztralva az sql tablaban
        int osszid = 0;
        Statement hanyid = kapcsolat.createStatement();
        hanyid.execute("USE madar");
        ResultSet ennyiid = hanyid.executeQuery("select COUNT(*) from jatekok7");
        ennyiid.next();
        osszid = ennyiid.getInt(1);

        System.out.println(osszid);

        //az osszadat alapjan kiszamoljuk a lapok szamat
        // felfele kerekitunk hogy minden adat megjelenjen, ellenkezo esetben egy lep hianyozna
        double teszt = (double) osszid/10;
        System.out.println(teszt);
        int lapokszama = (int )Math.ceil(teszt);
        System.out.println(lapokszama);

        ArrayList<Oldal> oldalszam = new ArrayList<Oldal>();
        ArrayList oldalakszama = new ArrayList<>();

        for (int i = 1; i<=lapokszama;i++){
            String oldalak = String.valueOf(i);
            oldalszam.add(new Oldal(oldalak));
            oldalakszama.add(oldalak);
        }

        OldalAdapter oldalAdapter = new OldalAdapter(oldalszam);

        int[] colors = {0, 0xFFFFFFFF, 0}; // red for the example
        oldalak.setDivider(new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors));
        oldalak.setDividerHeight(10);

        oldalak.setAdapter(oldalAdapter);


        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        //ujoldalak.setLayoutManager(linearLayoutManager);

        //Adapter adapter = new Adapter(getActivity(), oldalakszama);

        //ujoldalak.setAdapter(adapter);

        ArrayList<BevittAdat> feladatok = new ArrayList<BevittAdat>();
        ListaAdapter adapter2 = new ListaAdapter(feladatok);


        for (int szam = 1;szam<=10;szam++) {
            try {
                Statement parancs = kapcsolat.createStatement();

                parancs.execute("USE madar");
                ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,kep from jatekok7 where id="+szam);

                while (eredmeny.next()) {


                    jateknev = eredmeny.getString("jateknev");
                    kategoria = eredmeny.getString("kategoria");
                    korhatar = eredmeny.getString("korhatar");
                    varos = eredmeny.getString("varos");
                    leiras = eredmeny.getString("leiras");
                    kepnev = eredmeny.getString("kep");

                    String utvonal = url + kepnev;

                    keplekerdezes(utvonal);

                    feladatok.add(new BevittAdat(jateknev, kategoria, korhatar, varos, leiras, kep));


                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


        lista.setAdapter(adapter2);


    }





    public void keplekerdezes(String urlString) throws MalformedURLException, IOException{

        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        kep=bitmap;
    }

    //ez vegul nem kellett mert nem is lehetne igy modositani a layoutot
    // es egy xml-be kerult minden (betoltes, jatekok, sikertelenkapcs.) es a layoutok vannak megjelenitve es eltuntetve
    private void masikXml(int id){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        kinezet = inflater.inflate(id, null);
        ViewGroup rootView = (ViewGroup) getView();
        rootView.removeAllViews();
        rootView.addView(kinezet);
    }


}