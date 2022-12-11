package com.example.puntoequilibrio.empresario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.ProductoDto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarCostoVariableActivity extends AppCompatActivity {

    Button btn_add_cv,btn_dele_cv;
    TextInputEditText referencia, cantidad,precio,costoVariable;
    TextInputEditText probandoReferencia;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ProductoDto producto;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_costo_variable);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);

        this.setTitle("Crear Costo Variable");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referencia = findViewById(R.id.editTextReferencia_cv);
        cantidad = findViewById(R.id.editTextCantidad_cv);
        precio = findViewById(R.id.editTextPrecio_cv);
        costoVariable = findViewById(R.id.editTextCosto_cv);

        btn_add_cv = findViewById(R.id.btn_add_producto);
        //btn_delete_producto
        btn_dele_cv = findViewById(R.id.btn_delete_producto);

        //obtener el id del user
        Intent intent = getIntent();
        producto = (ProductoDto) intent.getSerializableExtra("producto");

        if (producto == null){

            btn_dele_cv.setVisibility(View.INVISIBLE);
            this.setTitle("Agregar Costo Variable");
            btn_add_cv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //Log.d("msg material",probandoReferencia.getText().toString());

                    String referenciaCv = referencia.getText().toString().trim();
                    String cantidadCv = cantidad.getText().toString().trim();
                    String precioCv = precio.getText().toString().trim();
                    String costoCv = costoVariable.getText().toString().trim();

                    double preci = Double.parseDouble(precioCv);
                    double costo = Double.parseDouble(costoCv);

                    if (preci>costo){
                        if (referenciaCv.isEmpty() && cantidadCv.isEmpty() && precioCv.isEmpty() && costoCv.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_LONG).show() ;
                        }else{

                            agregarCostoVariable(referenciaCv,cantidadCv,precioCv,costoCv);

                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"El precio debe ser mayor al costo",Toast.LENGTH_LONG).show() ;
                    }


                }
            });
        }else{
            this.setTitle("Editar Costo Variable");
            btn_add_cv.setText("Actualizar Data");

            obtenerProducto(producto.getIdProducto());
            btn_dele_cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        mDatabase.child(Constante.BD_COSTOS_VARIABLES).child(producto.getIdProducto()).removeValue();
                        Toast.makeText(AgregarCostoVariableActivity.this,"Se elimino registro exitosamente",Toast.LENGTH_LONG).show();
                        finish();



                }
            });

            btn_add_cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String referenciaCv = referencia.getText().toString().trim();
                    String cantidadCv = cantidad.getText().toString().trim();
                    String precioCv = precio.getText().toString().trim();
                    String costoCv = costoVariable.getText().toString().trim();

                    producto.setReferencia(referenciaCv);
                    producto.setCantidad(Integer.valueOf(cantidadCv));
                    producto.setPrecio(Double.valueOf(precioCv));
                    producto.setCostoVariable(Double.valueOf(costoCv));


                    double preci = Double.parseDouble(precioCv);
                    double costo = Double.parseDouble(costoCv);

                    if (preci>costo){
                        actualizarData(producto);
                        finish();
                    }else{
                        Toast.makeText(AgregarCostoVariableActivity.this,"El precio debe ser mayor al costo",Toast.LENGTH_LONG).show();
                    }



                }
            });
        }




    }

    private void actualizarData(ProductoDto producto_2) {
        FirebaseDatabase.getInstance().getReference()
                .child(Constante.BD_COSTOS_VARIABLES).child(producto_2.getIdProducto()).setValue(producto_2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AgregarCostoVariableActivity.this,"Se actualizo la data corretamente",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void obtenerProducto(String idProducto) {

        FirebaseDatabase.getInstance().getReference().child(Constante.BD_COSTOS_VARIABLES)
                .child(idProducto).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        ProductoDto productoDto = (ProductoDto) dataSnapshot.getValue(ProductoDto.class);
                        referencia.setText(productoDto.getReferencia());
                        cantidad.setText(String.valueOf(productoDto.getCantidad()));
                        precio.setText(String.valueOf(productoDto.getPrecio()));
                        costoVariable.setText(String.valueOf(productoDto.getCostoVariable()));
                    }
                });
    }

    private void agregarCostoVariable(String referenciaCv, String cantidadCv, String precioCv, String costoCv) {
        Log.d("msg uid user ",mAuth.getUid());
        //(String referencia, String imagenProducto, int cantidad, double precio, double costoVariable)
        ProductoDto productoDto = new ProductoDto("",referenciaCv,"imagenejemplo", Integer.valueOf(cantidadCv),Double.valueOf(precioCv),Double.valueOf(costoCv)
        ,mAuth.getCurrentUser().getUid());
       String llave =  mDatabase.push().getKey();
        productoDto.setIdProducto(llave);
        mDatabase.child(Constante.BD_COSTOS_VARIABLES).child(llave)
                .setValue(productoDto).addOnSuccessListener(unused -> {
                    Log.d("msg", "se registro el costo variable");
                    Toast.makeText(getApplicationContext(),"Se guardo el costo variable correctamente",Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.d("msg error data ",e.getMessage());
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}