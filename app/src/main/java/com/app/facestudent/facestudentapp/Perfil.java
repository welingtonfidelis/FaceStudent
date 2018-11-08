package com.app.facestudent.facestudentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.Model.Habilidade;

import java.util.List;

public class Perfil extends AppCompatActivity {
    private ImageView foto;
    private TextView nome, data_nascimento;
    private ListView lista_habilidade;
    private RecyclerView lista_post;
    private List<Habilidade> habilidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        foto = findViewById(R.id.imgv_foto_perfil);
        nome = findViewById(R.id.tv_nome_perfil);
        data_nascimento = findViewById(R.id.tv_nascimento_perfil);
        lista_habilidade = findViewById(R.id.list_habilidade_perfil);
        lista_post = findViewById(R.id.recycler_post_perfil);

    }
}
