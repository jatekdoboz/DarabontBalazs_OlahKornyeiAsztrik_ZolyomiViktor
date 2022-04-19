package com.example.jatekhaboru6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListaAdapter extends BaseAdapter {

    private List<BevittAdat> jatekok;

    public ListaAdapter(List<BevittAdat> todos) {
        this.jatekok = todos;
    }

    @Override
    public int getCount() {
        return jatekok.size();
    }

    @Override
    public Object getItem(int position) {
        return jatekok.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BevittAdat megcsinalni = jatekok.get(position);

        LayoutInflater inf = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inf.inflate(R.layout.lista_layout, null);

        //ImageView imageViewIcon = itemView.findViewById(R.id.fontossag);

        TextView jateknev = itemView.findViewById(R.id.jateknev);
        jateknev.setText(megcsinalni.getJateknev());

        TextView kategoria = itemView.findViewById(R.id.kategori);
        kategoria.setText(megcsinalni.getKategoria());

        TextView korhatar = itemView.findViewById(R.id.korhata);
        korhatar.setText(megcsinalni.getKorhatar());

        TextView varos = itemView.findViewById(R.id.varo);
        varos.setText(megcsinalni.getVaros());

        TextView leiras = itemView.findViewById(R.id.leira);
        leiras.setText(megcsinalni.getLeiras());

        ImageView kep = itemView.findViewById(R.id.kepecske);
        kep.setImageBitmap(megcsinalni.getKep());



        return itemView;
    }
}
