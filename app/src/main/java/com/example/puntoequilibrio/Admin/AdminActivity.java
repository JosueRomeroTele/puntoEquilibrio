package com.example.puntoequilibrio.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.puntoequilibrio.PerfilFragment;
import com.example.puntoequilibrio.R;
import com.example.puntoequilibrio.empresario.Fragments.CostoVariablesFragment;
import com.example.puntoequilibrio.empresario.Fragments.CostosFijosFragment;
import com.example.puntoequilibrio.empresario.Fragments.PuntoEquilibrioFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;


    FirebaseDatabase firebaseDatabase;
    NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        drawerLayout = findViewById(R.id.drawerAdmin);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Admin");
        }

        nav = findViewById(R.id.navigationViewAdmin);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                String titulo = "Punto de Equilibrio";

                switch (item.getItemId()){
                    case R.id.nav_perfil_admin:
                        fragment = new PerfilFragment();
                        titulo  = "Mi cuenta";
                        break;
                    case R.id.nav_Empresarios:
                        fragment = new EmpresariosFragment();
                        titulo  = "Empresarios";
                        break;
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainerViewAdmin, fragment);
                ft.commit();

                // set the toolbar title
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(titulo);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerAdmin);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }




}