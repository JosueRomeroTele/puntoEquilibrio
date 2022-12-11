package com.example.puntoequilibrio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.puntoequilibrio.Admin.AdminActivity;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.empresario.EmpresarioActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Inicio extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        mAuth = FirebaseAuth.getInstance();

        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void InicioVista(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void registrarseVista(View view){
        Intent i = new Intent(this, RegistarActivity.class);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // SE VERIFICA QUE SI ESTA LOGUEADO -> actividad solicitudes pendientes
        if (mAuth.getCurrentUser() != null){
            FirebaseUser currentUser = mAuth.getCurrentUser();
            Log.d("msg / i usuario logeado", mAuth.getCurrentUser().getUid());

            mDatabase.child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("msg / firebase", "Error getting data", task.getException());
                        Toast.makeText(Inicio.this, "Error en obtener información del usuario", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d("msg / firebase", String.valueOf(task.getResult().getValue()));
                        HashMap<String, String> data = (HashMap<String, String>) task.getResult().getValue();
                        String privilegio = data.get("rol");
                        Intent intent = null;
                        switch(privilegio){
                            case "Admin":
                                intent = new Intent(Inicio.this, AdminActivity.class);

                                break;

                            case "Empresa":
                                // TODO
                                Log.d("msg", "ir a pagina principal de cliente");
                                intent = new Intent(Inicio.this, EmpresarioActivity.class);

                                //mAuth.signOut();
                                break;

                        }
                        startActivity(intent);


                    }
                }
            });

        }
    }


}