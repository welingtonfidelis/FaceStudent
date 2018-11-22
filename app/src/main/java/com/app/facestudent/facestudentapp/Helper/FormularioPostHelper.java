package com.app.facestudent.facestudentapp.Helper;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.CadastroPost;
import com.app.facestudent.facestudentapp.Model.Post;
import com.app.facestudent.facestudentapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormularioPostHelper {
    private TextView data;
    private EditText titulo, texto;

    public FormularioPostHelper(CadastroPost activity) {
        titulo = activity.findViewById(R.id.edt_titulo_post);
        texto = activity.findViewById(R.id.edt_texto_post);
    }

    public Post pegaDoFormulario(){
        String t = titulo.getText().toString();
        String x = texto.getText().toString();

        Post post = new Post();
        post.setTitulo(t);
        post.setTexto(x);

        Date date = new Date();
        post.setData(date.getTime());

        return post;
    }
}
