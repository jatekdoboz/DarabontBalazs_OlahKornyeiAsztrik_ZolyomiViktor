package com.example.jatekhaboru6;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
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
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link jatekok2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    jatekok3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentContainer kontener1;
    private FragmentContainer kontener2;

    public jatekok3() {
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
    public static jatekok3 newInstance(String param1, String param2) {
        jatekok3 fragment = new jatekok3();
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
    private ListView lista;
    private RelativeLayout footer;

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

    private int firstVisibleItem, visibleItemCount,totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        kinezet = inflater.inflate(R.layout.fragment_jatekok3, null);
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
        jatekneve = getView().findViewById(R.id.jateknev);

        lista = getView().findViewById(R.id.lista);

        betoltesszoveg = getView().findViewById(R.id.keremvarjon);

        sikertelen.setVisibility(View.INVISIBLE);
        betoltes.setVisibility(View.VISIBLE);


        Animacio anim = new Animacio();
        Thread thread = new Thread(anim);
        thread.start();

        Adatbetoltes adatbetoltes = new Adatbetoltes();
        Thread thread2 = new Thread(adatbetoltes);
        thread2.start();

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



        lista.setOnScrollListener(new AbsListView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

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

    public void ListaLekerdezes2() throws IOException {


        String jateknev;
        String kategoria;
        String korhatar;
        String varos;
        String leiras;

        ArrayList<BevittAdat> jatekok = new ArrayList<BevittAdat>();
        ListaAdapter adapter2 = new ListaAdapter(jatekok);

        lista = (ListView) getView().findViewById(R.id.lista);

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE madar");
            ResultSet eredmeny = parancs.executeQuery("select jateknev,kategoria,korhatar,varos,leiras,kep from jatekok7");

            while (eredmeny.next()) {


                jateknev = eredmeny.getString("jateknev");
                kategoria = eredmeny.getString("kategoria");
                korhatar = eredmeny.getString("korhatar");
                varos = eredmeny.getString("varos");
                leiras = eredmeny.getString("leiras");
                kepnev = eredmeny.getString("kep");

                String utvonal = url+kepnev;

                keplekerdezes(utvonal);

                jatekok.add(new BevittAdat(jateknev, kategoria, korhatar, varos, leiras, kep));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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