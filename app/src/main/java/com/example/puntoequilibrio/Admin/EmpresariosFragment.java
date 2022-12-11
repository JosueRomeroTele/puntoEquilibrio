package com.example.puntoequilibrio.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.puntoequilibrio.Adaptadores.AdapterEmpresas;
import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EmpresariosFragment extends Fragment {

    AdapterEmpresas adapterEmpresas;
    RecyclerView recyclerViewEmpresas;

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
        View view = inflater.inflate(R.layout.fragment_empresarios, container, false);
        recyclerViewEmpresas = view.findViewById(R.id.recycleViewEmpresas);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mDatabase.child(Constante.DB_USUARIOS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            ArrayList<UsuarioDto> listUsuarios = new ArrayList<>();
                            for (DataSnapshot useSnap : snapshot.getChildren()){
                                if (useSnap.exists()){
                                    UsuarioDto empresas = (UsuarioDto) useSnap.getValue(UsuarioDto.class);
                                    System.out.println(empresas);
                                    if (empresas.getRol().equals(Constante.ROL_EMPRESA)){
                                        listUsuarios.add(empresas);
                                    }
                                }
                            }

                            UsuarioDto[] arrayUsuarios = new UsuarioDto[listUsuarios.size()];
                            listUsuarios.toArray(arrayUsuarios);

                            AdapterEmpresas adapterEmpresas = new AdapterEmpresas();
                            adapterEmpresas.setListaEmpresas(arrayUsuarios);
                            adapterEmpresas.setContext(getActivity());

                            recyclerViewEmpresas.setAdapter(adapterEmpresas);
                            recyclerViewEmpresas.setLayoutManager(new LinearLayoutManager(getActivity()));


                        }
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}