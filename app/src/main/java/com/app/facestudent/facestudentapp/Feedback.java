package com.app.facestudent.facestudentapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.google.gson.Gson;

public class Feedback extends AppCompatActivity {

    private RatingBar avaliacao;
    private TextView valorAvaliacao;

    ProgressDialog progressDialog;
    ImageButton enviar;
    Double nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        avaliacao = findViewById(R.id.ratb_avaliacao);
        valorAvaliacao = findViewById(R.id.tv_valorRat);

        avaliacao.setOnRatingBarChangeListener(rat);
        enviar = findViewById(R.id.imgb_enviar);

        Gson gson = new Gson();
        final Usuario usuario = gson.fromJson(getIntent().getStringExtra("USUARIO"), Usuario.class);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Feedback.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (netInfo == null){
                    new AlertDialog.Builder(Feedback.this)
                            .setTitle("SEM INTERNET")
                            .setMessage("Por favor, verifique sua conexão.")
                            .setPositiveButton("OK", null).show();
                }else{
                    usuario.setNota_absoluta(nota);
                    ReferencesHelper.getDatabaseReference().child("Usuario").child(usuario.getId()).child("nota_absoluta").setValue(usuario.getNota_absoluta());
                    ReferencesHelper.getDatabaseReference().child("Usuario").child(usuario.getId()).child("qtd_avaliacoes").setValue(usuario.getQtd_avaliacoes()+1);

                    Toast.makeText(Feedback.this, "Obrigado!.", Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }
    RatingBar.OnRatingBarChangeListener rat = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            valorAvaliacao.setText(rating+"");
            nota = (double) rating;
        }
    };

}
