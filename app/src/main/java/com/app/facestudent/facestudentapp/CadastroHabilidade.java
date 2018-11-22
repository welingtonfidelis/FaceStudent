package com.app.facestudent.facestudentapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.facestudent.facestudentapp.Adapter.AreaAdapter;
import com.app.facestudent.facestudentapp.Adapter.AreaAdapterLista;
import com.app.facestudent.facestudentapp.Adapter.HabilidadeAdapter;
import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Area;
import com.app.facestudent.facestudentapp.Model.Habilidade;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CadastroHabilidade extends AppCompatActivity {
    private TextView boas_vindas;
    private RecyclerView listView_area;
    private Button pular, salvar;
    private ValueEventListener areaEventListener;
    private List<Habilidade> lista_hablidade_usuario;

    private List<Area> lista_area, lista_area_selecionada;
    List<Habilidade> lista_habilidade = new ArrayList<Habilidade>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_area);

        boas_vindas = findViewById(R.id.tv_bem_vindo);
        listView_area = findViewById(R.id.lista_area);
        //pular = findViewById(R.id.btn_pular);
        salvar = findViewById(R.id.btn_salvar_area);

        lista_area = new ArrayList<Area>();
        lista_area_selecionada = new ArrayList<Area>();
        lista_hablidade_usuario = new ArrayList<Habilidade>();

        boas_vindas.setText("Bem vindo" + " " + ReferencesHelper.getFirebaseAuth().getCurrentUser().getDisplayName());

        //boolean ativado = getIntent().getExtras().getBoolean("ATIVADO");

        /*if(ativado){
            carregaAreas();
        }*/

        ValueEventListener habilidadeEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Habilidade h = postSnapshot.getValue(Habilidade.class);
                        h.setId(postSnapshot.getKey());
                        lista_hablidade_usuario.add(h);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        ReferencesHelper.getDatabaseReference().child("Habilidade").
                orderByChild("idUsuario").equalTo(ReferencesHelper.getFirebaseAuth().getUid()).addValueEventListener(habilidadeEventListener);


        areaEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Area area = postSnapshot.getValue(Area.class);
                        lista_area.add(area);
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(CadastroHabilidade.this);
                    listView_area.setHasFixedSize(true);
                    listView_area.setLayoutManager(layoutManager);

                    AreaAdapterLista adapter = new AreaAdapterLista(CadastroHabilidade.this, lista_area, lista_hablidade_usuario);
                    listView_area.setAdapter(adapter);

                    /*AreaAdapter adapter = new AreaAdapter(lista_area, CadastroHabilidade.this);
                    listView_area.setAdapter(adapter);

                    listView_area.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ColorDrawable colorView = (ColorDrawable) listView_area.getChildAt(position).getBackground();
                            if (colorView == null) {
                                listView_area.getChildAt(position).setBackgroundColor(Color.WHITE);
                            }
                            colorView = (ColorDrawable) listView_area.getChildAt(position).getBackground();
                            if (colorView.getColor() == Color.GREEN) {
                                listView_area.getChildAt(position).setBackgroundColor(Color.WHITE);

                                Area a = (Area) parent.getItemAtPosition(position);
                                lista_area_selecionada.remove(a);
                            } else {
                                listView_area.getChildAt(position).setBackgroundColor(Color.GREEN);

                                Area a = (Area) parent.getItemAtPosition(position);
                                lista_area_selecionada.add(a);
                            }
                        }
                    });*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ReferencesHelper.getDatabaseReference().child("Area").addValueEventListener(areaEventListener);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaHabilidade(lista_area);
            }
        });

        /*pular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroHabilidade.this, ListaArea.class);
                startActivity(intent);
                finish();
            }
        });*/
    }

    public void exibeAlerta() {

        AlertDialog alerta;
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroHabilidade.this);
        //define o titulo
        builder.setTitle("Você não selecionou habilidades");
        //define a mensagem
        builder.setMessage("Está certo disso?");
        //define um botão como positivo
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(CadastroHabilidade.this, ListaArea.class);
                startActivity(intent);
                finish();
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void salvaHabilidade(List<Area> lista) {

        for(Habilidade h: lista_hablidade_usuario){
            ReferencesHelper.getDatabaseReference().child("Habilidade").child(h.getId()).removeValue();
        }

        for (Area a : lista) {
            if(a.isAtivado()){
                Habilidade h = new Habilidade(
                        a.getNome(),
                        ReferencesHelper.getDatabaseReference().push().getKey(),
                        ReferencesHelper.getFirebaseAuth().getUid()
                );
                //h.setId(ReferencesHelper.getFirebaseAuth().getUid());
                lista_habilidade.add(h);
            }
        }

        if (lista_habilidade.isEmpty()) {
            exibeAlerta();
        } else {
            for(Habilidade habilidade: lista_habilidade){
                ReferencesHelper.getDatabaseReference().child("Habilidade").child(habilidade.getId()).setValue(habilidade);
            }
            //ReferencesHelper.getDatabaseReference().child("Habilidade").cx.getUid()).setValue(lista_habilidade);
            Toast.makeText(CadastroHabilidade.this, "Habilidades cadastras, obrigado.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CadastroHabilidade.this, Perfil.class);
            startActivity(intent);
            finish();
        }
    }

   /* public void carregaAreas(){
        //carregando habilidades do usuario no recycler view
        ValueEventListener habilidadeEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AreaAdapter adapter = new AreaAdapter(lista_area, CadastroHabilidade.this);
                listView_area.setAdapter(adapter);

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Habilidade h = postSnapshot.getValue(Habilidade.class);
                        Object o = listView_area.getItemAtPosition(0);
                        o.toString();
                        lista_habilidade.add(h);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        ReferencesHelper.getDatabaseReference().child("Habilidade").
                orderByChild("idUsuario").equalTo(ReferencesHelper.getFirebaseAuth().getUid()).addValueEventListener(habilidadeEventListener);
    }*/

}



