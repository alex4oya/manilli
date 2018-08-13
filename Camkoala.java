package cachfox.desarrollador_alejandro_manilla.simulacion;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Camkoala extends AppCompatActivity {
    DatabaseReference mdatabasereference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mrootchild =mdatabasereference.child("texto1");
    DatabaseReference mrootchild2 =mdatabasereference.child("imagen1");
    ImageView imagen1;
    TextView texto1;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camkoala);
        texto1=(TextView) findViewById(R.id.texto1);
        imagen1=(ImageView) findViewById(R.id.imagen1);
        //esto sirve para hacer zuum
        PhotoViewAttacher foto= new PhotoViewAttacher(imagen1);
        foto.update();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mrootchild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text=dataSnapshot.getValue().toString();
                texto1.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mrootchild2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image=dataSnapshot.getValue().toString();
                uri=Uri.parse(image);
                impresion();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void impresion() {
        Picasso.with(this)
                .load(uri)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .centerInside()
                .into(imagen1);

    }
}
