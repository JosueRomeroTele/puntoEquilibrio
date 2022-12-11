package com.example.puntoequilibrio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistarActivity extends AppCompatActivity {

    TextInputEditText ruc, nombre, apePaterno,apeMaterno,dni,correo,password,empresa;

    Button agregarUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();

        ruc = findViewById(R.id.editTextRuc_reg);
        nombre = findViewById(R.id.editTextNombre_reg);
        apePaterno = findViewById(R.id.editTextPaterno_reg);
        apeMaterno = findViewById(R.id.editTextMaterno_reg);
        dni = findViewById(R.id.editTextDni_reg);
        correo = findViewById(R.id.editTextCorreo_reg);
        password = findViewById(R.id.editTextPass_reg);
        empresa = findViewById(R.id.editTextEmpresa_reg);

        agregarUser = findViewById(R.id.btn_add_user);

        agregarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correoUser = correo.getText().toString().trim();
                String contraUser = password.getText().toString().trim();
                String nombreUser = nombre.getText().toString().trim();
                String rucUser =ruc.getText().toString().trim();
                String paternoUser=apePaterno.getText().toString().trim();
                String maternoUser=apeMaterno.getText().toString().trim();
                String dniUser=dni.getText().toString().trim();
                String empresaUser= empresa.getText().toString();


                regitrar(rucUser,nombreUser,paternoUser,maternoUser,dniUser,correoUser,contraUser,empresaUser);
            }
        });
    }

    private void regitrar(String rucUser, String nombreUser, String paternoUser, String maternoUser, String dniUser, String correoUser, String contraUser,String empresaUser) {

        mAuth.createUserWithEmailAndPassword(correoUser,contraUser).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    UsuarioDto usuarioDto = new UsuarioDto(rucUser,nombreUser,paternoUser,maternoUser,Integer.valueOf(dniUser),Constante.ROL_EMPRESA,Constante.EMPRESA_HABILITADO,correoUser,empresaUser,
                            mAuth.getCurrentUser().getUid(),"");
                    FirebaseDatabase.getInstance().getReference(Constante.DB_USUARIOS)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(usuarioDto).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mAuth.getCurrentUser().sendEmailVerification();
                                    finish();
                                    return;
                                }
                            });
                }else{
                    Toast.makeText(RegistarActivity.this, "Error en el registro", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


}