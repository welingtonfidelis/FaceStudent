package com.app.facestudent.facestudentapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.Model.Area;
import com.app.facestudent.facestudentapp.Model.Habilidade;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.app.facestudent.facestudentapp.Perfil;
import com.app.facestudent.facestudentapp.R;
import com.google.gson.Gson;

import java.util.List;

public class HabilidadeAdapter extends RecyclerView.Adapter<HabilidadeAdapter.ViewHolder> {
    private Context context;
    private List<Habilidade> lista;
    private LayoutInflater layoutInflater;

    public HabilidadeAdapter(Context context, List<Habilidade> lista) {
        this.context = context;
        this.lista = lista;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.tv_nome_habilidade_adapter);
        }
    }

    @NonNull
    @Override
    public HabilidadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.adapter_habilidade, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HabilidadeAdapter.ViewHolder viewHolder, int i) {
        final Habilidade habilidade = lista.get(i);

        viewHolder.nome.setText(habilidade.getNome());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


}
