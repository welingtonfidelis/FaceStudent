package com.app.facestudent.facestudentapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.ListaUsuario;
import com.app.facestudent.facestudentapp.Model.Area;
import com.app.facestudent.facestudentapp.R;

import java.util.List;

public class AreaAdapterGrid extends RecyclerView.Adapter<AreaAdapterGrid.ViewHolder>{
    private Context context;
    private List<Area> lista;
    private LayoutInflater layoutInflater;

    public AreaAdapterGrid(Context context, List<Area> lista) {
        this.context = context;
        this.lista = lista;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nome_area;
        protected CardView cardView_area;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_area = itemView.findViewById(R.id.nome_area_adapter);
            cardView_area = itemView.findViewById(R.id.cardview_area);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.adapter_area_grid, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Area area = lista.get(i);

        viewHolder.nome_area.setText(area.getNome());

        viewHolder.cardView_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListaUsuario.class);
                intent.putExtra("NOMEAREA", area.getNome());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


}
