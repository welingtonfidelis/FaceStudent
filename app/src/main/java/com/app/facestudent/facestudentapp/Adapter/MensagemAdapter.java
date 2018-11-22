package com.app.facestudent.facestudentapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Mensagem;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.app.facestudent.facestudentapp.Perfil;
import com.app.facestudent.facestudentapp.R;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

public class MensagemAdapter extends RecyclerView.Adapter<MensagemAdapter.ViewHolder> {
    private Context context;
    private List<Mensagem> lista;
    private LayoutInflater layoutInflater;

    public MensagemAdapter(Context context, List<Mensagem> lista) {
        this.context = context;
        this.lista = lista;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView data;
        protected EditText texto;
        protected ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            data = itemView.findViewById(R.id.tv_data_mensagem);
            texto = itemView.findViewById(R.id.texto_mensagem);
            layout = itemView.findViewById(R.id.layout_mensagem);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.adapter_mensagem, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Mensagem m = lista.get(i);

        viewHolder.data.setText(DateFormat.format("dd/MM/yy", new Date(m.getDataEnvio())).toString());
        viewHolder.texto.setText(m.getTexto());

        if(m.getIdDestinatario().equals(ReferencesHelper.getFirebaseAuth().getUid())){
            viewHolder.layout.setBackgroundColor(Color.parseColor("#FFFAFA"));
        }
        else{
            viewHolder.layout.setBackgroundColor(Color.parseColor("#98FB98"));
        }
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
}
