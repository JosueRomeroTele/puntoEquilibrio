package com.example.puntoequilibrio;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.puntoequilibrio.Admin.AdminActivity;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.empresario.EmpresarioActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser()!=null){
            Log.d("msg / i usuario logeado", mAuth.getCurrentUser().getUid());
            mDatabase.child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (task.isSuccessful()){
                        Log.d("msg / firebase", String.valueOf(task.getResult().getValue()));
                        HashMap<String, String> data = (HashMap<String, String>) task.getResult().getValue();
                        String rol = data.get("rol");
                        Intent intent = null;

                        switch(rol){
                            case Constante.ROL_EMPRESA:
                                Log.d("msg", "ir a pagina principal de empresa");
                                intent = new Intent(LoginActivity.this, EmpresarioActivity.class);
                                break;

                            case Constante.ROL_ADMIN:
                                // TODO
                                Log.d("msg", "ir a pagina principal de admin");
                                intent = new Intent(LoginActivity.this, AdminActivity.class);
                                //mAuth.signOut();
                                break;
                        }
                        startActivity(intent);

                    }else{
                        Log.e("msg / firebase", "Error getting data", task.getException());
                        Toast.makeText(LoginActivity.this, "Error en obtener información del usuario", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    public void registrar(View view){
        Intent intent = new Intent(LoginActivity.this, RegistarActivity.class);
        startActivity(intent);
    }


    public void login(View view){

        TextInputEditText email = findViewById(R.id.correoUserInicio);
        TextInputEditText password = findViewById(R.id.contraUserInicio);
        Log.d("msg login",email.getText().toString() + " + " + password.getText().toString());

        if (    email.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() ) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //LoginActivity.this.activityResultLauncher
                            Log.d("msg login","ingreso exitoso");
                            finish();
                            return;
                        }
                    }
                });
    }


    public void olvideMiContraseña(View view){

        TextInputEditText email = findViewById(R.id.correoUserInicio);

        String emailRe = email.getText().toString().trim();

        // se verifica que haya ingresado el correo
        if (    emailRe.isEmpty() ) {
            Toast.makeText(this, "Por favor, ingrese su email para enviar el correo de restauracion de contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.sendPasswordResetEmail(emailRe)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Correo enviado enviado para la restauracion de contraseña", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error en el proceso. Por favor, intente de nuevo", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }


}