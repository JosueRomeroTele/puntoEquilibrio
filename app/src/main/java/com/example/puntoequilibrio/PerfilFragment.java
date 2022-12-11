package com.example.puntoequilibrio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;


public class PerfilFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    TextView correo,usuario,empresa,ruc;
    Button logout,editar;
    private Context context;

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private String storagePath = "imagen_usuario/";
    private static final int COD_SEL_STORAGE = 300;
    private static final int COD_SEL_IMAGE = 200;
    private Uri imagenUrl;
    ProgressDialog progressDialog;

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
        View view = inflater.inflate(R.layout.fragment_perfil2, container, false);
        correo = view.findViewById(R.id.textViewCorreoUser);
        usuario = view.findViewById(R.id.textViewUsuarioUser);
        empresa = view.findViewById(R.id.textViewEmpresaUser);
        ruc = view.findViewById(R.id.textViewRucUser);

        cargarData();
        editar = view.findViewById(R.id.btn_editar_perfil);

        mDatabase.child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UsuarioDto user_2 = snapshot.getValue(UsuarioDto.class);
                    if (user_2.getRol().equals(Constante.ROL_ADMIN)){
                        editar.setVisibility(view.INVISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ImageView imagen = view.findViewById(R.id.id_photoUser);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, COD_SEL_IMAGE);
            }
        });
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            UsuarioDto user_2 = snapshot.getValue(UsuarioDto.class);
                            Intent intent = new Intent(getContext(), EditarPerfil.class);
                            intent.putExtra("usuario", user_2);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        logout = view.findViewById(R.id.btn_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (resultCode==COD_SEL_IMAGE){
                imagenUrl = data.getData();

                String routeStoragePhoto = storagePath + mAuth.getCurrentUser().getUid();
                StorageReference reference = storageReference.child(routeStoragePhoto);

                reference.putFile(imagenUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                        while (!uriTask.isSuccessful()){
                            if (uriTask.isSuccessful()){
                                uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String urlImagen = uri.toString();
                                        Toast.makeText(getActivity(), "Imagen subida correctamente", Toast.LENGTH_LONG).show();

                                        FirebaseDatabase.getInstance().getReference().child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid())
                                                .child("photoUser").setValue(urlImagen);
                                        cargarDatos();
                                    }
                                });
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error al subir imagen", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    private void cargarDatos() {

    }

    private void cargarData() {

        mDatabase.child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UsuarioDto user = snapshot.getValue(UsuarioDto.class);
                    Log.d("msg usuarioDto", user.getEmpresa());
                    correo.setText(user.getCorreo().toString());
                    usuario.setText(user.getNombre().toString());
                    empresa.setText(user.getEmpresa());
                    ruc.setText(user.getRuc().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();



    }
}