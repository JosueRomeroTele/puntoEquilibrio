package com.example.puntoequilibrio.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.carrier.CarrierMessagingClientService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puntoequilibrio.EditarPerfil;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.GastosDto;
import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.example.puntoequilibrio.empresario.EditarCostoFijoActivity;
import com.example.puntoequilibrio.empresario.Fragments.AgregarCostoFijoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdapterCostoFijo extends RecyclerView.Adapter<AdapterCostoFijo.CostoFijoViewHolder> {

    private GastosDto[] listaCostosFijos;
    private Context context;

    ImageView btn_delete,btn_edit;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    AgregarCostoFijoFragment costoFijoDa;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public class CostoFijoViewHolder extends RecyclerView.ViewHolder{
        GastosDto d;
        FragmentManager fm;
        public CostoFijoViewHolder(@NonNull View itemView){super(itemView);};
    }

    @NonNull
    @Override
    public CostoFijoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_costofijo,parent,false);
        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();


        return new CostoFijoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CostoFijoViewHolder holder, int position) {

        GastosDto costoFijo = listaCostosFijos[position];
        holder.d = costoFijo;

        TextView textGasto = holder.itemView.findViewById(R.id.id_gasto_nombre_cf);
        TextView textMonto = holder.itemView.findViewById(R.id.id_monto_cantidad_cf);
        btn_delete = holder.itemView.findViewById(R.id.btn_eliminar_cf);
        //btn_delete = holder.itemView.findViewById(R.id.btn_edit_cf);
        Log.d("msg double vista",String.valueOf(costoFijo.getMonto()));
        textGasto.setText("Gasto : " + costoFijo.getGasto());
        textMonto.setText("Monto : " + String.valueOf(costoFijo.getMonto()));
        btn_edit = holder.itemView.findViewById(R.id.id_editar_cf);

        mDatabase.child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UsuarioDto user_2 = snapshot.getValue(UsuarioDto.class);
                    if (!user_2.getHabilitado()){
                        btn_delete.setVisibility(View.INVISIBLE);
                        btn_edit.setVisibility(View.INVISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(costoFijo.getUid_Gastos());
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, EditarCostoFijoActivity.class);
                i.putExtra("costoFijo",costoFijo);
                context.startActivity(i);
                //fragmentTransaction.replace(R.layout.list_costofijo, AgregarCostoFijoFragment.class);



            }
        });



    }

    private void delete(String uid_gastos) {

        mDatabase.child(Constante.BD_COSTOS_FIJOS).child(uid_gastos).removeValue();

    }

    @Override
    public int getItemCount() {
        return listaCostosFijos.length;
    }

    public GastosDto[] getListaCostosFijos() {
        return listaCostosFijos;
    }

    public void setListaCostosFijos(GastosDto[] listaCostosFijos) {
        this.listaCostosFijos = listaCostosFijos;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
