package com.example.puntoequilibrio.empresario.Fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.GastosDto;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;


public class AgregarCostoFijoFragment extends DialogFragment {

    Button btn_add_cj,btn_cancelar;
    TextInputEditText gasto,monto;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_costo_fijo, container, false);

        gasto = view.findViewById(R.id.editTextGasto_cf);
        monto = view.findViewById(R.id.editTextMonto_cf);
        btn_add_cj = view.findViewById(R.id.btn_add_costoFijo_frag);
        btn_cancelar = view.findViewById(R.id.button4);
        Bundle objetoDispositivo = getArguments();
        System.out.println("data de costo fijo madnada");
        System.out.println(objetoDispositivo);
        btn_add_cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String gastoDescrip = gasto.getText().toString().trim();
                String montoDescrip = monto.getText().toString().trim();

                if (gastoDescrip.isEmpty() || monto.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Los campos son obligatorios", Toast.LENGTH_LONG).show();
                }else{
                    String validar = monto.getText().toString().trim();

                    if(!validar.isEmpty()){
                        if(!validar.matches("^[0-9]+(\\\\.[0-9]+){0,1}$")){
                            double x = Double.parseDouble(monto.getText().toString().trim());
                            Log.d("msg double", montoDescrip);

                            double prbando = (double)Math.round(x * 100d) / 100d;
                            System.out.println(prbando);
                            if(x > 0){
                                //BigDecimal bigDecimal = new BigDecimal(x).setScale(2, RoundingMode.UP);

                                GastosDto gastosDto = new GastosDto(gastoDescrip,String.valueOf(x),"",mAuth.getCurrentUser().getUid());
                                String llaveGasto = mDatabase.push().getKey();
                                gastosDto.setUid_Gastos(llaveGasto);
                                Log.d("msg llave cf", llaveGasto);

                                mDatabase.child(Constante.BD_COSTOS_FIJOS)
                                        .child(llaveGasto).setValue(gastosDto)
                                        .addOnSuccessListener(unused -> {
                                            Log.d("msg", "se registro el costo fijo");
                                            dismiss();
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.d("msg error data ",e.getMessage());
                                            dismiss();
                                        });
                            }else{
                                Toast.makeText(getActivity(), "Inserta un numero mayor a cero", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getActivity(), "Inserta un numero", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Insertar un monto", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }
}