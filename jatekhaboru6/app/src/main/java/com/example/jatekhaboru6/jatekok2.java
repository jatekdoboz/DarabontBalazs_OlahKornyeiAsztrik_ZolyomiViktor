package com.example.jatekhaboru6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link jatekok2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    jatekok2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentContainer kontener1;
    private FragmentContainer kontener2;

    public jatekok2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment jatekok2.
     */
    // TODO: Rename and change types and number of parameters
    public static jatekok2 newInstance(String param1, String param2) {
        jatekok2 fragment = new jatekok2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private View kinezet;
    private ConstraintLayout sikertelen;
    private ConstraintLayout betoltes;
    private FrameLayout listakeret;
    private TextView pontok;
    private ImageView pontokmaszk;
    private Animation animacio;

    private Bitmap kep;
    private Blob kepBlob;
    public InputStream input;
    public ByteArrayOutputStream outputstream;
    public ByteArrayInputStream inputstream;
    public byte[] bajt;

    private TextView betoltesszoveg;

    public boolean vankapcsolat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        kinezet = inflater.inflate(R.layout.fragment_jatekok2, null);
        return kinezet;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        sikertelen = getView().findViewById(R.id.sikertelen);
        listakeret = getView().findViewById(R.id.listakeret);
        betoltes = getView().findViewById(R.id.betoltes);
        pontok = getView().findViewById(R.id.pontok);
        pontokmaszk = getView().findViewById(R.id.pontokmaszk);
        animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);

        betoltesszoveg = getView().findViewById(R.id.keremvarjon);

        sikertelen.setVisibility(View.INVISIBLE);
        betoltes.setVisibility(View.VISIBLE);


        Animacio anim = new Animacio();
        Thread thread = new Thread(anim);
        thread.start();

        Adatbetoltes adatbetoltes = new Adatbetoltes();
        Thread thread2 = new Thread(adatbetoltes);
        thread2.start();


    }

    public class Animacio implements   Runnable{


        @Override
        public void run() {
            pontokmaszk = getView().findViewById(R.id.pontokmaszk);
            animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
            pontokmaszk.startAnimation(animacio);
        }
    }

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
                    ListaLekerdezes2();
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

    SimpleAdapter adapter;

    public void ListaLekerdezes(){

        ListView listView = (ListView) getView().findViewById(R.id.lista);
        sikertelen = (ConstraintLayout) getView().findViewById(R.id.sikertelen);
        listakeret = (FrameLayout) getView().findViewById(R.id.listakeret);

        List<Map<String,String>> AdatLista = null;
        Kilistazas Adatok = new Kilistazas();

        SqlKapcsolat abkapcs = new SqlKapcsolat();
        Connection kapcsolat = abkapcs.kapcsolatclass();

        AdatLista = Adatok.listaLekerdezes();

        String[] innen = {"jateknev","kategori","korhata","varo","leira"};
        int[] ide = {R.id.jateknev,R.id.kategori,R.id.korhata,R.id.varo,R.id.leira};


        adapter = new SimpleAdapter( jatekok2.this.getActivity(), AdatLista, R.layout.lista_layout, innen, ide);

        listView.setAdapter(adapter);

        //leiras.append("...");
    }

    public void ListaLekerdezes2() {


        String jateknev;
        String kategoria;
        String korhatar;
        String varos;
        String leiras;

        ArrayList<BevittAdat> feladatok = new ArrayList<BevittAdat>();
        ListaAdapter adapter2 = new ListaAdapter(feladatok);

        ListView listView = (ListView) getView().findViewById(R.id.lista);

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE madar");
            ResultSet eredmeny = parancs.executeQuery("select * from jatekok6");

            while (eredmeny.next()) {

                jateknev = eredmeny.getString("jateknev");
                kategoria = eredmeny.getString("kategoria");
                korhatar = eredmeny.getString("korhatar");
                varos = eredmeny.getString("varos");
                leiras = eredmeny.getString("leiras");
                kepBlob = eredmeny.getBlob("kepblob");

                input = kepBlob.getBinaryStream();
                input.close();
                byte[] bajt = new byte[1];
                int hossz = 0;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                while ((hossz = input.read(bajt)) != -1) {
                    output.write(bajt, 0, hossz);

                }
                output.close();
                byte[] byteArray = output.toByteArray();

                kep = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                feladatok.add(new BevittAdat(jateknev, kategoria, korhatar, varos, leiras, kep));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setAdapter(adapter2);

    }

    public void keplekerdezes(String urlString) throws MalformedURLException, IOException{
        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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