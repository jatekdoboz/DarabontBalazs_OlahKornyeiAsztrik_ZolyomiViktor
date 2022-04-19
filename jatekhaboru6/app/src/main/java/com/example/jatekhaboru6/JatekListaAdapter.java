package com.example.jatekhaboru6;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JatekListaAdapter extends
        RecyclerView.Adapter<JatekListaAdapter.ViewHolder> {

    public String telefon;
    public String email;
    public static TextView jatekneve;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
        public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView jateknev;
        public TextView kategoria;
        public TextView korhatar;
        public TextView varos;
        public TextView leiras;
        public ImageView kephelye;



        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            jateknev = (TextView) itemView.findViewById(R.id.jateknev);
            kategoria = (TextView) itemView.findViewById(R.id.kategori);
            korhatar = (TextView) itemView.findViewById(R.id.korhata);
            varos = (TextView) itemView.findViewById(R.id.varo);
            leiras = (TextView) itemView.findViewById(R.id.leira);
            kephelye = (ImageView) itemView.findViewById(R.id.kepecske);
        }
    }

    private List<Jatek> jatekLista;

    // Pass in the contact array into the constructor
    public JatekListaAdapter(List<Jatek> jatekok) {
        jatekLista = jatekok;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.lista_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Jatek jatek = jatekLista.get(position);


        // Set item views based on your views and data model
        TextView textView = holder.jateknev;
        textView.setText(jatek.getName());

        TextView textView2 = holder.kategoria;
        textView2.setText(jatek.getKategoriaja());

        TextView textView3 = holder.korhatar;
        textView3.setText(jatek.getKorhatara());

        TextView textView4 = holder.varos;
        textView4.setText(jatek.getVarosa());

        TextView textView5 = holder.leiras;
        textView5.setText(jatek.getLeirasa());

        ImageView imageView = holder.kephelye;
        imageView.setImageBitmap(jatek.getKepe());

    }




    @Override
    public int getItemCount() {
        return jatekLista.size();
    }


}
