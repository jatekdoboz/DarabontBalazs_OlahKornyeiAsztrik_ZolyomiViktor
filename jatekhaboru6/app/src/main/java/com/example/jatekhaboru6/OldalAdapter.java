package com.example.jatekhaboru6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class OldalAdapter extends BaseAdapter {

    private List<Oldal> oldalak;

    public OldalAdapter(List<Oldal> todos) {
        this.oldalak = todos;
    }

    @Override
    public int getCount() {
        return oldalak.size();
    }

    @Override
    public Object getItem(int position) {
        return oldalak.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Oldal megcsinalni = oldalak.get(position);

        LayoutInflater inf = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inf.inflate(R.layout.oldalelemek, null);

        //ImageView imageViewIcon = itemView.findViewById(R.id.fontossag);

        TextView jateknev = itemView.findViewById(R.id.oldal);
        jateknev.setText(megcsinalni.getOldalak());



        return itemView;
    }
}
