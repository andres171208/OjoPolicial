package com.ojopolicial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



// Lista de novedades
//
public class Actividad_Listado extends Activity {
    private  ListView lista;
    EditText Escuelas;
    EditText  Novedades;
    ArrayList<String> arrayListEscuelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);

        ArrayList<Lista_entrada> datos = new ArrayList<>();



        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = view.findViewById(R.id.titulo_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada) entrada).get_Titulo());

                    TextView texto_inferior_entrada = view.findViewById(R.id.descripcion_inferior);
                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada) entrada).get_Descripcion());

                    ImageView imagen_entrada = view.findViewById(R.id.imagen_lista);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada) entrada).get_Imagen());
                }
            }
        });
        lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada elegido = (Lista_entrada) pariente.getItemAtPosition(posicion);

                CharSequence texto = "Seleccionado: " + elegido.get_Descripcion();
                Toast toast = Toast.makeText(Actividad_Listado.this, texto, Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }

}