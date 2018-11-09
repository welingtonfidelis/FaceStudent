package com.app.facestudent.facestudentapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.facestudent.facestudentapp.Helper.FormularioPostHelper;
import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Post;
import com.app.facestudent.facestudentapp.Model.Usuario;

public class CadastroPost extends AppCompatActivity {
    private String idUsuario;
    private FloatingActionButton enviarPost;
    private FormularioPostHelper formularioPostHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_post);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            idUsuario= extras.getString("IDUSUARIO");
        }

        formularioPostHelper = new FormularioPostHelper(CadastroPost.this);

        enviarPost = findViewById(R.id.fb_enviar_post);

        enviarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = formularioPostHelper.pegaDoFormulario();
                post.setIdUsuario(idUsuario);

                ReferencesHelper.getDatabaseReference().child("Post").
                        child(ReferencesHelper.getDatabaseReference().push().getKey()).setValue(post);
                Toast.makeText(getBaseContext(), "Salvo com sucesso.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
