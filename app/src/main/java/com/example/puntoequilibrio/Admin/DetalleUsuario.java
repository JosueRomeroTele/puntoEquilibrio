package com.example.puntoequilibrio.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.ProductoDto;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalleUsuario extends AppCompatActivity {

    UsuarioDto empresa;

    TextInputEditText usuario,correo,dni,ruc,empresaName;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        empresa = (UsuarioDto) intent.getSerializableExtra("empresa");

        Log.d("msg detaller user", empresa.getEmpresa());

        usuario = findViewById(R.id.editTextUsuario_emp);
        usuario.setText(empresa.getNombre() + " " +empresa.getApellidoPaterno()+" "+empresa.getApellidoMaterno());
        correo = findViewById(R.id.editTextCorreo_emp);
        correo.setText(empresa.getCorreo());

        dni = findViewById(R.id.editTextDni_emp);
        dni.setText(String.valueOf(empresa.getDni()));

        ruc = findViewById(R.id.editTextRuc_emp);
        ruc.setText(empresa.getRuc());
        empresaName = findViewById(R.id.editTextEmpresa_emp);
        empresaName.setText(empresa.getEmpresa());

        Button habilitar = findViewById(R.id.btn_habil_empresa);
        boolean estado = empresa.getHabilitado();
        if (estado){
            habilitar.setText("Deshabilitar");
        }else{
            habilitar.setText("Habilitar");
        }
        habilitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (estado){
                    mDatabase.child(Constante.DB_USUARIOS).child(empresa.getUidUser()).child("habilitado").setValue(false);

                }else{
                    mDatabase.child(Constante.DB_USUARIOS).child(empresa.getUidUser()).child("habilitado").setValue(true);
                }
                finish();
                return;
            }
        });

    }
}