package com.example.jatekhaboru6;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Jatekok5 extends Fragment {

    private Jatekok5ViewModel mViewModel;

    private ImageView pontokmaszk;
    private Animation animacio;
    private boolean vankapcsolat;

    private ConstraintLayout sikertelen;
    private ConstraintLayout betoltes;
    private ConstraintLayout sikertelenkategoria;
    private ConstraintLayout sikertelenvaros;
    private ConstraintLayout sikertelenkorosztaly;
    private LinearLayout jatekListaKeret;

    private RecyclerView lista;

    private String telefon;
    private String email;
    private String nagykepnev;
    private static Bitmap nagykep;
    private TextView jatekneve;
    private TextView telefonszam;
    private TextView emailcim;
    private ImageView nagykepkeret;

    private String korosztalyszuro;

    //public static Context context;

    private Spinner szuro;
    private final String[] szuroelemek= {"??j->R??gi", "R??gi->??j", "Koroszt??ly","Kateg??ria","V??ros","Vissza"};

    private AlertDialog.Builder dbuilder;
    private AlertDialog.Builder dbuilder2;
    private AlertDialog dialog;
    private AlertDialog dialog2;

    public Jatekok5 newInstance() {
        return new Jatekok5();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jatekok5_fragment, container, false);

    }

    ArrayList<Jatek> jatekLista;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //eroforrasok megadasa, hogy semmi ne legyen null object
        pontokmaszk = getView().findViewById(R.id.pontokmaszk);
        animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
        sikertelen = getView().findViewById(R.id.sikertelen);
        sikertelenkategoria = getView().findViewById(R.id.sikertelenkategoriaszuro);
        sikertelenkorosztaly = getView().findViewById(R.id.sikertelenkorhatarszuro);
        sikertelenvaros = getView().findViewById(R.id.sikertelenvarosszuro);
        betoltes = getView().findViewById(R.id.betoltes);
        lista = (RecyclerView) getView().findViewById(R.id.recyclerView);
        jatekListaKeret = (LinearLayout) getView().findViewById(R.id.jatekListaKeret);

        szuro = getView().findViewById(R.id.szuro);

        //layoutok kezelese, melyik latszodjon es melyik nem
        sikertelen.setVisibility(View.INVISIBLE);
        sikertelenvaros.setVisibility(View.INVISIBLE);
        sikertelenkorosztaly.setVisibility(View.INVISIBLE);
        sikertelenkategoria.setVisibility(View.INVISIBLE);
        betoltes.setVisibility(View.VISIBLE);


        //context = getContext();

        //ListaSzal listaSzal = new ListaSzal();
        //Thread szal = new Thread(listaSzal);
        //szal.start();

        //kapcsolatellenorzos igazabol nem adatbetoltes
        //kulon threaden gyorsabb es lehet alatta animaciot lejatszani
        Adatbetoltes adatbetoltes = new Adatbetoltes();
        Thread thread2 = new Thread(adatbetoltes);
        thread2.start();

        //maga az animacio szinten kulon szalon
        //ezt nem kellett volna kulon szalon de az onResume-ban lefutatott programreszt kesleltetve lejatszodik
        //egy kicsit tobb ido mire betolt, mert tenylegesen az animacio vegen kezd csatlakozni a program
        //es keri le az adatokat

        //nincs mar ra szukseg a spinneren belul lejatszodika az animacio

        //Animacio anim = new Animacio();
        //Thread thread = new Thread(anim);
        //thread.start();


        //mivel a recyclerview/nak nincs onItemClick metodusa, egy egyedi class es egy oniTemTouchListenerrel
        lista.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), lista ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        //a klikkelt jatek meghatarozasa
                        jatekneve = (TextView) view.findViewById(R.id.jateknev);
                        String hol = jatekneve.getText().toString();

                        //masodik behatarolasi adat (azert kell, hogy ha esetleg ugyan olyan neven van jatek, vagy egy felhasznalo tobb jatekot tolt fel, meg tudja kulonboztetni a program)
                        TextView kategoriaja = (TextView) view.findViewById(R.id.kategori);
                        String hol2 = kategoriaja.getText().toString();

                        //harmadik
                        TextView varosa = (TextView) view.findViewById(R.id.varo);
                        String hol3 = varosa.getText().toString();

                        SqlKapcsolat abkapcs = new SqlKapcsolat();

                        Connection kapcsolat = abkapcs.kapcsolatclass();

                        try {
                            Statement parancs = kapcsolat.createStatement();

                            //az adott kriteriumoknak megfelelo sor lekerdezese
                            parancs.execute("USE u121374417_jatekdoboz");
                            ResultSet eredmeny = parancs.executeQuery("select telefon,email,image from jatekok3 where jateknev='"+hol+"' AND kategoria='"+hol2+"' AND varos='"+hol3+"'");

                            while (eredmeny.next()) {

                                telefon = eredmeny.getString("telefon");
                                email = eredmeny.getString("email");
                                nagykepnev = eredmeny.getString("image");

                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        //kep lekerdezese
                        try {
                            keplekerdezes("https://jatekdoboz.xyz/image/"+nagykepnev);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        felugroAblak();


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        // a szuro elemre valo kattintas metodusa
        szuro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String szures = adapterView.getItemAtPosition(i).toString();


                if (szures.equals("R??gi->??j")){

                    final Handler kezelo = new Handler(Looper.myLooper());

                    kezelo.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //animacio lejatszasa a betoltes elott
                            betoltes.setVisibility(View.VISIBLE);
                            Animacio anim = new Animacio();
                            Thread thread = new Thread(anim);
                            thread.start();


                            jatekListaKeret.setVisibility(View.INVISIBLE);
                                }
                            },1);

                    //az animacio lejatszasa utan kesleltetve lekerdezi a listat
                    //ahogy elindul a lekerdezes leall az animacio
                    //ha viszont a lekerdezes fut kulon szalon akkor nem tolti be megfeleloen
                    //az adatokat
                    kezelo.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            betoltes.setVisibility(View.INVISIBLE);
                            jatekListaKeret.setVisibility(View.VISIBLE);

                            sikertelenkorosztaly.setVisibility(View.INVISIBLE);
                            sikertelenkategoria.setVisibility(View.INVISIBLE);
                            sikertelenvaros.setVisibility(View.INVISIBLE);
                            jatekListaKeret.setVisibility(View.VISIBLE);

                            datumFel();

                        }
                    },1000);


                }else if (szures.equals("??j->R??gi")){
                    final Handler kezelo = new Handler(Looper.myLooper());

                    kezelo.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            betoltes.setVisibility(View.VISIBLE);
                            Animacio anim = new Animacio();
                            Thread thread = new Thread(anim);
                            thread.start();


                            jatekListaKeret.setVisibility(View.INVISIBLE);
                        }
                    },1);

                    kezelo.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            betoltes.setVisibility(View.INVISIBLE);
                            jatekListaKeret.setVisibility(View.VISIBLE);

                            sikertelenkorosztaly.setVisibility(View.INVISIBLE);
                            sikertelenkategoria.setVisibility(View.INVISIBLE);
                            sikertelenvaros.setVisibility(View.INVISIBLE);
                            jatekListaKeret.setVisibility(View.VISIBLE);

                            datumLe();

                        }
                    },1000);


                }else if (szures.equals("Koroszt??ly")){

                    //felugro ablakot nyit meg
                    felugroAblakKorosztaly();

                    szuro.setSelection(5);

                }else if (szures.equals("V??ros")){
                    felugroAblakVaros();
                    szuro.setSelection(5);

                }else if (szures.equals("Kateg??ria")){

                    felugroAblakKategoria();
                    szuro.setSelection(5);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        RecyclerView lista = (RecyclerView) getView().findViewById(R.id.recyclerView);
        ArrayAdapter<String> szuroAdapter = new ArrayAdapter<>(getContext(), R.layout.szuromenu, szuroelemek);

        //keslelteteskezelo a benne levo runnable metodust valamennyi idovel kesleltetve hajtja vegre
        final Handler kezelo = new Handler(Looper.myLooper());
        kezelo.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (vankapcsolat==true) {

                    //jatekLista = Jatek.jatekLista();

                    //JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

                    //lista.setAdapter(adapter);

                    //lista.setLayoutManager(new LinearLayoutManager(getActivity()));

                    szuro.setAdapter(szuroAdapter);

                    sikertelen.setVisibility(View.INVISIBLE);
                    betoltes.setVisibility(View.INVISIBLE);
                }else{

                    sikertelen.setVisibility(View.VISIBLE);
                    betoltes.setVisibility(View.INVISIBLE);
                }
            }
        },1000);

    }

    @Override
    public void onResume() {
        super.onResume();

        //itt volt eredetileg a listalekerdezes, de ez nyilvan butasag
        //most ha kilepunk az alkalmazasbol, ott maradunk a listaban ahol voltunk

    }

    public class ListaSzal implements Runnable{

        @Override
        public void run() {
            jatekLista = Jatek.jatekLista();
        }
    }

    public class Animacio implements   Runnable {


        @Override
        public void run() {
            //egy a hatterrel megegyezo szinu elem kiuszik jobbra felfedve az alatta fekv[ pontokat
            pontokmaszk = getView().findViewById(R.id.pontokmaszk);
            animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
            pontokmaszk.startAnimation(animacio);
        }
    }

    //ellenorzi hogy van e kapcsolat, ez alapjan valaszt a program megfelelo layoutot
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

    //a kiszurt adatok lekerdezese a Jatek.java fajlban szereplo metodussal
    // es a reyclerview-hoz adas
    public void datumFel(){
        jatekLista = Jatek.jatekListaDatumFel();

        JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

        lista.setAdapter(adapter);

        lista.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void datumLe(){
        //itt egy sima datum asc/desc
        jatekLista = Jatek.jatekListaDatumLe();

        JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

        lista.setAdapter(adapter);

        lista.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    //itt viszont egy string tovabbadasaval tudjuk lekerdezni a kivant adatokat
    //ezt a stringet a user valasztja a felugroablakban a lista egy elemere kattintva
    public void korosztalyLista(String korosztalyszuro){

        sikertelenkorosztaly.setVisibility(View.INVISIBLE);
        sikertelenkategoria.setVisibility(View.INVISIBLE);
        sikertelenvaros.setVisibility(View.INVISIBLE);
        jatekListaKeret.setVisibility(View.VISIBLE);

        jatekLista = Jatek.jatekListaKorhatar(korosztalyszuro);

        JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

        lista.setAdapter(adapter);

        lista.setLayoutManager(new LinearLayoutManager(getActivity()));

        //amennyiben nincs eredmenye a keresesnek egy a szurohoz tartozo egyedi
        //uzenetet jelenit meg a program
        if (adapter.getItemCount() == 0){
            sikertelenkorosztaly.setVisibility(View.VISIBLE);
            jatekListaKeret.setVisibility(View.INVISIBLE);
        }
    }

    public void varosLista(String varosszuro){

        sikertelenkorosztaly.setVisibility(View.INVISIBLE);
        sikertelenkategoria.setVisibility(View.INVISIBLE);
        sikertelenvaros.setVisibility(View.INVISIBLE);
        jatekListaKeret.setVisibility(View.VISIBLE);

        jatekLista = Jatek.jatekListaVaros(varosszuro);

        JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

        lista.setAdapter(adapter);

        lista.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (adapter.getItemCount() == 0){
            sikertelenvaros.setVisibility(View.VISIBLE);
            jatekListaKeret.setVisibility(View.INVISIBLE);
        }
    }

    public void kategoriaLista(String kategoriaszuro){

        sikertelenkorosztaly.setVisibility(View.INVISIBLE);
        sikertelenkategoria.setVisibility(View.INVISIBLE);
        sikertelenvaros.setVisibility(View.INVISIBLE);
        jatekListaKeret.setVisibility(View.VISIBLE);

        jatekLista = Jatek.jatekListaKategoria(kategoriaszuro);

        System.out.println(jatekLista.size());

        JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

        lista.setAdapter(adapter);

        lista.setLayoutManager(new LinearLayoutManager(getActivity()));

        System.out.println(adapter.getItemCount());

        if (adapter.getItemCount() == 0){
            sikertelenkategoria.setVisibility(View.VISIBLE);
            jatekListaKeret.setVisibility(View.INVISIBLE);
        }
    }

    //listaelemre kattintva ez jeleneik meg
    private void felugroAblak(){

        dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugroablak , null);
        felugroablakKeret.setMinimumHeight(600);

        //eroforrasok megadasa
        telefonszam = (TextView) felugroablakKeret.findViewById(R.id.telefonfelugro);
        emailcim = (TextView) felugroablakKeret.findViewById(R.id.emailfelugro);
        nagykepkeret = (ImageView) felugroablakKeret.findViewById(R.id.nagykep);

        //kep nagyitasa huzassal
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(nagykepkeret);
        pAttacher.update();

        //kep beallitasa bitmapbol
        nagykepkeret.setImageBitmap(nagykep);

        // a telefonszam "gomb" onClick metodusa
        telefonszam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri u = Uri.parse("tel:06" + telefon);
                Intent i = new Intent(Intent.ACTION_DIAL, u);

                getActivity().startActivity(i);
            }
        });

        //"email cim"-re kattintva
        emailcim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                felugroAblakVisszavonas();
            }
        });



        dbuilder.setView(felugroablakKeret);
        dialog = dbuilder.create();
        dialog.show();

    }

    private void felugroAblakVisszavonas(){

        dbuilder2 = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugrovisszavonas , null);
        felugroablakKeret.setMinimumHeight(600);

        EditText emailcim = (EditText) felugroablakKeret.findViewById(R.id.emailellenorzes);
        AppCompatButton visszavonas = (AppCompatButton) felugroablakKeret.findViewById(R.id.visszavonas);
        AppCompatButton megse = (AppCompatButton) felugroablakKeret.findViewById(R.id.megse2);

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();


        visszavonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Statement parancs = null;

                if (emailcim.getText().toString().equals(email)) {
                    try {
                        parancs = kapcsolat.createStatement();
                        parancs.execute("USE u121374417_jatekdoboz");
                        parancs.execute("DELETE FROM jatekok3 where email='"+email+"'");
                        dialog2.dismiss();
                        dialog.dismiss();
                        datumLe();
                        Toast.makeText(getContext(), "Sikeres visszavon??s!", Toast.LENGTH_LONG).show();

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    //ha nem megfelelo az email cim
                }else {
                    Toast.makeText(getContext(), "Sikertelen visszavonas, ellenorizd az email cimet!", Toast.LENGTH_LONG).show();
                }
            }
        });

        megse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        dbuilder2.setView(felugroablakKeret);
        dialog2 = dbuilder2.create();
        dialog2.show();

    }

    private void felugroAblakKorosztaly(){

        dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugroablak2 , null);
        felugroablakKeret.setMinimumHeight(600);

        ListView korlista = (ListView) felugroablakKeret.findViewById(R.id.felugrolista);
        String[] korok = {"0","1","3","5","7","10","14"};
        final List<String> koradatok = new ArrayList<String>(Arrays.asList(korok));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.lenyilomenu3, koradatok);

        korlista.setAdapter(adapter);

        korlista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String kor = (String) korlista.getItemAtPosition(i);
                korosztalyszuro = kor;
                 korosztalyLista(korosztalyszuro);
                 dialog.dismiss();
            }
        });

        dbuilder.setView(felugroablakKeret);
        dialog = dbuilder.create();
        dialog.show();

    }

    private void felugroAblakVaros(){

        dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugroablak2 , null);
        felugroablakKeret.setMinimumHeight(600);

        ListView varoslista = (ListView) felugroablakKeret.findViewById(R.id.felugrolista);
        String[] varosok =  {"Abony","Ajka","Asz??d","B??csalm??s","Baja","Baktal??r??nth??za","Balassagyarmat","Balatonalm??di","Balatonbogl??r","Balatonf??ldv??r","Balatonf??red","Balatonlelle","Balmaz??jv??ros","Barcs","B??tasz??k","B??tonyterenye","Battonya","B??k??s","B??k??scsaba","Beretty????jfalu","Bicske","Biharkeresztes","B??ly","Bonyh??d","Buda??rs","Budapest","Cegl??d","Celld??m??lk","Csenger","Csepreg","Csongr??d","Csorna","Csurg??","Dabas","Debrecen","Derecske","Devecser","Domb??v??r","Dorog","Dunaf??ldv??r","Dunakeszi","Duna??jv??ros","Edel??ny","Eger","Elek","Encs","Enying","??rdPest","Esztergom","Feh??rgyarmat","Fels??zsolca","Fert??d","Fony??d","F??zesabony","G??rdony","G??d??ll??","Gy??l","Gyomaendr??d","Gy??ngy??s","Gy??r","Gyula","Hajd??b??sz??rm??ny","Hajd??dorog","Hajd??hadh??z","Hajd??n??n??s","Hajd??szoboszl??","Hatvan","Heves","H??v??z","H??dmez??v??s??rhely","Ibr??ny","Izs??k","J??noshalma","J??szap??ti","J??sz??roksz??ll??s","J??szber??ny","J??szf??nyszaru","Kalocsa","Kaposv??r","Kapuv??r","Karcag","Kazincbarcika","Kecel","Kecskem??t","Keszthely","Kisb??r","Kisk??r??s","Kiskunf??legyh??za","Kiskunhalas","Kiskunmajsa","Kistelek","Kis??jsz??ll??s","Kisv??rda","Kom??rom","Koml??","K??rmend","K??szeg","Kunhegyes","Kunszentm??rton","Kunszentmikl??s","Lajosmizse","Lengyelt??ti","Lenti","L??tav??rtes","Letenye","L??rinci","Mak??","Marcali","M??riap??cs","Martf??","M??t??szalka","Mez??ber??ny","Mez??cs??t","Mez??hegyes","Mez??k??vesd","Mez??kov??csh??za","Mez??t??r","Mindszent","Miskolc","Moh??cs","Monor","M??rFej??r","M??rahalom","Mosonmagyar??v??r","N??dudvar","Nagyat??d","Nagyecsed","Nagyhal??sz","Nagyk??ll??","Nagykanizsa","Nagyk??ta","Nagyk??r??s","Nagymaros","Nyerges??jfalu","Ny??radony","Ny??rb??tor","Ny??regyh??za","Orosh??za","Oroszl??ny","??zd","Paks","P??pa","P??szt??","P??cel","P??cs","P??csv??rad","P??terv??s??ra","Pilisv??r??sv??r","Polg??r","Polg??rdi","P??sp??klad??ny","Putnok","R??ckeve","R??ts??g","Saj??szentp??ter","Salg??tarj??n","S??rbog??rd","Sarkad","S??rospatak","S??rv??r","S??sd","S??toralja??jhely","Sellye","Si??fok","Sikl??s","Simontornya","Solt","Soltvadkert","Sopron","S??meg","Szabadsz??ll??s","SzarvasB??k??s","Sz??zhalombatta","Sz??cs??ny","Szeged","Szeghalom","Sz??kesfeh??rv??r","Szeksz??rd","Szendr??","Szentendre","Szentes","Szentgotth??rd","Szentl??rinc","Szerencs","Szigetszentmikl??s","Szigetv??r","Sziksz??","Szolnok","Szombathely","Tab","Tam??si","Tapolca","Tata","Tatab??nya","T??gl??s","Tiszaf??ldv??r","Tiszaf??red","Tiszak??cske","Tiszal??k","Tisza??jv??ros","Tiszavasv??ri","Tokaj","Tolna","T??tkoml??s","T??r??kszentmikl??s","T??rkeve","??jfeh??rt??","??jsz??sz","V??cPest","V??rpalota","V??s??rosnam??ny","Vasv??r","Veszpr??m","Z??hony","Zalaegerszeg","Zalakaros","Zalaszentgr??t","Zirc"};
        final List<String> varosarray = new ArrayList<String>(Arrays.asList(varosok));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.lenyilomenu3, varosarray);

        varoslista.setAdapter(adapter);

        varoslista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String varos = (String) varoslista.getItemAtPosition(i);

                varosLista(varos);
                dialog.dismiss();
            }
        });

        dbuilder.setView(felugroablakKeret);
        dialog = dbuilder.create();
        dialog.show();

    }

    private void felugroAblakKategoria(){

        dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugroablak2 , null);
        felugroablakKeret.setMinimumHeight(600);

        ListView kategorialista = (ListView) felugroablakKeret.findViewById(R.id.felugrolista);
        String[] kategoriak =  {"Akci??j??t??k","Babaj??t??k","Bark??csol??s","Elektronikus j??t??k","??p??t??kocka","Faj??t??k","F??rd??szobai j??t??k","Hangszer","??r??szerek","J??t??kaut??","J??t??kbaba","J??t??kfegyver","J??t??kfigura","J??t??kkonyha","Jelmez","K??nyv","Kreat??v","K??lt??ri/kerti j??t??k","Lego","Makett","Oktat??j??t??k","Pap??r??ru","Party","Pl??ss","Puzzle","Ruh??zat","Sport","T??rsasj??t??k","Zen??s"};
        final List<String> kategoriaarray = new ArrayList<String>(Arrays.asList(kategoriak));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.lenyilomenu3, kategoriaarray);

        kategorialista.setAdapter(adapter);

        kategorialista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String kategoria = (String) kategorialista.getItemAtPosition(i);

                kategoriaLista(kategoria);
                dialog.dismiss();
            }
        });

        dbuilder.setView(felugroablakKeret);
        dialog = dbuilder.create();
        dialog.show();

    }

    //a keplekerdezes metodust hasznalva egy url megadasaval kepet tudunk lekerdezn a szerverrol
    public static void keplekerdezes(String urlString) throws MalformedURLException, IOException {

        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        nagykep=bitmap;
    }
}