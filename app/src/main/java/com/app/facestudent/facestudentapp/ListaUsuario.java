package com.app.facestudent.facestudentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.facestudent.facestudentapp.Adapter.UsuarioAdapter;
import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Habilidade;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaUsuario extends AppCompatActivity {

    private ValueEventListener usuarioEventListener, habilidadeEventListner;
    private List<Usuario> lista_usuario;
    private List<Habilidade> lista_habilidade;
    private RecyclerView listView_usuarios;
    private LinearLayoutManager linearLayoutManager;
    private String nome_area;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuario);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lista_usuario = new ArrayList<Usuario>();
        listView_usuarios = findViewById(R.id.listview_usuario_area);
        lista_habilidade = new ArrayList<Habilidade>();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            nome_area = extras.getString("NOMEAREA");
        }

        //carregaUsuario();
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
                intent = new Intent(ListaUsuario.this, ListaArea.class);
                startActivity(intent);
                return true;
            case R.id.action_conversas:
                intent = new Intent(ListaUsuario.this, ListaConversa.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                ReferencesHelper.getFirebaseAuth().signOut();
                intent = new Intent(ListaUsuario.this, Login.class);
                startActivity(intent);
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void carregaUsuario(){
        habilidadeEventListner = new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                lista_habilidade.clear();
                lista_usuario.clear();
                listView_usuarios.getRecycledViewPool().clear();
                if (dataSnapshot.exists()) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        Habilidade h = postSnapshot.getValue(Habilidade.class);
                        lista_habilidade.add(h);
                    }
                }

                //lista_usuario.clear();
                for(Habilidade h: lista_habilidade){
                    usuarioEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Usuario u = dataSnapshot.getValue(Usuario.class);
                                u.setId(dataSnapshot.getKey());
                                lista_usuario.add(u);
                            }

                            layoutManager = new LinearLayoutManager(ListaUsuario.this);
                            listView_usuarios.setHasFixedSize(true);
                            listView_usuarios.setLayoutManager(layoutManager);

                            UsuarioAdapter usuarioAdapter = new UsuarioAdapter(ListaUsuario.this, lista_usuario);
                            listView_usuarios.setAdapter(usuarioAdapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    };
                    ReferencesHelper.getDatabaseReference().child("Usuario").child(h.getIdUsuario()).addValueEventListener(usuarioEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        ReferencesHelper.getDatabaseReference().child("Habilidade").orderByChild("nome").equalTo(nome_area).addValueEventListener(habilidadeEventListner);

    }

    @Override
    protected void onDestroy() {
        Log.e("DESTROY",  "Destruido");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.e("RESUME",  "RESUMIDO");
        super.onResume();
        lista_habilidade.clear();
        lista_usuario.clear();
        listView_usuarios.getRecycledViewPool().clear();
        carregaUsuario();
    }


}
