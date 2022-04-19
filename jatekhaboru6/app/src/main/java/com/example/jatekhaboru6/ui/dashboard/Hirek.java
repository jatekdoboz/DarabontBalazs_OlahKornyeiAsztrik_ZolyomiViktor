package com.example.jatekhaboru6.ui.dashboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.jatekhaboru6.HirKilistazas;
import com.example.jatekhaboru6.HirLista;
import com.example.jatekhaboru6.ListaAdapter2;
import com.example.jatekhaboru6.R;
import com.example.jatekhaboru6.SqlKapcsolat;
import com.example.jatekhaboru6.databinding.FragmentDashboardBinding;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hirek extends Fragment {

    private FragmentDashboardBinding binding;

    private ConstraintLayout sikertelen;
    private ConstraintLayout betoltes;
    private FrameLayout listakeret;
    private ImageView pontokmaszk;
    private Animation animacio;
    private ImageView logo;
    private ListView lista;

    public boolean vankapcsolat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        sikertelen = getView().findViewById(R.id.sikertelen2);
        listakeret = getView().findViewById(R.id.uzenolistakeret);
        betoltes = getView().findViewById(R.id.betoltes);
        pontokmaszk = getView().findViewById(R.id.pontokmaszk);
        animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
        logo = getView().findViewById(R.id.hireklogo);

        sikertelen.setVisibility(View.INVISIBLE);
        logo.setVisibility(View.INVISIBLE);
        betoltes.setVisibility(View.VISIBLE);


        Animacio anim = new Animacio();
        Thread thread = new Thread(anim);
        thread.start();

        Adatbetoltes adatbetoltes = new Adatbetoltes();
        Thread thread2 = new Thread(adatbetoltes);
        thread2.start();

        final Handler kezelo = new Handler(Looper.myLooper());
        kezelo.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(vankapcsolat==true) {
                    try {
                        hirKilistazas();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logo.setVisibility(View.VISIBLE);
                    listakeret.setVisibility(View.VISIBLE);
                    sikertelen.setVisibility(View.INVISIBLE);
                    betoltes.setVisibility(View.INVISIBLE);
                }else{
                    sikertelen.setVisibility(View.VISIBLE);
                    listakeret.setVisibility(View.INVISIBLE);
                    betoltes.setVisibility(View.INVISIBLE);
                }


            }
        },800);

    }

    public class Animacio implements   Runnable{


        @Override
        public void run() {
            pontokmaszk = getView().findViewById(R.id.pontokmaszk);
            animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
            pontokmaszk.startAnimation(animacio);
        }
    }

    public void onResume() {

        super.onResume();




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

    SimpleAdapter adapter;


        public void HirLekerdezes () {

            ListView listView = (ListView) getView().findViewById(R.id.uzenolista);

            List<Map<String, String>> HirLista;
            HirKilistazas hirek = new HirKilistazas();

            HirLista = hirek.hirLekerdezes();

            String[] innen2 = {"hir"};
            int[] ide2 = {R.id.hir};


            adapter = new SimpleAdapter(getContext(), HirLista, R.layout.uzenofal_layout, innen2, ide2);

            listView.setAdapter(adapter);

            //leiras.append("...");
        }

    public void hirKilistazas() throws IOException {


        String hircim;
        String datum;
        String hir;
        lista = (ListView) getView().findViewById(R.id.uzenolista);


        ArrayList<HirLista> hirek = new ArrayList<HirLista>();
        ListaAdapter2 adapter2 = new ListaAdapter2(hirek);

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        try {
            Statement parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("select hircim,datum,hir from hirek ORDER BY id DESC");

            while (eredmeny.next()) {


                hircim = eredmeny.getString("hircim");
                datum = eredmeny.getString("datum");
                hir = eredmeny.getString("hir");

                hirek.add(new HirLista(hircim, datum, hir));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        lista.setAdapter(adapter2);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}