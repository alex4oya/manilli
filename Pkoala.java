package cachfox.desarrollador_alejandro_manilla.simulacion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class Pkoala extends AppCompatActivity implements View.OnClickListener {

    StorageReference storageReference;
    FirebaseStorage storage;
    ImageButton imagen1;
    EditText texto1;
    Button mandar_datos,subir_foto;
    Uri filepath, link01;
    String url;
    private final int PICK_IMAGEN_REQUEST = 71;

    DatabaseReference mdatabasereference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mrootchild =mdatabasereference.child("texto1");
    DatabaseReference mrootchild2 =mdatabasereference.child("imagen1");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pkoala);
        mandar_datos=(Button)findViewById(R.id.mandar_datos);
        texto1=(EditText)findViewById(R.id.texto1);
        imagen1=(ImageButton)findViewById(R.id.imagen1);
        subir_foto=(Button)findViewById(R.id.subir_foto);
        subir_foto.setOnClickListener(this);
        imagen1.setOnClickListener(this);
        mandar_datos.setOnClickListener(this);

        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id. imagen1:
                elegir_imagen();
                break;
            case R.id.mandar_datos:
                String text=texto1.getText().toString();
                if (text!=null){
                mrootchild.setValue(text);}

                if(url!=null){
                mrootchild2.setValue(url);}
                break;
            case R.id.subir_foto:
                subir_imagen();

        }

    }
    private void subir_imagen() {
        if(filepath!=null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Esperando besos para subir la imagen...");
            progressDialog.show();
            StorageReference ref =storageReference.child("imagenes/"+ UUID.randomUUID().toString());
            ref.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Pkoala.this,"Wiii, ya la podre ver amor :3",Toast.LENGTH_LONG).show();
                            link01=taskSnapshot.getDownloadUrl();
                            url=link01.toString();

                            Toast.makeText(Pkoala.this,""+link01,Toast.LENGTH_LONG).show();

                            im1();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Pkoala.this,"Fallo subir la imagen amor :c"+e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progres =(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Espera un poco amor :3 "+(int)progres+"%");

                        }
                    });
        }
    }

    private void elegir_imagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "¿Que imagen quieres que vea amor?:3"),
                PICK_IMAGEN_REQUEST);
    }
    private void im1() {
        Picasso.with(this)
                .load(link01)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .centerInside()
                .into(imagen1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGEN_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imagen1.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
