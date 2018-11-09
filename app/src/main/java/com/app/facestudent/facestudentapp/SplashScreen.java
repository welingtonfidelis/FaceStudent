package com.app.facestudent.facestudentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        if(ReferencesHelper.getFirebaseAuth().getCurrentUser() != null){
            Intent intent = new Intent(SplashScreen.this, ListaArea.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(SplashScreen.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

}
