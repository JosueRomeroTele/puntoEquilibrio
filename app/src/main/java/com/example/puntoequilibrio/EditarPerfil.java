package com.example.puntoequilibrio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.example.puntoequilibrio.empresario.AgregarCostoVariableActivity;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarPerfil extends AppCompatActivity {

    UsuarioDto usuario;
    TextInputEditText  nombre,apelPa,apelMa,dni,ruc,correo,empresa;
    Button edit_perfil;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.setTitle("Actualizar Usuario");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("msg user","llego a la otra vista");
        Intent intent = getIntent();
        usuario = (UsuarioDto) intent.getSerializableExtra("usuario");
        Log.d("msg user","llego el usuario");
        Log.d("msg user",usuario.getNombre());

        nombre = findViewById(R.id.editTextNombre_user);
        apelPa = findViewById(R.id.editTextApePate_user);
        apelMa = findViewById(R.id.editTextApeMate_user);
        dni = findViewById(R.id.editTextDni_user);
        ruc = findViewById(R.id.editTextRuc_user);
        correo = findViewById(R.id.editTextCorreo_user);
        empresa = findViewById(R.id.editTextempresa_user);

        edit_perfil = findViewById(R.id.btn_edit_user);



        nombre.setText(usuario.getNombre().toString().trim());
        apelPa.setText(usuario.getApellidoPaterno());
        apelMa.setText(usuario.getApellidoMaterno());
        dni.setText(String.valueOf(usuario.getDni()));
        ruc.setText(usuario.getRuc());
        correo.setText(usuario.getCorreo());
        empresa.setText(usuario.getEmpresa());

        edit_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreEdit = nombre.getText().toString().trim();
                String apelPaEdit = apelPa.getText().toString().trim();
                String apelMaEdit = apelMa.getText().toString().trim();
                String dniEdit = dni.getText().toString().trim();
                String rucEdit = ruc.getText().toString().trim();
                String correoEdit = correo.getText().toString().trim();
                String empresaEdit = empresa.getText().toString().trim();

                usuario.setNombre(nombreEdit);
                usuario.setApellidoPaterno(apelPaEdit);
                usuario.setApellidoMaterno(apelMaEdit);
                usuario.setDni(Integer.valueOf(dniEdit));
                usuario.setRuc(rucEdit);
                usuario.setCorreo(correoEdit);
                usuario.setEmpresa(empresaEdit);

                actualizarData(usuario);
                finish();
                return;
            }
        });
    }

    private void actualizarData(UsuarioDto usuario) {

        FirebaseDatabase.getInstance().getReference()
                .child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid())
                .setValue(usuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getBaseContext(),"Se actualizo la data corretamente",Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }
}