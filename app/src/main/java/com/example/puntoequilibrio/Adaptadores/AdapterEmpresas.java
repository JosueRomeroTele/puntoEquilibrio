package com.example.puntoequilibrio.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puntoequilibrio.Admin.DetalleUsuario;
import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.dto.ProductoDto;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdapterEmpresas extends RecyclerView.Adapter<AdapterEmpresas.EmpresasViewHolder> {

    private UsuarioDto[] listaEmpresas;
    private Context context;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @NonNull
    @Override
    public EmpresasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_empresas,parent,false);

        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return new EmpresasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresasViewHolder holder, int position) {

        UsuarioDto empresa = listaEmpresas[position];
        holder.d = empresa;

        TextView textView_nombre = holder.itemView.findViewById(R.id.id_nombreUsuario);
        TextView textView_dni = holder.itemView.findViewById(R.id.id_dniUsuario);
        TextView textView_ruc = holder.itemView.findViewById(R.id.id_rucUsuario);
        TextView textView_correo = holder.itemView.findViewById(R.id.id_correoUsuario);

        textView_nombre.setText(empresa.getNombre() + " " + empresa.getApellidoPaterno() + " " + empresa.getApellidoMaterno());
        textView_dni.setText(String.valueOf(empresa.getDni()));
        textView_ruc.setText(empresa.getRuc());
        boolean estado = empresa.getHabilitado();
        String mensaje =null;
        if (estado){
            mensaje="Habilitado";
        }else {
            mensaje="Deshabilitado";
        }
        textView_correo.setText(mensaje);


        ConstraintLayout constraintLayout = holder.itemView.findViewById(R.id.constraintEmpresas);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetalleUsuario.class);
                intent.putExtra("empresa", empresa);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaEmpresas.length;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setListaEmpresas(UsuarioDto[] listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public UsuarioDto[] getListaEmpresas() {
        return listaEmpresas;
    }

    public class EmpresasViewHolder extends RecyclerView.ViewHolder{
        UsuarioDto d;
        public EmpresasViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
