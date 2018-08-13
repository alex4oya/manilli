package cachfox.desarrollador_alejandro_manilla.simulacion;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class pruebas extends AppCompatActivity {
    ImageView prueba,cache;
    EditText ref, data;

    String database="prueba",comparacion1;
    Uri imagen,caching;
    String traspaso;
    Bitmap bitmap;


    DatabaseReference mdatabasereference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference texto =mdatabasereference.child(database);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);
        prueba=(ImageView)findViewById(R.id.prueba);
        cache=(ImageView)findViewById(R.id.cache);
        data=(EditText)findViewById(R.id.data);
        Picasso.with(this)
                .setIndicatorsEnabled(true);

    }


    @Override
    protected void onStart() {
        super.onStart();
        texto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    traspaso=dataSnapshot.getValue().toString();
                    imagen=Uri.parse(traspaso);
                    datos1();
                        internet();
                        rescribirdatos1();
                        //cache();
                        //Toast.makeText(getBaseContext(),"se debe mostrar el cache",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void datos1(){

        SharedPreferences sharedPreferences=getPreferences(this.MODE_PRIVATE);
        comparacion1=sharedPreferences.getString("imagen","prueba");

    }
    public void rescribirdatos1(){
        SharedPreferences sharedPreferences=getPreferences(this.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("imagen",traspaso);
        editor.commit();
        Toast.makeText(getBaseContext(),""+comparacion1,Toast.LENGTH_LONG).show();

    }
    public void internet(){
        //carga los datos de internet
        Picasso.with(this)
                .load(imagen)
             //   .memoryPolicy(MemoryPolicy.NO_CACHE)
               // .networkPolicy(NetworkPolicy.NO_CACHE)
                .fit()
                .centerInside()
                .into(cache);
    }
    //carga los datos del cache
    private   void cache(){
        Picasso.with(this)
                .load(imagen)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.kokoro)
                .into(cache);
    }
}
