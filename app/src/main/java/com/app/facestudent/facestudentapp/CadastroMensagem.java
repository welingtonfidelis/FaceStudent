package com.app.facestudent.facestudentapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Mensagem;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.google.gson.Gson;

import java.util.Date;

public class CadastroMensagem extends AppCompatActivity {
    private Usuario destinatario;
    private EditText textoMensagem;
    private TextView nomeDestinatario;
    private FloatingActionButton enviarMensagem, cancelarMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mensagem);

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        Gson gson = new Gson();
        destinatario = gson.fromJson(getIntent().getStringExtra("USUARIO"), Usuario.class);

        textoMensagem = findViewById(R.id.edt_mensagem_destinatario);
        nomeDestinatario = findViewById(R.id.tv_nome_destinatario);
        enviarMensagem = findViewById(R.id.fb_enviar_mensagem);
        cancelarMensagem = findViewById(R.id.fb_sair_mensagem);

        nomeDestinatario.setText("Para: " + destinatario.getNome());

        enviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textoMensagem.getText().toString().isEmpty()){
                    textoMensagem.setError("Campo obrigatório");
                }
                else{
                    Mensagem mensagem = new Mensagem();

                    mensagem.setId(ReferencesHelper.getDatabaseReference().push().getKey());
                    mensagem.setIdDestinatario(destinatario.getId());
                    mensagem.setIdRemetente(ReferencesHelper.getFirebaseAuth().getUid());
                    mensagem.setDataEnvio(new Date().getTime());
                    mensagem.setTexto(textoMensagem.getText().toString());

                    ReferencesHelper.getDatabaseReference().child("Mensagem").child(mensagem.getId()).setValue(mensagem);
                    Toast.makeText(CadastroMensagem.this, "Mensagem Enviada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        cancelarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
