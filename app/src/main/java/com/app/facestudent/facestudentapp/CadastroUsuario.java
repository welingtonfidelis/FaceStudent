package com.app.facestudent.facestudentapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.facestudent.facestudentapp.Helper.FormularioUsuarioHelper;
import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Usuario;

import java.text.ParseException;

public class CadastroUsuario extends AppCompatActivity {
    private FormularioUsuarioHelper formularioUsuarioHelper;
    private Button btn_salvar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        btn_salvar = findViewById(R.id.btn_proximo);

        formularioUsuarioHelper = new FormularioUsuarioHelper(CadastroUsuario.this);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    usuario = formularioUsuarioHelper.pegaDoFormulario();
                    ReferencesHelper.getDatabaseReference().child("Usuario").child(usuario.getId()).setValue(usuario);
                    Toast.makeText(getBaseContext(), "Salvo com sucesso.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CadastroUsuario.this, CadastroHabilidade.class);
                    startActivity(intent);
                    finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
