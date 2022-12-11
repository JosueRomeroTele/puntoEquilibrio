package com.example.puntoequilibrio.empresario.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.puntoequilibrio.Adaptadores.AdapterCostoFijo;
import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.GastosDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CostosFijosFragment extends Fragment {

    Button btn_add;
    EditText gasto,costo;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    RecyclerView recyclerViewCF;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize firebase auth
        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_costos_fijos, container, false);

        recyclerViewCF = view.findViewById(R.id.recyclerViewCostosFijos);


        btn_add = view.findViewById(R.id.btn_add_costoFijo);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(CostosFijosFragment.this,));
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);
                //fragment_container_view_costoFijo
                transaction.replace(R.id.fragment_container_view_costoFijo ,AgregarCostoFijoFragment.class,null);
                transaction.commit();


            }
        });
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        mDatabase.child(Constante.BD_COSTOS_FIJOS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<GastosDto> listCostosFijos = new ArrayList<GastosDto>();

                    for (DataSnapshot cfSnap : snapshot.getChildren()){
                        if (cfSnap.exists()){
                            GastosDto gastosDto = (GastosDto) cfSnap.getValue(GastosDto.class);
                            if (gastosDto.getUid_user().equals(mAuth.getCurrentUser().getUid())){
                                listCostosFijos.add(gastosDto);
                            }

                        }
                    }

                    GastosDto[] arrayCostosFijos = new GastosDto[listCostosFijos.size()];
                    listCostosFijos.toArray(arrayCostosFijos);
                    AdapterCostoFijo adapterCostoFijo = new AdapterCostoFijo();
                    adapterCostoFijo.setListaCostosFijos(arrayCostosFijos);
                    adapterCostoFijo.setContext(getActivity());

                    recyclerViewCF.setAdapter(adapterCostoFijo);
                    recyclerViewCF.setLayoutManager(new LinearLayoutManager(getActivity()));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}