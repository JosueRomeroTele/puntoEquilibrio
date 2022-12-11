package com.example.puntoequilibrio.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.dto.ProductoDto;
import com.example.puntoequilibrio.dto.PuntoEquilibrioDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdapterPuntoEquilibrio extends RecyclerView.Adapter<AdapterPuntoEquilibrio.PuntoEquilibrioViewHolder> {

    private PuntoEquilibrioDto[] listaPuntoEquilibrio;
    private Context context;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @NonNull
    @Override
    public AdapterPuntoEquilibrio.PuntoEquilibrioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_puntoequilibrio,parent,false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return  new PuntoEquilibrioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPuntoEquilibrio.PuntoEquilibrioViewHolder holder, int position) {

        PuntoEquilibrioDto puntoEquilibrioDto = listaPuntoEquilibrio[position];
        holder.d = puntoEquilibrioDto;

        TextView refePE = holder.itemView.findViewById(R.id.id_puntEquiCantidadReferencia);
        TextView cantidadPE = holder.itemView.findViewById(R.id.id_puntEquiCantidad);
        TextView porcePondePE = holder.itemView.findViewById(R.id.id_puntEquiMargenPon);
        TextView porcePartiPE = holder.itemView.findViewById(R.id.id_puntEquiPorcentaParti);

        refePE.setText(puntoEquilibrioDto.getReferencia());
        cantidadPE.setText(String.valueOf(puntoEquilibrioDto.getPtoEquilibrioCantidad()));
        porcePondePE.setText(String.valueOf(puntoEquilibrioDto.getMargenPonderado()));
        porcePartiPE.setText(String.valueOf(puntoEquilibrioDto.getParticipacion()));



    }

    @Override
    public int getItemCount() {
        return listaPuntoEquilibrio.length;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setListaPuntoEquilibrio(PuntoEquilibrioDto[] listaPuntoEquilibrio) {
        this.listaPuntoEquilibrio = listaPuntoEquilibrio;
    }

    public PuntoEquilibrioDto[] getListaPuntoEquilibrio() {
        return listaPuntoEquilibrio;
    }

    public class PuntoEquilibrioViewHolder extends RecyclerView.ViewHolder{
        PuntoEquilibrioDto d;
        public PuntoEquilibrioViewHolder(@NonNull View itemView){super(itemView);}
    }


}
