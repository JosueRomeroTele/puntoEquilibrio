package com.example.puntoequilibrio.empresario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.puntoequilibrio.Interface.iComunicaFragments;
import com.example.puntoequilibrio.PerfilFragment;
import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.constantes.Constante;
import com.example.puntoequilibrio.dto.ProductoDto;
import com.example.puntoequilibrio.dto.UsuarioDto;
import com.example.puntoequilibrio.empresario.Fragments.CostoVariablesFragment;
import com.example.puntoequilibrio.empresario.Fragments.CostosFijosFragment;
import com.example.puntoequilibrio.empresario.Fragments.MainFragment;
import com.example.puntoequilibrio.empresario.Fragments.ProductosDetalleFragment;
import com.example.puntoequilibrio.empresario.Fragments.PuntoEquilibrioFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmpresarioActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {



    private FirebaseAuth mAuth;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;


    FirebaseDatabase firebaseDatabase;



    NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresario);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        drawerLayout = findViewById(R.id.drawerEmpresario);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Empresario");
        }

        nav = findViewById(R.id.navigationViewEmpresa);


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                String titulo = "Punto de Equilibrio";

                switch (item.getItemId()){
                    case R.id.nav_perfil:
                        fragment = new PerfilFragment();
                        titulo  = "Mi cuenta";
                        break;
                    case R.id.nav_costosFijos:
                        fragment = new CostosFijosFragment();
                        titulo  = "Costos Fijos";
                        break;
                    case R.id.nav_costosVariables:
                        fragment = new CostoVariablesFragment();
                        titulo  = "Costo Variables";
                        break;
                    case R.id.nav_puntoEquilibrio:
                        fragment = new PuntoEquilibrioFragment();
                        titulo  = "Punto Equilibrio";
                        break;
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainerViewEmpresario, fragment);
                ft.commit();

                // set the toolbar title
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(titulo);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerEmpresario);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });




        firebaseDatabase.getReference().child(Constante.DB_USUARIOS).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Log.d("msg header", "ingreso a la ada");
                    UsuarioDto user = snapshot.getValue(UsuarioDto.class);
                    View header = nav.getHeaderView(0);
                    //textNameNavUser
                    TextView textUserHeader = header.findViewById(R.id.textNameNavUser);
                    textUserHeader.setText(user.getNombre() + " " + user.getApellidoPaterno() + " "+user.getApellidoMaterno());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }


}