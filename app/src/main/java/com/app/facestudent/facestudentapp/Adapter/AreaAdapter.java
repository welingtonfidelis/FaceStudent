package com.app.facestudent.facestudentapp.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.Model.Area;
import com.app.facestudent.facestudentapp.R;

import java.util.List;

public class AreaAdapter extends BaseAdapter {
    private final List<Area> lista_area;
    private final Activity activity;

    public AreaAdapter(List<Area> lista_area, Activity activity) {
        this.lista_area = lista_area;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return lista_area.size();
    }

    @Override
    public Object getItem(int position) {
        return lista_area.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista_area.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_area, null);

        Area area = lista_area.get(position);

        TextView nome_area = view.findViewById(R.id.tv_nome_area_adapter);
        nome_area.setText(area.getNome());

        return view;
    }
}
