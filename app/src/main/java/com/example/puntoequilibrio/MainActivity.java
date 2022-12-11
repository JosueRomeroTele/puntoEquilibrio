package com.example.puntoequilibrio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.puntoequilibrio.dto.UsuarioDto;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();


        DatabaseReference usuariosReference = firebaseDatabase.getReference().child("Usuarios");

        usuariosReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        usuariosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren()){
                    UsuarioDto userDto = children.getValue(UsuarioDto.class);
                    Log.d("msg", children.getKey() + " | " + userDto.getNombre() + " | " + userDto.getDni() + " | " + userDto.getRol());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /**
        firebaseDatabase.getReference().child("Usuario/-NIOcHt11CvqJcRcoEcP").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    UsuarioDto usuarioDto = snapshot.getValue(UsuarioDto.class);
                    Log.d("msg", usuarioDto.getNombre() + " | " + usuarioDto.getRuc());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("msg", error.getMessage());
            }
        });*/
    }
}