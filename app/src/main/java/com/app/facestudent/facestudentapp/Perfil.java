package com.app.facestudent.facestudentapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.facestudent.facestudentapp.Adapter.HabilidadeAdapter;
import com.app.facestudent.facestudentapp.Adapter.PostAdapter;
import com.app.facestudent.facestudentapp.Adapter.UsuarioAdapter;
import com.app.facestudent.facestudentapp.Helper.DownloadImageTask;
import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Habilidade;
import com.app.facestudent.facestudentapp.Model.Post;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity {
    private CircleImageView foto;
    private TextView nome;
    private RecyclerView listView_post, listView_habilidade;
    private List<Habilidade> lista_habilidade;
    private List<Post> lista_post;
    private Usuario usuario;
    private ValueEventListener habilidadeEventListener, postEventListiner, usuarioEventListener;
    private FloatingActionButton novoPost, novaMensagem, conversa;
    private FloatingActionButton editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Gson gson = new Gson();
        usuario = gson.fromJson(getIntent().getStringExtra("USUARIO"), Usuario.class);

        lista_habilidade = new ArrayList<Habilidade>();
        lista_post = new ArrayList<Post>();

        foto = (CircleImageView) findViewById(R.id.imgv_foto_perfil);
        nome = findViewById(R.id.tv_nome_perfil);
        listView_habilidade = findViewById(R.id.list_habilidade_perfil);
        listView_post = findViewById(R.id.recycler_post_perfil);
        novoPost = findViewById(R.id.fb_novo_post);
        editar = findViewById(R.id.fb_editar_usuario);
        novaMensagem = findViewById(R.id.fb_mensagem);
        conversa = findViewById(R.id.fb_conversa);
        final Button btn1 = (Button) findViewById(R.id.button1);

        if(!usuario.getId().equals(ReferencesHelper.getFirebaseAuth().getUid())){
            novoPost.setVisibility(View.GONE);
            editar.setVisibility(View.GONE);
            conversa.setVisibility(View.GONE);
        }

        if(usuario.getId().equals(ReferencesHelper.getFirebaseAuth().getUid())){
            novaMensagem.setVisibility(View.GONE);
            novaMensagem.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
        }

        new DownloadImageTask(foto).execute(usuario.getFoto());
        nome.setText(usuario.getNome());

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Intent it = new Intent(getBaseContext(), CadastroUsuario.class);
                it.putExtra("USUARIO", gson.toJson(usuario));
                startActivity(it);
            }
        });

        //carregando habilidades do usuario no recycler view
        habilidadeEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Habilidade h = postSnapshot.getValue(Habilidade.class);
                        lista_habilidade.add(h);
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Perfil.this);
                    listView_habilidade.setHasFixedSize(true);
                    listView_habilidade.setLayoutManager(layoutManager);

                    HabilidadeAdapter adapter = new HabilidadeAdapter(Perfil.this,lista_habilidade);
                    listView_habilidade.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        ReferencesHelper.getDatabaseReference().child("Habilidade").
                orderByChild("idUsuario").equalTo(usuario.getId()).addValueEventListener(habilidadeEventListener);

        //carregando post do usuario

        postEventListiner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Post p = postSnapshot.getValue(Post.class);
                        lista_post.add(p);
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Perfil.this);
                    listView_post.setHasFixedSize(true);
                    listView_post.setLayoutManager(layoutManager);

                    PostAdapter adapter = new PostAdapter(Perfil.this,lista_post);
                    listView_post.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ReferencesHelper.getDatabaseReference().child("Post").
                orderByChild("idUsuario").equalTo(usuario.getId()).addValueEventListener(postEventListiner);

        novoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, CadastroPost.class);
                intent.putExtra("IDUSUARIO", usuario.getId());
                startActivity(intent);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Feedback.class);
                Gson gson = new Gson();
                intent.putExtra("USUARIO", gson.toJson(usuario));
                startActivity(intent);
            }
        });

        novaMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, CadastroMensagem.class);
                Gson gson = new Gson();
                intent.putExtra("USUARIO", gson.toJson(usuario));
                startActivity(intent);
            }
        });

        conversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, ListaConversa.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_area, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_areas:
                intent = new Intent(Perfil.this, ListaArea.class);
                startActivity(intent);
                return true;
            case R.id.action_conversas:
                intent = new Intent(Perfil.this, ListaConversa.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                ReferencesHelper.getFirebaseAuth().signOut();
                intent = new Intent(Perfil.this, Login.class);
                startActivity(intent);
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
