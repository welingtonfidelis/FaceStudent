package com.app.facestudent.facestudentapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.app.facestudent.facestudentapp.Adapter.MensagemAdapter;
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

public class ListaMensagem extends AppCompatActivity {
    private List<Mensagem> lista_mensagem;
    private RecyclerView list_view;
    private Usuario usuario, usuario_destinatario;
    private FloatingActionButton mensagem_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mensagem);

        usuario_destinatario = new Usuario();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Gson gson = new Gson();
        usuario = gson.fromJson(getIntent().getStringExtra("USUARIO"), Usuario.class);

        lista_mensagem = new ArrayList<Mensagem>();
        list_view = findViewById(R.id.lista_mensagem_conversa);
        mensagem_usuario = findViewById(R.id.fb_mensagem_usuario);
        lista_mensagem.clear();
        ValueEventListener mensagemEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot postSnaptshot : dataSnapshot.getChildren()){
                        Mensagem m = postSnaptshot.getValue(Mensagem.class);
                        lista_mensagem.add(m);
                        if(m.getIdDestinatario().equals(ReferencesHelper.getFirebaseAuth().getUid()) && usuario_destinatario.getId() == null){
                            usuario_destinatario.setId(m.getIdRemetente());
                        }else if(m.getIdRemetente().equals(ReferencesHelper.getFirebaseAuth().getUid()) && usuario_destinatario.getId() == null){
                            usuario_destinatario.setId(m.getIdDestinatario());
                        }
                    }
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(ListaMensagem.this);
                list_view.setHasFixedSize(true);
                list_view.setLayoutManager(layoutManager);

                MensagemAdapter mensagemAdapter = new MensagemAdapter(ListaMensagem.this, lista_mensagem);
                list_view.setAdapter(mensagemAdapter);

                mensagem_usuario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ListaMensagem.this, CadastroMensagem.class);
                        Gson gson = new Gson();
                        intent.putExtra("USUARIO", gson.toJson(usuario_destinatario));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ReferencesHelper.getDatabaseReference().child("Mensagem").
                orderByChild("idRemetente").equalTo(usuario.getId()).addValueEventListener(mensagemEventListener);

        ReferencesHelper.getDatabaseReference().child("Mensagem").
                orderByChild("idDestinatario").equalTo(usuario.getId()).addValueEventListener(mensagemEventListener);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_areas:
                intent = new Intent(ListaMensagem.this, ListaArea.class);
                startActivity(intent);
                return true;
            case R.id.action_conversas:
                intent = new Intent(ListaMensagem.this, ListaConversa.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                ReferencesHelper.getFirebaseAuth().signOut();
                intent = new Intent(ListaMensagem.this, Login.class);
                startActivity(intent);
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
