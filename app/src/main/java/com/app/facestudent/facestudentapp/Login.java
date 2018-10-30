package com.app.facestudent.facestudentapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private SignInButton logar;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth
    private Usuario usuario;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth.AuthStateListener mAuthListiner;
    private ValueEventListener usarioEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = ReferencesHelper.getFirebaseAuth().getInstance();
        logar = findViewById(R.id.btn_logar);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuthListiner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){

                }
                else{

                }
            }
        };

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                String key = account.getEmail();//guarda email como chave unica do usuario
                key = key.replace(".","*"); //substitui ponto por asterisco (padrao firebase reserva ponto final)

                buscaUsuarioBanco(key);
                if(usuario == null){
                    //inicio a tela de cadastro
                    usuario = new Usuario();
                    ReferencesHelper.getDatabaseReference().child("Usuario").child(key).setValue(usuario);
                    Toast.makeText(this, "Usuario nao cadastrado", Toast.LENGTH_SHORT).show();
                }
                else{
                    //inicio tela de listar areas
                    Toast.makeText(this, "Usuario ja cadastrado", Toast.LENGTH_SHORT).show();
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingl    mAuth.addAuthStateListener(mAuthListiner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListiner != null){
            mAuth.removeAuthStateListener(mAuthListiner);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
       // Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }

    public void buscaUsuarioBanco(String email){
        usarioEventListener = new ValueEventListener() {
            @Override
            public void onDataChange( com.google.firebase.database.DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    usuario = new Usuario();
                    usuario = dataSnapshot.getValue(Usuario.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };

        ReferencesHelper.getDatabaseReference().child("Usuario").child(email).addValueEventListener(usarioEventListener);
    }
}
