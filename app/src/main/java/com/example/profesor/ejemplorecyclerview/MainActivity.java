package com.example.profesor.ejemplorecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Libro> listaLibros = new ArrayList<>();
    private RecyclerView recyclerView;
    private LibroAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preparaInterface();

        // Referencia al recycled view definido en el xml
        recyclerView = findViewById(R.id.recycler_view);

        // Instancia del  adaptador
        mAdapter = new LibroAdapter(listaLibros);

        // Instancia un LayoutManager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // Asigna el layout manager al recycler view
        recyclerView.setLayoutManager(mLayoutManager);

        // Asigna tipo de animación al insertar, borrar..., items
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Asigna el adaptador al recycler view
        recyclerView.setAdapter(mAdapter);



        // Poblamos la lista listaLibros
        // Considerar siempre implementar esto en un singleton. Sobre todo si la aplicación está
        // compuesta por varias actividades que necesitarán también acceder a los datos.

        listaLibros.add(new Libro("Dick, Philip K.","¿Sueñan los androides con ovejas eléctricas?",R.drawable.l19248));
        listaLibros.add(new Libro("Sebald, W. G.","Los anillos de Saturno",R.drawable.l8433974920));
        listaLibros.add(new Libro("Ballard, J. G.","Aparato de vuelo rasante",R.drawable.l24148));
        listaLibros.add(new Libro("Wilson, Robert Charles","Darwinia",R.drawable.l590));
        listaLibros.add(new Libro("Benford, Gregory","En carne alienígena",R.drawable.l23501));
        listaLibros.add(new Libro("Clarke, Arthur C.","En las profundidades",R.drawable.l20707));
        listaLibros.add(new Libro("Asimov, Isaac","Fundación",R.drawable.l24844));
        listaLibros.add(new Libro("Asimov, Isaac","Estoy en Puertomarte sin Hilda",R.drawable.l24785));
        listaLibros.add(new Libro("Achinelli, Sergio","Lágrimas de un dios plutónico",R.drawable.l25880));
        listaLibros.add(new Libro("Abnett, Dan","Legión",R.drawable.l25923));
        listaLibros.add(new Libro("Pohl, Frederik","La llegada de los gatos cuánticos",R.drawable.l7442));
        listaLibros.add(new Libro("Clarke, Arthur C. & Baxter, Stephen","Luz de otros días",R.drawable.l20697));
        listaLibros.add(new Libro("Delany, Samuel R.","Nova",R.drawable.l19512));



        // Notificamos al adaptador para que refleje cambios en el recyclerview
        mAdapter.notifyDataSetChanged();


    }

    void preparaInterface(){

        // Creamos referencias a los views
        Button bInsertar = findViewById(R.id.boton_ins);
        Button bBorrar = findViewById(R.id.boton_borr);
        final EditText editorPos = findViewById(R.id.edit_posicion);

        // Creamos listeners

        // Boton Borrar
        bInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion=0; boolean error = false;
                try {
                    posicion = Integer.valueOf(editorPos.getText().toString());
                } catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Posición no es un número valido" ,Toast.LENGTH_SHORT).show();
                    error = true;
                }
                if(!error && posicion <= listaLibros.size()) {
                    listaLibros.add(posicion, new Libro("Nuevo Autor", "Nuevo Libro", R.drawable.l590));
                    mAdapter.notifyItemInserted(posicion);
                    recyclerView.scrollToPosition(posicion);
                } else {
                    Toast.makeText(getApplicationContext(), "Posición: " + posicion + " fuera de rango" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Boton borrar
        bBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion;
                try {
                    posicion = Integer.valueOf(editorPos.getText().toString());
                } catch (NumberFormatException e){
                    posicion = 0;
                }
                if(posicion < listaLibros.size() && posicion >= 0) {
                    listaLibros.remove(posicion);
                    recyclerView.scrollToPosition(posicion);
                    mAdapter.notifyItemRemoved(posicion);
                } else {
                    Toast.makeText(getApplicationContext(), "Posición: " + posicion + " fuera de rango" ,Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
