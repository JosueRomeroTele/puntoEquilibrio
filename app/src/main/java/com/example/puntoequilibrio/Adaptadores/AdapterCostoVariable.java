package com.example.puntoequilibrio.Adaptadores;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.dto.ProductoDto;
import com.example.puntoequilibrio.empresario.AgregarCostoVariableActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterCostoVariable extends RecyclerView.Adapter<AdapterCostoVariable.CostoVariableViewHolder> {

    private ProductoDto[] listaCostoVariables;
    private Context context;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public class CostoVariableViewHolder extends RecyclerView.ViewHolder {
        ProductoDto d;
        public CostoVariableViewHolder(@NonNull View itemView){super(itemView);}
    }


    @NonNull
    @Override
    public CostoVariableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_cost_variable,parent,false);

        mAuth = FirebaseAuth.getInstance();
        // initialize firebase database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return new CostoVariableViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CostoVariableViewHolder holder, int position) {
        ProductoDto costoVariable = listaCostoVariables[position];
        holder.d = costoVariable;

        ImageView imagePro =holder.itemView.findViewById(R.id.imagen_cv_producto);

        TextView nombrePro =  holder.itemView.findViewById(R.id.id_nombreProducto);
        nombrePro.setText(costoVariable.getReferencia());

        TextView costoPro =  holder.itemView.findViewById(R.id.id_costoProducto);
        costoPro.setText("precio : " + costoVariable.getPrecio());

        TextView costoVariablePro =  holder.itemView.findViewById(R.id.id_costoVariable);
        costoVariablePro.setText("Costo V. : " + costoVariable.getCostoVariable());

        TextView cantidadPro =  holder.itemView.findViewById(R.id.id_cantidad);
        cantidadPro.setText("Cantidad : " + Integer.valueOf(costoVariable.getCantidad()));


        // 3. onClick para ir a editar
        CardView constraintLayout = holder.itemView.findViewById(R.id.constraintProducto);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AgregarCostoVariableActivity.class);
                intent.putExtra("producto", costoVariable);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaCostoVariables.length;
    }

    public void setListaCostoVariables(ProductoDto[] listaCostoVariables) {
        this.listaCostoVariables = listaCostoVariables;
    }

    public ProductoDto[] getListaCostoVariables() {
        return listaCostoVariables;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
