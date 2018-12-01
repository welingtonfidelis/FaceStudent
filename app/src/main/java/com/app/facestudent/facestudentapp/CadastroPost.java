package com.app.facestudent.facestudentapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.facestudent.facestudentapp.Helper.FormularioPostHelper;
import com.app.facestudent.facestudentapp.Helper.ReferencesHelper;
import com.app.facestudent.facestudentapp.Helper.StorageHelper;
import com.app.facestudent.facestudentapp.Model.Post;
import com.app.facestudent.facestudentapp.Model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
/*import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;*/

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class CadastroPost extends AppCompatActivity {
    private String idUsuario;
    private String localArquivoFoto;
    private FloatingActionButton enviarPost;
    private FormularioPostHelper formularioPostHelper;
    private TextView dataPost;
    private ImageView imgv_post;
    private Bitmap imagemPost;
    private Uri resultUri;
    private Bitmap imagemSalva;
    private String photoUrl;
    private Bitmap imagemServidor;
    private final int TIRA_FOTO  = 123;

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
        dataPost = findViewById(R.id.tv_data_post);
        imgv_post = findViewById(R.id.imagv_foto_post);

        Date data = new Date();
        dataPost.setText(DateFormat.format("dd/MM/yy", new Date(data.getTime())).toString());

        enviarPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = formularioPostHelper.pegaDoFormulario();
                post.setIdUsuario(idUsuario);

                ReferencesHelper.getDatabaseReference().child("Post").
                        child(ReferencesHelper.getDatabaseReference().push().getKey()).setValue(post);
                Toast.makeText(getBaseContext(), "Post salvo com sucesso.", Toast.LENGTH_SHORT).show();
                //saveImage();
                finish();
            }
        });

        imgv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                localArquivoFoto = getExternalFilesDir(null)+"/" + System.currentTimeMillis() + ".jpg";

                Uri localFoto = Uri.fromFile(new File(localArquivoFoto));

                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                startActivityForResult(irParaCamera, 123);
                //chooseImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TIRA_FOTO){
            if(resultCode == Activity.RESULT_OK){
                Bitmap im = BitmapFactory.decodeFile(localArquivoFoto);
                Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(im, 150,150,true);

                imgv_post.setImageBitmap(imagemFotoReduzida);
            }
            else{
                this.localArquivoFoto = null;
            }
        }
    }

    /*private void chooseImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(CadastroPost.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();

                try {
                    imagemSalva = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    imgv_post.setImageBitmap(imagemSalva);
                    //imgv_post.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }*/

    // CODIGO NOVO

    /*private void loadImage(String id) {
        final StorageHelper storageHelper = new StorageHelper(CadastroPost.this, id);
        imagemPost = storageHelper.openImage();

        if (imagemPost != null) {
            imgv_post.setImageBitmap(imagemPost);
            //imageView.setVisibility(View.VISIBLE);
            Log.e("BuscaLocal", "Imagem Local");
        } else {
            ReferencesHelper.getStorageReference().child("Imagens").child( id+".jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    imagemServidor = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    if (imagemServidor != null) {
                        imgv_post.setImageBitmap(imagemServidor);
                        //imgv_post.setVisibility(View.VISIBLE);

                        storageHelper.save(bytes);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception exception) {
                    try {

                        //imgv_foto.setVisibility(View.VISIBLE);
                        //imgv_upload.setVisibility(View.VISIBLE);

                        throw exception;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }*/

    private void saveImage() {
        if (imagemSalva != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagemSalva.compress(Bitmap.CompressFormat.JPEG, 65, stream);
            byte[] data = stream.toByteArray();

            if (data != null) {
                //PopUp de carregamento
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                final StorageHelper iStorageHelper = new StorageHelper(getApplicationContext(), localArquivoFoto);
                progressDialog.show();iStorageHelper.save(data);


                //Upload da imagem nova
                UploadTask uploadTask = ReferencesHelper.getStorageReference().child("Imagens").child(localArquivoFoto+".jpg").putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(CadastroPost.this, exception.toString(), Toast.LENGTH_SHORT).show();
                        //ProgressBarHelper.stopProgress(ProfileActivity.this);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(CadastroPost.this, "Imagem enviada com sucesso", Toast.LENGTH_SHORT).show();

                        // Atualizando key da nova imagem no objeto local
                        //if(local.getKey() != null){
                           // ReferencesHelper.getDatabaseReference().child("Local").child(local.getKey()).child("imagem").
                               //     setValue(photoUrl);

                       // }
                    }
                });
            }
        }else{
            Toast.makeText(this, "Você deve selecionar uma imagem", Toast.LENGTH_SHORT).show();
        }
    }
}
