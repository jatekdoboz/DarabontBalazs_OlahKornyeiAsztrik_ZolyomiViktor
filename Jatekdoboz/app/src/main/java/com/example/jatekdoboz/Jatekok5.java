package com.example.jatekdoboz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView szerkesztes;
    private TextView torles;
    private ImageView nagykepkeret;

    private String korosztalyszuro;
    private int kivalasztottelem;

    //public static Context context;

    private Spinner szuro;
    private String[] szuroelemek= {"??j->R??gi", "R??gi->??j", "Koroszt??ly","Kateg??ria","V??ros","Vissza"};
    private boolean kivalasztva = false;

    private AlertDialog.Builder dbuilder;
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


        lista.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), lista ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        jatekneve = (TextView) view.findViewById(R.id.jateknev);
                        String hol = jatekneve.getText().toString();

                        TextView kategoriaja = (TextView) view.findViewById(R.id.kategori);
                        String hol2 = kategoriaja.getText().toString();

                        TextView varosa = (TextView) view.findViewById(R.id.varo);
                        String hol3 = varosa.getText().toString();

                        SqlKapcsolat abkapcs = new SqlKapcsolat();

                        Connection kapcsolat = abkapcs.kapcsolatclass();

                        final Handler kezelo = new Handler(Looper.myLooper());

                        kezelo.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                        try {
                            Statement parancs = kapcsolat.createStatement();

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

                        try {
                            keplekerdezes("https://jatekdoboz.xyz/image/"+nagykepnev);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        felugroAblak();

                            }
                        },200);


                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        szuro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String szures = adapterView.getItemAtPosition(i).toString();


                if (szures.equals("R??gi->??j")){

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

                            datumFel();

                        }
                    },960);


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

                            datumLe();

                        }
                    },960);


                }else if (szures.equals("Koroszt??ly")){

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
            pontokmaszk = getView().findViewById(R.id.pontokmaszk);
            animacio= AnimationUtils.loadAnimation(getContext(), R.anim.betoltes);
            pontokmaszk.startAnimation(animacio);
        }
    }

    //csak ellenorzi hogy van e kapcsolat nem tolt be semmit
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

    public void datumFel(){
        jatekLista = Jatek.jatekListaDatumFel();

        JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

        lista.setAdapter(adapter);

        lista.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void datumLe(){
        jatekLista = Jatek.jatekListaDatumLe();

        JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

        lista.setAdapter(adapter);

        lista.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void korosztalyLista(String korosztalyszuro){

        sikertelenkorosztaly.setVisibility(View.INVISIBLE);
        sikertelenkategoria.setVisibility(View.INVISIBLE);
        sikertelenvaros.setVisibility(View.INVISIBLE);
        jatekListaKeret.setVisibility(View.VISIBLE);

        jatekLista = Jatek.jatekListaKorhatar(korosztalyszuro);

        JatekListaAdapter adapter = new JatekListaAdapter(jatekLista);

        lista.setAdapter(adapter);

        lista.setLayoutManager(new LinearLayoutManager(getActivity()));

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

    private void felugroAblak(){

        dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugroablak , null);
        felugroablakKeret.setMinimumHeight(600);


        telefonszam = (TextView) felugroablakKeret.findViewById(R.id.telefonfelugro);
        emailcim = (TextView) felugroablakKeret.findViewById(R.id.emailfelugro);
        torles = (TextView) felugroablakKeret.findViewById(R.id.torol);
        szerkesztes = (TextView) felugroablakKeret.findViewById(R.id.szerkeszt);
        nagykepkeret = (ImageView) felugroablakKeret.findViewById(R.id.nagykep);

        //kep nagyitasa huzassal
        //github repo:
        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(nagykepkeret);
        pAttacher.update();

        nagykepkeret.setImageBitmap(nagykep);

        telefonszam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri u = Uri.parse("tel:06" + telefon);
                Intent i = new Intent(Intent.ACTION_DIAL, u);

                getActivity().startActivity(i);
            }
        });

        emailcim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("email", email);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getContext(),"Email m??solva a v??g??lapra.", Toast.LENGTH_SHORT).show();
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
                    parancs.execute("DELETE FROM jatekok3 WHERE telefon="+telefon);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                Toast.makeText(getContext(),"Poszt t??r??lve.", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                datumLe();
            }
        });

        szerkesztes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                felugroAblak2();
                dialog.dismiss();

            }
        });



        dbuilder.setView(felugroablakKeret);
        dialog = dbuilder.create();
        dialog.show();

    }

    private void felugroAblak2(){

        AlertDialog.Builder dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugroablakszerkeszt , null);
        felugroablakKeret.setMinimumHeight(600);

        TextView mentes = (TextView) felugroablakKeret.findViewById(R.id.mentes);
        TextView megse = (TextView) felugroablakKeret.findViewById(R.id.megse);

        EditText jateknev2 = (EditText) felugroablakKeret.findViewById(R.id.jateknevszerk);
        EditText kategoria2 = (EditText) felugroablakKeret.findViewById(R.id.kategoriaszerk);
        EditText korhatar2 = (EditText) felugroablakKeret.findViewById(R.id.korhatarszerk);
        EditText varos2 = (EditText) felugroablakKeret.findViewById(R.id.varosszerk);
        EditText leiras2 = (EditText) felugroablakKeret.findViewById(R.id.leirasszerk);

        SqlKapcsolat abkapcs = new SqlKapcsolat();

        Connection kapcsolat = abkapcs.kapcsolatclass();

        Statement parancs = null;
        try {

            parancs = kapcsolat.createStatement();

            parancs.execute("USE u121374417_jatekdoboz");
            ResultSet eredmeny = parancs.executeQuery("SELECT jateknev,kategoria,korhatar,varos,leiras from jatekok3 where telefon="+telefon);

            while (eredmeny.next()) {

                jateknev2.setText(eredmeny.getString("jateknev"));
                kategoria2.setText(eredmeny.getString("kategoria"));
                korhatar2.setText(eredmeny.getString("korhatar"));
                varos2.setText(eredmeny.getString("varos"));
                leiras2.setText(eredmeny.getString("leiras"));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        mentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SqlKapcsolat abkapcs = new SqlKapcsolat();

                Connection kapcsolat = abkapcs.kapcsolatclass();

                Statement parancs = null;
                try {

                    parancs = kapcsolat.createStatement();

                    parancs.execute("USE u121374417_jatekdoboz");
                    parancs.execute("UPDATE jatekok3 SET jateknev='"+jateknev2.getText().toString()+"',kategoria='"+kategoria2.getText()+"',korhatar='"+korhatar2.getText().toString()+"',varos='"+varos2.getText().toString()+"',leiras='"+leiras2.getText().toString()+"' WHERE telefon="+telefon);

                    dialog2.dismiss();
                    datumLe();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Toast.makeText(getContext(),"Sikertelen mentes", Toast.LENGTH_SHORT).show();

                }


            }
        });

        megse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });

        dbuilder.setView(felugroablakKeret);
        dialog2 = dbuilder.create();
        dialog2.show();

    }

    private void felugroAblakKorosztaly(){

        dbuilder = new AlertDialog.Builder(getContext());
        final View felugroablakKeret = getLayoutInflater().inflate(R.layout.felugroablak2 , null);
        felugroablakKeret.setMinimumHeight(600);

        ListView korlista = (ListView) felugroablakKeret.findViewById(R.id.felugrolista);
        String[] korok = {"1-3","4+","6+","9+","13+"};
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
        String[] kategoriak =  {"oktat??","szabadt??ri","pl??ss","??p??t??s","kisaut??","bab??knak","f??z??s","f??rd??s","zen??s","t??rsasj??t??k","sz??nez??s"};
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

    public static void keplekerdezes(String urlString) throws MalformedURLException, IOException {

        URL url = new URL(urlString);
        InputStream inputStream = url.openStream();
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        nagykep=bitmap;
    }
}