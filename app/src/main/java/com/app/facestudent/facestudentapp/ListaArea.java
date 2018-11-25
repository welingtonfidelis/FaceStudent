package com.app.facestudent.facestudentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.Adapter.AreaAdapterGrid;
import com.app.facestudent.facestudentapp.Adapter.UsuarioAdapter;
import com.app.facestudent.facestudentapp.Helper.DownloadImageTask;
import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Helper.Util;
import com.app.facestudent.facestudentapp.Model.Area;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ListaArea extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView teste;
    private ImageView foto_usuario;
    private ValueEventListener areaEventListener;
    private List<Area> lista_area;
    private RecyclerView listView_area;
    private GoogleApiClient mGoogleSignInClient;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView_area = findViewById(R.id.lista_area_afins);
        foto_usuario = findViewById(R.id.imageView);

        lista_area = new ArrayList<Area>();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lista_area.clear();
        areaEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Area area = postSnapshot.getValue(Area.class);
                        lista_area.add(area);
                    }

                    int numColunas = 2;
                    listView_area.setLayoutManager(new GridLayoutManager(ListaArea.this, numColunas));
                    AreaAdapterGrid adapter = new AreaAdapterGrid(ListaArea.this, lista_area);
                    //reaAdapter adapter = new AreaAdapter(lista_area, CadastroHabilidade.this);
                    listView_area.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        ReferencesHelper.getDatabaseReference().child("Area").addValueEventListener(areaEventListener);

        Util.infoInicial(ListaArea.this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
                intent = new Intent(ListaArea.this, ListaArea.class);
                startActivity(intent);
                return true;
            case R.id.action_conversas:
                intent = new Intent(ListaArea.this, ListaConversa.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                ReferencesHelper.getFirebaseAuth().signOut();
                intent = new Intent(ListaArea.this, Login.class);
                startActivity(intent);
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_perfil) {
            final Usuario[] u = new Usuario[1];
            ValueEventListener usuarioEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        u[0] = dataSnapshot.getValue(Usuario.class);
                        u[0].setId(dataSnapshot.getKey());
                        Gson gson = new Gson();
                        Intent it = new Intent(ListaArea.this, Perfil.class);
                        it.putExtra("USUARIO", gson.toJson(u[0]));
                        startActivity(it);
                        //finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            };
            ReferencesHelper.getDatabaseReference().child("Usuario").child(ReferencesHelper.getFirebaseAuth().getUid()).addValueEventListener(usuarioEventListener);

        } else if (id == R.id.nav_conversas) {
            Intent intent = new Intent(ListaArea.this, ListaConversa.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            ReferencesHelper.getFirebaseAuth().signOut();
            Intent intent = new Intent(ListaArea.this, Login.class);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
