package com.app.facestudent.facestudentapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.Model.Area;
import com.app.facestudent.facestudentapp.Model.Habilidade;
import com.app.facestudent.facestudentapp.R;

import java.util.List;

public class AreaAdapterLista extends RecyclerView.Adapter<AreaAdapterLista.ViewHolder> {
    private Context context;
    private List<Area> lista;
    private List<Habilidade> habilidade_usuario;
    private LayoutInflater layoutInflater;

    public AreaAdapterLista(Context context, List<Area> lista, List<Habilidade> habilidades){
        this.context = context;
        this.lista = lista;
        this.habilidade_usuario = habilidades;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected CardView cardView;
        protected TextView nome_area;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_area );
            nome_area = itemView.findViewById(R.id.tv_nome_area_cadastro);
        }
    }


    @NonNull
    @Override
    public AreaAdapterLista.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.adapter_area_lista, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AreaAdapterLista.ViewHolder viewHolder, int i) {
        final Area area = lista.get(i);

        viewHolder.nome_area.setText(area.getNome());

        /*if(area.isAtivado()){
            viewHolder.cardView.setBackgroundColor(Color.GREEN);
        }
        else{
            viewHolder.cardView.setBackgroundColor(Color.GRAY);
        }*/

        if(!habilidade_usuario.isEmpty()){
            for(Habilidade h: habilidade_usuario){
                if(area.getNome().equals(h.getNome())){
                    area.setAtivado(true);
                }
            }
        }

        if(area.isAtivado()){
            viewHolder.cardView.setBackgroundColor(Color.GREEN);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                area.setAtivado(!area.isAtivado());
                if(area.isAtivado()) {
                    viewHolder.cardView.setBackgroundColor(Color.GREEN);
                }
                else{
                    viewHolder.cardView.setBackgroundColor(Color.WHITE);
                }
                Log.e("ATIVADO", area.getNome()+area.isAtivado());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
