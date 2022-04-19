package com.example.jatekdoboz.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.jatekdoboz.R;
import com.example.jatekdoboz.SqlKapcsolat;
import com.example.jatekdoboz.databinding.FragmentHomeBinding;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class Kepfeltoltes extends Fragment {

    private FragmentHomeBinding binding;
    public EditText kepcim;
    public EditText leiras;
    public AutoCompleteTextView kategoria;
    public AutoCompleteTextView korhatar;
    public AutoCompleteTextView varos;
    public EditText telefon;
    public EditText email;
    public ScrollView gorgo;
    public View ablak;
    public Button feltoltes;
    public ImageView valasztokep;

    public String kepfajlnev;
    public static String kepnev;
    public static String kiterjesztes;
    public Uri fajlUri;

    public TextView kepfelirat;
    public TextView cimfelirat;
    public TextView kategoriafelirat;
    public TextView korhatarfelirat;
    public TextView varosfelirat;
    public TextView leirasfelirat;
    public TextView feltoltesfelirat;
    public TextView telefonfelirat;
    public TextView emailfelrirat;

    private static String fajlUtvonal;
    private static String igaziFajlUtvonal;

    Connection kapcsolat;
    String kapcsolatEredmeny="";
    Boolean sikereskapcs = false;

    //legordulo elemek megadasa
    public String[] eletkorok = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
    public String[] varosok = {"Abony","Ajka","Aszód","Bácsalmás","Baja","Baktalórántháza","Balassagyarmat","Balatonalmádi","Balatonboglár","Balatonföldvár","Balatonfüred","Balatonlelle","Balmazújváros","Barcs","Bátaszék","Bátonyterenye","Battonya","Békés","Békéscsaba","Berettyóújfalu","Bicske","Biharkeresztes","Bóly","Bonyhád","Budaörs","Budapest","Cegléd","Celldömölk","Csenger","Csepreg","Csongrád","Csorna","Csurgó","Dabas","Debrecen","Derecske","Devecser","Dombóvár","Dorog","Dunaföldvár","Dunakeszi","Dunaújváros","Edelény","Eger","Elek","Encs","Enying","ÉrdPest","Esztergom","Fehérgyarmat","Felsõzsolca","Fertõd","Fonyód","Füzesabony","Gárdony","Gödöllõ","Gyál","Gyomaendrõd","Gyöngyös","Gyõr","Gyula","Hajdúböszörmény","Hajdúdorog","Hajdúhadház","Hajdúnánás","Hajdúszoboszló","Hatvan","Heves","Hévíz","Hódmezõvásárhely","Ibrány","Izsák","Jánoshalma","Jászapáti","Jászárokszállás","Jászberény","Jászfényszaru","Kalocsa","Kaposvár","Kapuvár","Karcag","Kazincbarcika","Kecel","Kecskemét","Keszthely","Kisbér","Kiskõrös","Kiskunfélegyháza","Kiskunhalas","Kiskunmajsa","Kistelek","Kisújszállás","Kisvárda","Komárom","Komló","Körmend","Kõszeg","Kunhegyes","Kunszentmárton","Kunszentmiklós","Lajosmizse","Lengyeltóti","Lenti","Létavértes","Letenye","Lõrinci","Makó","Marcali","Máriapócs","Martfû","Mátészalka","Mezõberény","Mezõcsát","Mezõhegyes","Mezõkövesd","Mezõkovácsháza","Mezõtúr","Mindszent","Miskolc","Mohács","Monor","MórFejér","Mórahalom","Mosonmagyaróvár","Nádudvar","Nagyatád","Nagyecsed","Nagyhalász","Nagykálló","Nagykanizsa","Nagykáta","Nagykõrös","Nagymaros","Nyergesújfalu","Nyíradony","Nyírbátor","Nyíregyháza","Orosháza","Oroszlány","Ózd","Paks","Pápa","Pásztó","Pécel","Pécs","Pécsvárad","Pétervására","Pilisvörösvár","Polgár","Polgárdi","Püspökladány","Putnok","Ráckeve","Rétság","Sajószentpéter","Salgótarján","Sárbogárd","Sarkad","Sárospatak","Sárvár","Sásd","Sátoraljaújhely","Sellye","Siófok","Siklós","Simontornya","Solt","Soltvadkert","Sopron","Sümeg","Szabadszállás","SzarvasBékés","Százhalombatta","Szécsény","Szeged","Szeghalom","Székesfehérvár","Szekszárd","Szendrõ","Szentendre","Szentes","Szentgotthárd","Szentlõrinc","Szerencs","Szigetszentmiklós","Szigetvár","Szikszó","Szolnok","Szombathely","Tab","Tamási","Tapolca","Tata","Tatabánya","Téglás","Tiszaföldvár","Tiszafüred","Tiszakécske","Tiszalök","Tiszaújváros","Tiszavasvári","Tokaj","Tolna","Tótkomlós","Törökszentmiklós","Túrkeve","Újfehértó","Újszász","VácPest","Várpalota","Vásárosnamény","Vasvár","Veszprém","Záhony","Zalaegerszeg","Zalakaros","Zalaszentgrót","Zirc"};
    public String[] kategoriak = {"oktató","szabadtéri","plüss","építős","kisautó","babáknak","főzős","fürdős","zenés","társasjáték","színezős"};


    ArrayAdapter<String> koradapter;
    ArrayAdapter<String> varosadapter;
    ArrayAdapter<String> katadapter;

    public static String PACKAGE_NAME;
    boolean check = true;
    ProgressDialog progressDialog;
    String ImageName = "kepnev" ;
    String ImagePath = "utvonal" ;
    String ServerUploadPath ="https://jatekdoboz.xyz/feltoltes3.php" ;
    String ServerUploadPathKicsi ="https://jatekdoboz.xyz/feltoltesKicsi.php" ;
    private Bitmap bitmap;

    boolean joEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);

            }
        });



        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        //url = "https://192.168.43.152:80/hozzaad.php?add";

        gorgo = getView().findViewById(R.id.kepfeltoltogorgeto);
        ablak = getView().findViewById(R.id.ablak);

        feltoltes = getView().findViewById(R.id.feltoltesgomb);

        kepcim = getView().findViewById(R.id.cim);
        leiras = getView().findViewById(R.id.leiras);
        kategoria = getView().findViewById(R.id.kategoria);
        korhatar = getView().findViewById(R.id.korhatar);
        varos = getView().findViewById(R.id.helyseg);
        telefon = getView().findViewById(R.id.telefonszam);
        email = getView().findViewById(R.id.email);


        kepfelirat = getView().findViewById(R.id.kepfelirat);
        cimfelirat = getView().findViewById(R.id.cimfelirat);
        kategoriafelirat = getView().findViewById(R.id.kategoriafelirat);
        korhatarfelirat = getView().findViewById(R.id.korhatarfelirat);
        varosfelirat = getView().findViewById(R.id.helysegfelirat);
        leirasfelirat = getView().findViewById(R.id.leirasfelirat);
        feltoltesfelirat = getView().findViewById(R.id.feltoltesallapota);
        telefonfelirat = getView().findViewById(R.id.telefonszamfelirat);
        emailfelrirat = getView().findViewById(R.id.emailfelirat);

        valasztokep = getView().findViewById(R.id.valasztokep);


        koradapter = new ArrayAdapter<String>(getContext(),R.layout.lenyilomenu,eletkorok);
        korhatar.setAdapter(koradapter);

        varosadapter = new ArrayAdapter<String>(getContext(),R.layout.lenyilomenu2,varosok);
        varos.setAdapter(varosadapter);

        katadapter = new ArrayAdapter<String>(getContext(),R.layout.lenyilomenu2,kategoriak);
        kategoria.setAdapter(katadapter);

        PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();

        valasztokep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //galeria megnyitasa ha meg lett adva a hozzaferes (az registerForActivityResults metoduson belul is
                // megjelenik ez a ket sor mert ott a minden tovabbi rakattintast engedejezi ez pedig az elsot,
                // amikor megadjuk az engedejt)
                if (ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {

                    galeria.launch("image/*");
                    //Intent intent = new Intent();
                    //intent.setType("image/*");
                    //intent.setAction(Intent.ACTION_GET_CONTENT);
                    //startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    valasztokep.setForeground(null);

                } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                }else {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                    Toast.makeText(getContext(), "Add meg a hozzáférést a fotókhoz a beállításokban.", Toast.LENGTH_LONG).show();


                }

            }


        });


        leiras.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //if (leiras.getLayout().getLineCount() > 5){
               //     leiras.getText().delete(leiras.getText().length()-1,leiras.getText().length());
                //}
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        gorgo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                billentyuzetelrejt(view.getContext(),view);
                kepcim.clearFocus();
                kategoria.clearFocus();
                korhatar.clearFocus();
                varos.clearFocus();
                leiras.clearFocus();
                telefon.clearFocus();
                email.clearFocus();
                return false;
            }
        });

        feltoltes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kepFeltoltes();
                //uploadImage();

                feltoltesfelirat.setText(kepfajlnev);

                Futtat futtat = new Futtat();
                Thread thread = new Thread(futtat);
                thread.run();
            }

        });

    }

    public void Feltoltes() {

        valasztokep = getView().findViewById(R.id.valasztokep);
        Editable kepcime = kepcim.getText();
        Editable kategoriaja = kategoria.getText();
        Editable korhatara = korhatar.getText();
        Editable varosa = varos.getText();
        Editable leirasa = leiras.getText();
        TextView feltoltesallapota = getView().findViewById(R.id.feltoltesallapota);


        // ellenorzi hogy ki van e toltve minden mezo es van e kep

        kepfelirat.setText("Válassyon képet:");
        cimfelirat.setText("A játék neve:");
        kategoriafelirat.setText("Válasszon kategóriát:");
        korhatarfelirat.setText("Hány éves kortól:");
        varosfelirat.setText("Hol vehető át:");
        leirasfelirat.setText("Rövid leírás:");
        telefonfelirat.setText("Telefonszám:");
        emailfelrirat.setText("Email:");

        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorOnPrimary, typedValue, true);
        int color = typedValue.data;

        kepfelirat.setTextColor(color);
        cimfelirat.setTextColor(color);
        kategoriafelirat.setTextColor(color);
        korhatarfelirat.setTextColor(color);
        varosfelirat.setTextColor(color);
        leirasfelirat.setTextColor(color);
        telefonfelirat.setTextColor(color);
        emailfelrirat.setTextColor(color);

    if(telefon.getText().toString().trim().length() != 0){

        try {

            //SqlKapcsolat class hasznalata az adatbazis eleresehez
            SqlKapcsolat sqlkapcsolas = new SqlKapcsolat();
            kapcsolat = sqlkapcsolas.kapcsolatclass();

            if (kapcsolat != null) {
                System.out.println(fajlUtvonal);

                //adatbazis megadasa
                //uj adatok felvetele az adatbazisba
                Statement parancs = kapcsolat.createStatement();
                PreparedStatement ps = null;
                parancs.execute("use u121374417_jatekdoboz");

                //parancs.execute("insert into jatekok5 (jateknev,kategoria,korhatar,varos,leiras,kep)" +
                //       " VALUES (" + "'" + kepcime + "'" + "," + "'" + kategoriaja + "'" + "," + "'" + korhatara + "'" + "," + "'" + varosa + "'" + "," + "'" + leirasa + "'" + ", curdate())");

                ps = kapcsolat.prepareStatement("INSERT INTO jatekok3(jateknev,kategoria,korhatar,varos,leiras,telefon,email,image,datum)" + " VALUES (?,?,?,?,?,?,?,?,?);");
                ps.setString(1, String.valueOf(kepcim.getText()));
                ps.setString(2, String.valueOf(kategoria.getText()));
                ps.setString(3, String.valueOf(korhatar.getText()));
                ps.setString(4, String.valueOf(varos.getText()));
                ps.setString(5, String.valueOf(leiras.getText()));
                ps.setString(6, String.valueOf(telefon.getText()));
                ps.setString(7, String.valueOf(email.getText()));
                ps.setString(8, String.valueOf(kepfajlnev));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ps.setString(9, String.valueOf(LocalDate.now()));
                }
                ps.execute();

                kapcsolatEredmeny = "Sikeres kapcsolódás";
                sikereskapcs = true;
                kapcsolat.close();
                //a profil adatbazisahoz ad hozza egy pontot ha jatekfeltoltes tortent
                //Adatok.feltoltottjatekok += 1;

                //szovegek es a kep alaphelyzetbe allitasa
                kepcim.setText("");
                kategoria.setText("");
                korhatar.setText("");
                varos.setText("");
                leiras.setText("");
                email.setText("");
                telefon.setText("");

                valasztokep.setImageResource(R.drawable.kephozzaad_foreground);
                feltoltesallapota.setTextColor(getResources().getColor(R.color.lila));
                feltoltesallapota.setText("Sikeres feltöltés!");
                //a TextView-k szoveget is alaphelyzetbe kell allitani ha hibauzenet jelenik meg rajta

            } else {
                kapcsolatEredmeny = "Sikertelen kapcsolodas";

                feltoltesallapota.setText("Nincs sql kapcsolat");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            feltoltesallapota.setTextColor(Color.RED);


            feltoltesallapota.setText("Hibe az sql kodban");
        }

        if (bitmap != null) {
            KicsiKepFeltoltes();
        }

        final Handler kezelo = new Handler(Looper.myLooper());
        kezelo.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {
                    KepFeltoltes();
                }
            }
        }, 200);

    }else{

        telefon.setText("ezt ki kell tolteni mert int mezo");
    }

    }



    public class Futtat implements Runnable{

        @Override
        public void run() {
            Feltoltes();
        }
    }

    public void Feltoltes2(){


        Editable kepcime = kepcim.getText();
        Editable kategoriaja = kategoria.getText();
        Editable korhatara = korhatar.getText();
        Editable varosa = varos.getText();
        Editable leirasa = leiras.getText();
        TextView feltoltesallapota = getView().findViewById(R.id.feltoltesallapota);

        //SqlKapcsolat class hasznalata az adatbazis eleresehez
                        SqlKapcsolat sqlkapcsolas = new SqlKapcsolat();
                        kapcsolat = sqlkapcsolas.kapcsolatclass();
        try {
                        if (kapcsolat != null) {
                            System.out.println(fajlUtvonal);

                            //adatbazis megadasa
                            //uj adatok felvetele az adatbazisba
                            Statement parancs = kapcsolat.createStatement();
                            PreparedStatement ps = null;
                            parancs.execute("use madar");

                            //parancs.execute("insert into jatekok5 (jateknev,kategoria,korhatar,varos,leiras,kep)" +
                             //       " VALUES (" + "'" + kepcime + "'" + "," + "'" + kategoriaja + "'" + "," + "'" + korhatara + "'" + "," + "'" + varosa + "'" + "," + "'" + leirasa + "'" + ", curdate())");

                            ps = kapcsolat.prepareStatement("INSERT INTO jatekok6(jateknev,kategoria,korhatar,varos,leiras,image,telefonszam,email)"+" VALUES (?,?,?,?,?,?,?,?);");
                            ps.setString(1, String.valueOf(kepcim.getText()));
                            ps.setString(2, String.valueOf(kategoria.getText()));
                            ps.setString(3, String.valueOf(korhatar.getText()));
                            ps.setString(4, String.valueOf(varos.getText()));
                            ps.setString(5, String.valueOf(leiras.getText()));

                            BitmapDrawable drawable = (BitmapDrawable) valasztokep.getDrawable();
                            Bitmap bitmap = drawable.getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                            byte[] bitmapdata = bos.toByteArray();
                            ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);

                            kepFeltoltes();
                            ps.setString(6, kepfajlnev+"."+kiterjesztes);
                            ps.setString(7,"telefonszam proba");
                            ps.setString(8,"email proba");
                            ps.execute();

                            kapcsolatEredmeny = "Sikeres kapcsolódás";
                            sikereskapcs = true;
                            kapcsolat.close();
                            //a profil adatbazisahoz ad hozza egy pontot ha jatekfeltoltes tortent
                            //Adatok.feltoltottjatekok += 1;

                            //szovegek es a kep alaphelyzetbe allitasa
                            kepcim.setText("");
                            leiras.setText("");
                            varos.setText("");
                            korhatar.setText("");
                            kategoria.setText("");
                            valasztokep.setImageResource(R.drawable.kephozzaad_foreground);
                            feltoltesallapota.setTextColor(getResources().getColor(R.color.lila));
                            feltoltesallapota.setText("Sikeres feltöltés!");
                            //a TextView-k szoveget is alaphelyzetbe kell allitani ha hibauzenet jelenik meg rajta

                        } else {
                            kapcsolatEredmeny = "Sikertelen kapcsolodas";
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        feltoltesallapota.setTextColor(Color.RED);
                        feltoltesallapota.setText("Sikertelen feltöltés.(ntfs nem támogatott)");
                    }

    }

    //kivalasytott kep feldolgozasa
    ActivityResultLauncher<String> galeria = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                //a kep helyenek a megadasa (hogy hova tegye a valasztott kepet)
                @Override
                public void onActivityResult(Uri uri) {

                    valasztokep.setImageURI(uri);

                    BitmapDrawable draw = (BitmapDrawable) valasztokep.getDrawable();
                    bitmap = draw.getBitmap();

                    //valasztokep.setImageBitmap(bitmap);

                    fajlUri=uri;
                    fajlUtvonal = uri.getPath();
                    //mentes(Uri.parse(fajlUtvonal+kiterjesztes));

                    File fajl = new File(fajlUtvonal);
                    //kepfajlnev = fajl.getName();  --> nem igazi nev csak index
                    fajlNeve(uri);

                    ContentResolver cR = getContext().getContentResolver();
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    kiterjesztes = mime.getExtensionFromMimeType(cR.getType(uri));


                    kepnev = kepfajlnev+"."+kiterjesztes;

                    System.out.println(fajlUtvonal);

                    kepfelirat.setText(kepfajlnev+"."+kiterjesztes);
                }
            });




    //ellenorzi hogy meg van e adva az engedely a fajlok hozzaferesehez a telefonon
    //ha nem ujra promptolja az uzenetet, amig a "ne kerdezze tobbszor" opcio ki nincs valasztva
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                    //galeria megnyitasa
                    galeria.launch("image/*");
                    //az eredeti kep levetele (ez lehet nem szukseges) PROBA!
                    valasztokep.setForeground(null);

                }else if (!isGranted){

                    boolean showRationale = shouldShowRequestPermissionRationale( Manifest.permission.READ_EXTERNAL_STORAGE );

                    //megnyitja az alkalmazashoz tartozo beallitasokat a telefonon
                    if (!showRationale){
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromParts("package", PACKAGE_NAME, null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(getContext(), "Sajnos így nem tud képet feltolteni.", Toast.LENGTH_LONG).show();
                }
            });




    //billentyuzet elrejtese
    public void billentyuzetelrejt(Context context, View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void kepFeltoltes() {
        String path = igaziFajlUtvonal;
        try {
            //String uploadId = UUID.randomUUID().toString();
           // new MultipartUploadRequest(getActivity() , url)
                   // .addFileToUpload(path, "image")
                    //.addParameter("jateknev",kepcim.getText().toString())
                    //.addParameter("kategoria",kategoria.getText().toString())
                    //.addParameter("korhatar",korhatar.getText().toString())
                    //.addParameter("varos",varos.getText().toString())
                    //.addParameter("leiras","leirastesyt")
                    //.addParameter("telefon","telefonteszt")
                    //.addParameter("email","emailteszt")
                  //  .setNotificationConfig(new UploadNotificationConfig())
                   // .setMaxRetries(3)
                   // .startUpload();

        } catch (Exception ex) {


        }

    }
    public void mentes() throws IOException {
        FileOutputStream outStream = null;
        File appspecifikus = getContext().getFilesDir();
        File dir = new File(appspecifikus.getAbsolutePath());
        dir.mkdirs();
        String fileName = kepfajlnev+"."+kiterjesztes;
        File outFile = new File(dir, fileName);
        outStream = new FileOutputStream(outFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        igaziFajlUtvonal = dir+kepfajlnev;
        outStream.flush();
        outStream.close();
    }

    private void kepFeltoltes2(){
        URLConnection urlconnection = null;
        try {
            File file = new File(fajlUtvonal+"."+kiterjesztes);
            URL url = new URL("https://192.168.43.152:8080/hozzaad.php?add");
            urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setDoInput(true);

            if (urlconnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlconnection).setRequestMethod("POST");
                ((HttpURLConnection) urlconnection).connect();
            }

            BufferedOutputStream bos = new BufferedOutputStream(urlconnection.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int i;
            byte [] buffer = new byte[4096];
            // read byte by byte until end of stream
            while ((i = bis.read(buffer)) > 0) {
                bos.write(buffer,0,i);
            }
            bis.close();
            bos.close();
            System.out.println(((HttpURLConnection) urlconnection).getResponseMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            InputStream inputStream;
            int responseCode = ((HttpURLConnection) urlconnection).getResponseCode();
            if ((responseCode >= 200) && (responseCode <= 202)) {
                inputStream = ((HttpURLConnection) urlconnection).getInputStream();
                int j;
                while ((j = inputStream.read()) > 0) {
                    System.out.println(j);
                }

            } else {
                inputStream = ((HttpURLConnection) urlconnection).getErrorStream();
            }
            ((HttpURLConnection) urlconnection).disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //ezek a funkciok es classok kerultek vegul hasznalatra a kep feltoltesehez, egyenlore http cleantext-tel
    //xampp certificate nem sikerult, lehet menne ha a domain tanusitvanya megfelelo

    //kep feltoltese -> htttp post
    public void KepFeltoltes(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getContext(),"Feltoltes","Varjon",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ImageName,kepfajlnev);

                HashMapParams.put(ImagePath, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public void KicsiKepFeltoltes(){

        ByteArrayOutputStream byteArrayOutputStreamObjectKicsi ;

        byteArrayOutputStreamObjectKicsi = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 3, byteArrayOutputStreamObjectKicsi);

        byte[] byteArrayVarKicsi = byteArrayOutputStreamObjectKicsi.toByteArray();

        final String ConvertKicsiImage = Base64.encodeToString(byteArrayVarKicsi, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                //progressDialog = ProgressDialog.show(getContext(),"Feltoltes","Varjon",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                //progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                //Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapKicsiParams = new HashMap<String,String>();

                HashMapKicsiParams.put(ImageName,kepfajlnev);

                HashMapKicsiParams.put(ImagePath,ConvertKicsiImage);

                String FinalKicsiData = imageProcessClass.ImageHttpRequest(ServerUploadPathKicsi, HashMapKicsiParams);

                return FinalKicsiData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    //kep -> stringbuilder objekt -> string
    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }

    //a fajl valodi neve nem az androidos indexeles amit az uri-bol ki lehetne nyerni alapbol
    //erdekesseg, a kettospont miatt a kep neveben (pl.: image:12345.jpg) nem ertelmezi a php csak az image vegeig az utvonalat
    //de igy az eredeti fajlnevet tudjuk hasznalni
    private void fajlNeve(Uri uri) {
        Cursor returnCursor =
                    getContext().getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        kepfajlnev=name;
    }


}