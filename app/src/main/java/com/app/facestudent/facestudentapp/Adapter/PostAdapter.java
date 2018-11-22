package com.app.facestudent.facestudentapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.Model.Habilidade;
import com.app.facestudent.facestudentapp.Model.Post;
import com.app.facestudent.facestudentapp.R;

import java.util.Date;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private List<Post> lista;
    private LayoutInflater layoutInflater;

    public PostAdapter(Context context, List<Post> lista) {
        this.context = context;
        this.lista = lista;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView titulo, texto, data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.tv_data_post_adapter);
            titulo = itemView.findViewById(R.id.tv_titulo_post_adapter);
            texto = itemView.findViewById(R.id.tv_texto_post_adapter);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.adapter_post, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Post post = lista.get(i);

        viewHolder.titulo.setText(post.getTitulo());
        viewHolder.texto.setText(post.getTexto());
        viewHolder.data.setText(DateFormat.format("dd/MM/yy", new Date(post.getData())).toString());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

}
