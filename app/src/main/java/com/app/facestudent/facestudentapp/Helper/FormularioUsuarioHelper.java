package com.app.facestudent.facestudentapp.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.app.facestudent.facestudentapp.CadastroUsuario;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.app.facestudent.facestudentapp.R;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormularioUsuarioHelper {
    private EditText nome, email, dataNascimento, descricao;
    private RadioButton sexo_masculino, sexo_feminino;
    private ImageView foto_usuario;
    private Usuario usuario;
    private FirebaseUser user;

    public FormularioUsuarioHelper(CadastroUsuario activity) {
        user = ReferencesHelper.getFirebaseAuth().getCurrentUser();

        foto_usuario = activity.findViewById(R.id.imgv_foto_usuario);
        nome = activity.findViewById(R.id.edt_nome_usuario);
        email = activity.findViewById(R.id.edt_email_usuario);
        dataNascimento = activity.findViewById(R.id.edt_data_nascimento_usuario);
        dataNascimento.addTextChangedListener(MaskEditUtil.mask(dataNascimento,MaskEditUtil.FORMAT_DATE));
        descricao = activity.findViewById(R.id.edt_descricao_usuario);
        sexo_feminino = activity.findViewById(R.id.rb_sexo_feminino);
        sexo_masculino = activity.findViewById(R.id.rb_sexo_masculino);

        new DownloadImageTask(foto_usuario).execute(user.getPhotoUrl().toString());
        nome.setText(user.getDisplayName());
        email.setText(user.getEmail());

    }

    public Usuario pegaDoFormulario() throws ParseException {
        usuario = new Usuario();

        if(nome.getText().toString().isEmpty()){
            nome.setError("Campo obrigatório.");
        }

        if(dataNascimento.getText().toString().isEmpty()){
            dataNascimento.setError("Campo obrigatório.");
        }

        if(descricao.getText().toString().isEmpty()){
            descricao.setError("Campo obrigatório.");
        }

        usuario.setFoto(user.getPhotoUrl().toString());
        usuario.setId(user.getUid());
        usuario.setNome(nome.getText().toString());
        usuario.setEmail(email.getText().toString());
        usuario.setDataCriacaoConta(new Date().getTime());
        usuario.setDescricaoPessoal(descricao.getText().toString());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = formatter.parse(dataNascimento.getText().toString());

        usuario.setDataNascimento(date.getTime());

        return usuario;
    }

}

/*class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}*/
