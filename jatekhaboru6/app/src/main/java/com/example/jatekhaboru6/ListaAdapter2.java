package com.example.jatekhaboru6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jatekhaboru6.HirLista;

import java.util.List;

public class ListaAdapter2 extends BaseAdapter{
    private List<HirLista> hirek;

    public ListaAdapter2(List<HirLista> todos) {
        this.hirek = todos;
    }

    @Override
    public int getCount() {
        return hirek.size();
    }

    @Override
    public Object getItem(int position) {
        return hirek.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HirLista megcsinalni = hirek.get(position);

        LayoutInflater inf = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inf.inflate(R.layout.uzenofal_layout, null);

        //ImageView imageViewIcon = itemView.findViewById(R.id.fontossag);

        TextView hircim = itemView.findViewById(R.id.hircim);
        hircim.setText(megcsinalni.getHircim());

        TextView hirdatum = itemView.findViewById(R.id.hirdatum);
        hirdatum.setText(megcsinalni.getDatum());

        TextView hir = itemView.findViewById(R.id.hir);
        hir.setText(megcsinalni.getHir());








        return itemView;
    }
}
