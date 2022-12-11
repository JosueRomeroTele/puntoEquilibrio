package com.example.puntoequilibrio.empresario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.GastosDto;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarCostoFijoActivity extends AppCompatActivity {

    private GastosDto gastosDto;

    TextInputEditText gasto, monto;
    Button btn_edit_cf;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_costo_fijo);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        gastosDto = (GastosDto) intent.getSerializableExtra("costoFijo");

        gasto = findViewById(R.id.id_editar_cf_gasto);
        monto = findViewById(R.id.id_editar_cf_monto);
        btn_edit_cf= findViewById(R.id.btn_edit_costoFijo);

        this.setTitle("Editar Costo Fijo");

        gasto.setText(gastosDto.getGasto());
        monto.setText(gastosDto.getMonto());

        btn_edit_cf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        gastosDto.setGasto(gasto.getText().toString().trim());
                        gastosDto.setMonto(monto.getText().toString().trim());
                    mDatabase.child(Constante.BD_COSTOS_FIJOS).child(gastosDto.getUid_Gastos())
                        .setValue(gastosDto)
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(getApplicationContext(),"Se actualizó el costo fijo correctamente",Toast.LENGTH_LONG).show();
                            finish();
                        });

            }
        });

    }
}