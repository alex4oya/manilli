package cachfox.desarrollador_alejandro_manilla.simulacion;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class estados extends AppCompatActivity implements View.OnClickListener {
    ImageButton imagen1,imagen2;
    TextView primer_texto,texto2;
    Uri uri,uri2;
    Button zorro;
    String comparacion1,texto1,texto002,comparacion2,Mensaje,M2;
    Context context=this;
    private Toolbar toolbar;



    DatabaseReference mdatabasereference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference texto =mdatabasereference.child("texto1");
    DatabaseReference imagen =mdatabasereference.child("imagen1");
    DatabaseReference texto02 =mdatabasereference.child("texto2");
    DatabaseReference imagen02 =mdatabasereference.child("imagen2");
    DatabaseReference mensaje =mdatabasereference.child("mensaje");
    DatabaseReference mensaje2 =mdatabasereference.child("mensaje2");
    public  static  final int NOTIFICACION_ID=1; //numero de intentificacion de mi notificacion




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estados);
        primer_texto=(TextView)findViewById(R.id.primer_texto);
        texto2=(TextView)findViewById(R.id.texto2);
        imagen1=(ImageButton)findViewById(R.id.imagen1);
        imagen2=(ImageButton)findViewById(R.id.imagen2);
        //zorro=(Button)findViewById(R.id.zorro);
        /////////////////////////////////////////

        //zorro.setOnClickListener(this);
        imagen1.setOnClickListener(this);
        imagen2.setOnClickListener(this);
        M2="Estados";

        SharedPreferences sharedPreferences=getSharedPreferences("archivosp",context.MODE_PRIVATE);

        //toolbar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(M2);



        imagen2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent Czorro=new Intent(estados.this,Pzorro.class);
                startActivity(Czorro);
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.iconos,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.zorro:
                Toast.makeText(getBaseContext(),""+Mensaje,Toast.LENGTH_LONG).show();
                break;
            case R.id.koala:
                Intent Ckoala=new Intent(estados.this,Pkoala.class);
                Toast.makeText(getBaseContext(),"Wii a subir una foto para miii <3",Toast.LENGTH_LONG).show();
                startActivity(Ckoala);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void datos1(){

        SharedPreferences sharedPreferences=getPreferences(context.MODE_PRIVATE);
        comparacion1=sharedPreferences.getString("midato","prueba");
        //Toast.makeText(getBaseContext(),""+comparacion1,Toast.LENGTH_LONG).show();
    }

    public void rescribirdatos1(){
        SharedPreferences sharedPreferences=getPreferences(context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("midato",texto1);
        editor.commit();

    }
    public void rescribirdatos2(){
        SharedPreferences sharedPreferences=getPreferences(context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("midato2",texto002);
        editor.commit();

    }
    public void datos2(){

        SharedPreferences sharedPreferences=getPreferences(context.MODE_PRIVATE);
        comparacion2=sharedPreferences.getString("midato2","prueba");
        //Toast.makeText(getBaseContext(),""+comparacion2,Toast.LENGTH_LONG).show();
    }
    public void notificacion(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.kokoro)
                .setContentTitle("Hay una nueva fotito amor ")
                .setContentText("Espero te guste :3")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.corazon))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManager notificacion=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificacion.notify(NOTIFICACION_ID,builder.build());
    }
    @Override
    protected void onStart() {
        super.onStart();
        //impresioncarga();
        mensaje2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                M2=dataSnapshot.getValue().toString();
                getSupportActionBar().setTitle(""+M2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mensaje.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Mensaje=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        texto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 texto1=dataSnapshot.getValue().toString();
                primer_texto.setText(texto1);
                datos1();
                if (!comparacion1.equals( texto1)){
                    rescribirdatos1();
                    notificacion();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        imagen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                String imag=dataSnapshot.getValue().toString();
                uri=Uri.parse(imag);
                impresion();
                } catch (Exception e){
                    Toast.makeText(getBaseContext(), "Lo siento amor :c no cargo la foto :c lo arreglare :3", Toast.LENGTH_LONG).show();
            }
            }
            @Override


    public void onCancelled(DatabaseError databaseError) {

            }
        });
        texto02.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 texto002=dataSnapshot.getValue().toString();
                texto2.setText(texto002);
                datos2();
                if (!comparacion2.equals(texto002)) {
                    rescribirdatos2();
                    notificacion();
                }

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        imagen02.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String imag = dataSnapshot.getValue().toString();
                    if (imag!= null){
                    uri2 = Uri.parse(imag);}
                    impresion2();
                }catch (Exception e){
                    Toast.makeText(getBaseContext(), "Lo siento amor :c no cargo la foto :c lo arreglare :3", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void impresion2() {
        Picasso.with(this)
                .load(uri2)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .centerInside()
                .into(imagen2);
    }

    /*private void impresioncarga() {
        Glide.with(this)
                .load(uri2)
                .fitCenter()//centra la imagen
                .centerCrop()  //ocupa el espacio del imageview
                .placeholder(R.drawable.loader)//carga la imagen de internet o de los recursos de la app
                .diskCacheStrategy(DiskCacheStrategy.ALL)//ocupa all el cache disponible
                //.thumbnail(0.5f) //carga la miniatura en lo que descarga la imagen
                .into(imagen2);//en que imageview se va a iprimir la imegen
    }*/
    private void impresion() {
        Picasso.with(this)
                .load(uri)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .centerInside()
                .into(imagen1);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.zorro:
                Intent Czorro=new Intent(estados.this,Pzorro.class);
                startActivity(Czorro);
                break;*/

            case R.id.imagen1:
                Intent Cambiokoala=new Intent(estados.this,Camkoala.class);
                startActivity(Cambiokoala);
                break;
            case R.id.imagen2:
                Intent Cambiozorro=new Intent(estados.this,Camzorro.class);
                startActivity(Cambiozorro);
                break;
        }
    }
}
