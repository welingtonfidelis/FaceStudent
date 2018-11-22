package com.app.facestudent.facestudentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.app.facestudent.facestudentapp.Adapter.ConversaAdapter;
import com.app.facestudent.facestudentapp.Adapter.UsuarioAdapter;
import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Mensagem;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListaConversa extends AppCompatActivity {
    private ValueEventListener mensagemEventListener, usuarioEventListener;
    private List<Mensagem> lista_mensagem;
    private List<Usuario> lista_usuario;
    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_conversa);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        lista_mensagem = new ArrayList<Mensagem>();
        lista_usuario = new ArrayList<Usuario>();

        listView = findViewById(R.id.listview_usuario_conversa);
        lista_mensagem.clear();
        lista_usuario.clear();
        mensagemEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(final com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Mensagem m = postSnapshot.getValue(Mensagem.class);
                        lista_mensagem.add(m);
                        Log.e("MENSAGEM", m.toString());
                    }

                }
                for (Mensagem m : lista_mensagem) {
                    usuarioEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshott) {
                            if (dataSnapshott.exists()) {
                                boolean v = false;
                                Usuario u;
                                u = dataSnapshott.getValue(Usuario.class);
                                u.setId(dataSnapshott.getKey());
                                for (Usuario lu : lista_usuario) {
                                    if (lu.getId() == u.getId()) {
                                        v = true;
                                        break;
                                    }
                                }
                                if (v == false && !u.getId().equals(ReferencesHelper.getFirebaseAuth().getUid())) {
                                    lista_usuario.add(u);
                                }
                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(ListaConversa.this);
                            listView.setHasFixedSize(true);
                            listView.setLayoutManager(layoutManager);

                            ConversaAdapter conversaAdapter = new ConversaAdapter(ListaConversa.this, lista_usuario);

                            listView.setAdapter(conversaAdapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    };
                    ReferencesHelper.getDatabaseReference().child("Usuario").
                            child(m.getIdDestinatario()).addValueEventListener(usuarioEventListener);
                    ReferencesHelper.getDatabaseReference().child("Usuario").
                            child(m.getIdRemetente()).addValueEventListener(usuarioEventListener);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        ReferencesHelper.getDatabaseReference().child("Mensagem").
                orderByChild("idRemetente").equalTo(ReferencesHelper.getFirebaseAuth().getUid()).addValueEventListener(mensagemEventListener);

        ReferencesHelper.getDatabaseReference().child("Mensagem").
                orderByChild("idDestinatario").equalTo(ReferencesHelper.getFirebaseAuth().getUid()).addValueEventListener(mensagemEventListener);

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
                intent = new Intent(ListaConversa.this, ListaArea.class);
                startActivity(intent);
                return true;
            case R.id.action_conversas:
                intent = new Intent(ListaConversa.this, ListaConversa.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                ReferencesHelper.getFirebaseAuth().signOut();
                intent = new Intent(ListaConversa.this, Login.class);
                startActivity(intent);
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
