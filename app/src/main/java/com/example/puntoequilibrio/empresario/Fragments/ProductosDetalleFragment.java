package com.example.puntoequilibrio.empresario.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.dto.ProductoDto;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductosDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductosDetalleFragment extends Fragment {

    TextView nombreProducto;
    ImageView imageProducto;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductosDetalleFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProductosDetalleFragment newInstance(String param1, String param2) {
        ProductosDetalleFragment fragment = new ProductosDetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos_detalle, container, false);
        nombreProducto = view.findViewById(R.id.nombre_producto_detalle);
        imageProducto = view.findViewById(R.id.imagen_producto_detalle);
        // crear el bundle para recibir el objeto enviardo por argumentos
        Bundle objetoProducto = getArguments();
        ProductoDto productoDto = null;
        if (objetoProducto!= null){
            productoDto = (ProductoDto) objetoProducto.getSerializable("objeto");
            //datos de la vista
            nombreProducto.setText(productoDto.getReferencia());
           // imageProducto.setImageResource(productoDto.getImagenProducto());
        }
        return view;
    }
}