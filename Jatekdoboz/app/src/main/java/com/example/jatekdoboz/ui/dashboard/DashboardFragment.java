package com.example.jatekdoboz.ui.dashboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.jatekdoboz.BevittAdat;
import com.example.jatekdoboz.ListaAdapter2;
import com.example.jatekdoboz.R;
import com.example.jatekdoboz.SqlKapcsolat;
import com.example.jatekdoboz.databinding.FragmentDashboardBinding;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private ConstraintLayout sikertelen;
    private ConstraintLayout betoltes;
    private FrameLayout listakeret;
    private ImageView pontokmaszk;
    private Animation animacio;
    private ImageView logo;
    private CardView hozzaadas;
    private AlertDialog.Builder dbuilder;
    private AlertDialog dialog;
    private ListView lista;
    private TextView melyikhir;
    private String azahir;

    public boolean vankapcsolat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        sikertelen = getView().findViewById(R.id.sikertelen2);
        listakeret = getView().findViewById(R.id.uzenolistakeret);
        betoltes = getView().findViewById(R.id.betoltes);
        pontokmaszk = getView().findViewById(R.id.pontokmaszk);
        animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
        logo = getView().findViewById(R.id.hireklogo);

        hozzaadas = getView().findViewById(R.id.hozzzaadas);

        lista = (ListView) getView().findViewById(R.id.uzenolista);

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

        hozzaadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                felugroAblak();
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                melyikhir = (TextView) view.findViewById(R.id.hircim);
                azahir = melyikhir.getText().toString();

                felugroAblakSzerk();

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

            List<Map<String, String>> HirLista = null;
            //HirKilistazas hirek = new HirKilistazas();

            //HirLista = hirek.hirLekerdezes();

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


        ArrayList<BevittAdat> hirek = new ArrayList<BevittAdat>();
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

                hirek.add(new BevittAdat(hircim, datum, hir));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        lista.setAdapter(adapter2);


    }

    private void felugroAblak(){

        AlertDialog.Builder dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugrohirek , null);
        felugroablakKeret.setMinimumHeight(600);

        TextView mentes = (TextView) felugroablakKeret.findViewById(R.id.mentes);
        TextView megse = (TextView) felugroablakKeret.findViewById(R.id.megse);

        EditText hozzahircim = (EditText) felugroablakKeret.findViewById(R.id.hozzahircim);
        EditText hozzadatum = (EditText) felugroablakKeret.findViewById(R.id.hozzadatum);
        EditText hozzahir = (EditText) felugroablakKeret.findViewById(R.id.hozzahir);


        mentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hircim = hozzahircim.getText().toString();
                String datum = hozzadatum.getText().toString();
                String hir = hozzahir.getText().toString();

                SqlKapcsolat abkapcs = new SqlKapcsolat();

                Connection kapcsolat = abkapcs.kapcsolatclass();

                Statement parancs = null;
                try {

                    parancs = kapcsolat.createStatement();

                    parancs.execute("USE u121374417_jatekdoboz");
                    parancs.execute("INSERT INTO hirek(hircim,datum,hir) VALUES ('"+hircim+"','"+datum+"','"+hir+"');");


                    dialog.dismiss();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Toast.makeText(getContext(),"Sikertelen mentes", Toast.LENGTH_SHORT).show();

                }

                try {
                    hirKilistazas();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        megse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dbuilder.setView(felugroablakKeret);
        dialog = dbuilder.create();
        dialog.show();

    }

    private void felugroAblakSzerk(){

        AlertDialog.Builder dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugrohirekszerk , null);
        felugroablakKeret.setMinimumHeight(600);

        TextView mentes = (TextView) felugroablakKeret.findViewById(R.id.mentes);
        TextView torles = (TextView) felugroablakKeret.findViewById(R.id.torles);
        TextView megse = (TextView) felugroablakKeret.findViewById(R.id.megse);

        EditText hozzahircim = (EditText) felugroablakKeret.findViewById(R.id.hozzahircim);
        EditText hozzadatum = (EditText) felugroablakKeret.findViewById(R.id.hozzadatum);
        EditText hozzahir = (EditText) felugroablakKeret.findViewById(R.id.hozzahir);

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        Statement parancs = null;
        try {

            parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("SELECT hircim,datum,hir FROM hirek WHERE hircim='"+azahir+"';");

            while (eredmeny.next()) {
                hozzahircim.setText(eredmeny.getString("hircim"));
                hozzadatum.setText(eredmeny.getString("datum"));
                hozzahir.setText(eredmeny.getString("hir"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Toast.makeText(getContext(),"Sikertelen mentes", Toast.LENGTH_SHORT).show();

        }


        mentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hircim = hozzahircim.getText().toString();
                String datum = hozzadatum.getText().toString();
                String hir = hozzahir.getText().toString();

                SqlKapcsolat abkapcs = new SqlKapcsolat();

                Connection kapcsolat = abkapcs.kapcsolatclass();

                Statement parancs = null;
                try {

                    parancs = kapcsolat.createStatement();

                    parancs.execute("USE u121374417_jatekdoboz");
                    parancs.execute("UPDATE hirek SET hircim='"+hircim+"',datum='"+datum+"',hir='"+hir+"' WHERE hircim='"+azahir+"';");


                    dialog.dismiss();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Toast.makeText(getContext(),"Sikertelen mentes", Toast.LENGTH_SHORT).show();

                }

                try {
                    hirKilistazas();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        torles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SqlKapcsolat abkapcs = new SqlKapcsolat();

                Connection kapcsolat = abkapcs.kapcsolatclass();

                Statement parancs = null;
                try {

                    parancs = kapcsolat.createStatement();

                    parancs.execute("USE u121374417_jatekdoboz");
                    parancs.execute("DELETE FROM hirek WHERE hircim='"+azahir+"';");


                    dialog.dismiss();
                    hirKilistazas();

                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                    Toast.makeText(getContext(),"Sikertelen torles", Toast.LENGTH_SHORT).show();

                }
            }
        });

        megse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dbuilder.setView(felugroablakKeret);
        dialog = dbuilder.create();
        dialog.show();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}