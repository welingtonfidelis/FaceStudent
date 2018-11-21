package com.app.facestudent.facestudentapp.Helper;

import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.facestudent.facestudentapp.ListaArea;
import com.app.facestudent.facestudentapp.R;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class Util {

    public static boolean retornaSexo(String sexo){
        if(sexo.equals("Masculino")){
            return false;
        }else{
            return true;
        }
    }

    public static void infoInicial(ListaArea activity) {
        FirebaseUser user = ReferencesHelper.getFirebaseAuth().getCurrentUser();

        NavigationView navigationView;
        navigationView = activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(activity);
        View header = navigationView.getHeaderView(0);
        TextView nome = (TextView) header.findViewById(R.id.textView);
        CircleImageView foto_usuario = (CircleImageView) header.findViewById(R.id.imageView);
        nome.setText(user.getDisplayName());
        new DownloadImageTask(foto_usuario).execute(user.getPhotoUrl().toString());

    }


}
