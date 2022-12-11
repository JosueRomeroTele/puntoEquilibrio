package com.example.puntoequilibrio.empresario.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.health.SystemHealthManager;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.puntoequilibrio.Adaptadores.AdapterCostoVariable;
import com.example.puntoequilibrio.Adaptadores.AdapterPuntoEquilibrio;
import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.GastosDto;
import com.example.puntoequilibrio.dto.ProductoDto;
import com.example.puntoequilibrio.dto.PuntoEquilibrioDto;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PuntoEquilibrioFragment extends Fragment {

    AdapterCostoVariable adapterProductos;
    RecyclerView recyclerViewEquilibrio;

    private Integer costoTotal,totalVentasMes, ventasTotales;
    Button btn_add;

    double montoTotal,totalVentas;
    PuntoEquilibrioDto puntoEquilibrioDto;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList<ProductoDto> listCostosVariables;


    RecyclerView recyclerViewPuntoEquilibrio;


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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_punto_equilibrio, container, false);

        recyclerViewPuntoEquilibrio = view.findViewById(R.id.recycleViewPuntoEquilibrio);



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        mDatabase.child(Constante.BD_PUNTO_EQUILIBRIO).removeValue();

        mDatabase
                .child(Constante.BD_COSTOS_FIJOS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){

                            double total = 0;
                            for (DataSnapshot cfSnp : snapshot.getChildren()){
                                if (cfSnp.exists()){
                                    GastosDto costoFijo =(GastosDto) cfSnp.getValue(GastosDto.class);
                                    if (costoFijo.getUid_user().equals(mAuth.getCurrentUser().getUid())){
                                        double cfijoObtenido = Double.parseDouble(costoFijo.getMonto());

                                        total +=cfijoObtenido;
                                        Log.d("msg CF TO DENTRO", String.valueOf(costoFijo.getMonto()));
                                    }
                                }
                            }
                            montoTotal  = total;
                            Log.d("msg CF total AFUERA", String.valueOf(montoTotal));

                            puntoEquilibrioDto = new PuntoEquilibrioDto();

                            mDatabase
                                    .child(Constante.BD_COSTOS_VARIABLES)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()){
                                                int ventas=0;
                                                //ventas
                                                for (DataSnapshot cvSnap : snapshot.getChildren()){
                                                    if (cvSnap.exists()){
                                                        ProductoDto proObtenid = cvSnap.getValue(ProductoDto.class);
                                                        if (proObtenid.getUid_user().equals(mAuth.getCurrentUser().getUid())){
                                                            ventas += proObtenid.getCantidad();
                                                        }

                                                    }
                                                }

                                                Log.d("msg cantidad ventas",String.valueOf(ventas));
                                                ventasTotales= ventas;
                                                double margenPonderadoTotal=0.0;

                                                //participacion
                                                ArrayList<PuntoEquilibrioDto> listaPuntoEquilibrio = new ArrayList<>();
                                                for (DataSnapshot cvSnap : snapshot.getChildren()){
                                                    Log.d("msg cantidad ventas for",String.valueOf(ventas));
                                                    if (cvSnap.exists()){
                                                        ProductoDto proObtenid = cvSnap.getValue(ProductoDto.class);
                                                        PuntoEquilibrioDto nuevo = new PuntoEquilibrioDto();
                                                        if (proObtenid.getUid_user().equals(mAuth.getCurrentUser().getUid())){
                                                            nuevo.setReferencia(proObtenid.getReferencia());
                                                            nuevo.setCantidadMes(proObtenid.getCantidad());
                                                            Log.d("msg pro",proObtenid.getReferencia());
                                                            Log.d("msg cant pro",String.valueOf(proObtenid.getCantidad()));
                                                            Log.d("msg ventaTotal pro",String.valueOf(ventasTotales));

                                                            double participacionObt = ( (double)proObtenid.getCantidad()/ventasTotales )*10;

                                                            Log.d("msg participacio pro",String.valueOf(participacionObt));

                                                            nuevo.setParticipacion(participacionObt);
                                                            nuevo.setPrecio(proObtenid.getPrecio());
                                                            nuevo.setCostoVariable(proObtenid.getCostoVariable());

                                                            double margenDisObt = proObtenid.getPrecio()-proObtenid.getCostoVariable() ;
                                                            Log.d("msg margenDisObt",String.valueOf(margenDisObt));
                                                            nuevo.setMargenDistribucion(margenDisObt);

                                                            double margPondeObt = nuevo.getMargenDistribucion()*nuevo.getParticipacion()*10;
                                                            nuevo.setMargenPonderado(margPondeObt);
                                                            Log.d("msg margenPondeObt",String.valueOf(margenDisObt));

                                                            margenPonderadoTotal += margPondeObt;
                                                            Log.d("msg MArg ind", String.valueOf(margenPonderadoTotal));
                                                            listaPuntoEquilibrio.add(nuevo);
                                                        }

                                                    }
                                                }
                                                Log.d("msg MArgtotal", String.valueOf(margenPonderadoTotal));
                                                System.out.println("valores de punto equilibrio");
                                                System.out.println(listaPuntoEquilibrio);
                                                //punto equilibrio
                                                for(PuntoEquilibrioDto peSnap : listaPuntoEquilibrio){
                                                    System.out.println(peSnap.getReferencia() + " " + peSnap.getParticipacion());
                                                   // System.out.println(peSnap.getReferencia() + " " + peSnap.getParticipacion());
                                                    //puntoEquilibrio cantidad
                                                    double pEquiCantidad = (montoTotal*peSnap.getMargenPonderado())/margenPonderadoTotal;
                                                    peSnap.setPtoEquilibrioCantidad((int) pEquiCantidad);
                                                    Log.d("msg pe cantidad", String.valueOf(pEquiCantidad));

                                                    //puntEquilibrioMonto
                                                    double pEquiMonto = peSnap.getPtoEquilibrioCantidad()*peSnap.getPrecio();
                                                    peSnap.setPtEquilibrioMonto(pEquiMonto);

                                                    //costoVariable
                                                    double costoVtotal = peSnap.getPtoEquilibrioCantidad()*peSnap.getCostoVariable();
                                                    peSnap.setCostoVariableTotal(costoVtotal);
                                                    peSnap.setUidUser(mAuth.getCurrentUser().getUid());

                                                    mDatabase.child(Constante.BD_PUNTO_EQUILIBRIO).push().setValue(peSnap);
                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        mDatabase.child(Constante.BD_PUNTO_EQUILIBRIO).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<PuntoEquilibrioDto> listPuntoEquilibrios = new ArrayList<>();
                    for (DataSnapshot abd : snapshot.getChildren()){
                            if (abd.exists()){
                                PuntoEquilibrioDto pdto = (PuntoEquilibrioDto) abd.getValue(PuntoEquilibrioDto.class);
                                if (pdto.getUidUser().equals(mAuth.getCurrentUser().getUid())){
                                    listPuntoEquilibrios.add(pdto);
                                }
                            }
                    }

                    PuntoEquilibrioDto[] arrayEquilibrios = new PuntoEquilibrioDto[listPuntoEquilibrios.size()];
                    listPuntoEquilibrios.toArray(arrayEquilibrios);

                    AdapterPuntoEquilibrio adapterPuntoEquilibrio = new AdapterPuntoEquilibrio();
                    adapterPuntoEquilibrio.setListaPuntoEquilibrio(arrayEquilibrios);
                    adapterPuntoEquilibrio.setContext(getActivity());

                    recyclerViewPuntoEquilibrio.setAdapter(adapterPuntoEquilibrio);
                    recyclerViewPuntoEquilibrio.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}