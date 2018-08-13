package cachfox.desarrollador_alejandro_manilla.simulacion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registro extends AppCompatActivity implements View.OnClickListener {
    EditText email,contraseña;
    Button registrar;
    TextView cuenta;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        email=(EditText)findViewById(R.id.email);
        contraseña=(EditText)findViewById(R.id.contraseña);
        registrar=(Button)findViewById(R.id.registrar);
        registrar.setOnClickListener(this);
        cuenta=(TextView)findViewById(R.id.cuenta);
        cuenta.setOnClickListener(this);

        auth=FirebaseAuth.getInstance();
    }
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cuenta:
                Intent entrada=new Intent(this,MainActivity.class);
                startActivity(entrada);
                break;
            case R.id.registrar:
                sigUpuser(email.getText().toString(),contraseña.getText().toString());
        }
    }

    private void sigUpuser(String correo,final String password) {
        auth.createUserWithEmailAndPassword(correo,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getBaseContext(), "Error: "+task.getException(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getBaseContext(), "Registro completo", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(registro.this,MainActivity.class));
                        }
                    }
                });
    }}
