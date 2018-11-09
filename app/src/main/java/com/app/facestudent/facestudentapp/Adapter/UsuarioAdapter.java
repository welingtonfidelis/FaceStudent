package com.app.facestudent.facestudentapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.Model.Usuario;
import com.app.facestudent.facestudentapp.Perfil;
import com.app.facestudent.facestudentapp.R;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {
    private Context context;
    private List<Usuario> lista;
    private LayoutInflater layoutInflater;

    public UsuarioAdapter(Context context, List<Usuario> lista) {
        this.context = context;
        this.lista = lista;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView nome_usuario;
        protected ImageView foto_usuario;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_usuario = itemView.findViewById(R.id.tv_nome_usuario_adater);
            foto_usuario = itemView.findViewById(R.id.imgv_foto_usuario);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = layoutInflater.inflate(R.layout.adapter_usuario, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Usuario usuario = lista.get(i);

        viewHolder.nome_usuario.setText(usuario.getNome());

        new DownloadImageTask(viewHolder.foto_usuario).execute(usuario.getFoto());

        viewHolder.foto_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Intent it = new Intent(context, Perfil.class);
                it.putExtra("USUARIO", gson.toJson(usuario));
                context.startActivity(it);
            }
        });
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}