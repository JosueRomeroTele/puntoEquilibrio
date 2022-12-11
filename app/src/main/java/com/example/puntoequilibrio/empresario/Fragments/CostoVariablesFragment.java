package com.example.puntoequilibrio.empresario.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.puntoequilibrio.Adaptadores.AdapterCostoVariable;
import com.example.puntoequilibrio.Interface.iComunicaFragments;
import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.ProductoDto;
import com.example.puntoequilibrio.empresario.AgregarCostoVariableActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CostoVariablesFragment extends Fragment {

    AdapterCostoVariable adapterProductos;
    RecyclerView recyclerViewProductos;


    Button btn_add;

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
        View view = inflater.inflate(R.layout.fragment_costo_variables, container, false);
        recyclerViewProductos = view.findViewById(R.id.recycleViewCostosVariables);


        btn_add = view.findViewById(R.id.btn_agregar_costoVariable);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AgregarCostoVariableActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        mDatabase.child(Constante.BD_COSTOS_VARIABLES)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<ProductoDto> listCostosVariables = new ArrayList<ProductoDto>();
                    for (DataSnapshot cvSnp : snapshot.getChildren()){
                        if (cvSnp.exists()){

                            ProductoDto productoDto = (ProductoDto) cvSnp.getValue(ProductoDto.class);
                            if (productoDto.getUid_user().equals(mAuth.getCurrentUser().getUid())){
                                listCostosVariables.add(productoDto);
                            }
                            //if (productoDto.get)

                        }
                    }

                    ProductoDto[] arrayCostoVariable = new ProductoDto[listCostosVariables.size()];
                    listCostosVariables.toArray(arrayCostoVariable);

                    AdapterCostoVariable adapterCostoVariable = new AdapterCostoVariable();
                    adapterCostoVariable.setListaCostoVariables(arrayCostoVariable);
                    adapterCostoVariable.setContext(getActivity());

                    recyclerViewProductos.setAdapter(adapterCostoVariable);
                    recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}