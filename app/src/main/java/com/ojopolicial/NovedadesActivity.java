package com.ojopolicial;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NovedadesActivity extends AppCompatActivity {

    ListView listado;
    ArrayList<String> arrayListEscuelas;
    ArrayList<Actividad_Listado> actividad_listados;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedades);
        listado = (ListView) findViewById(R.id.lista);
        init();
    }


    //Agregar   Lista   de  escuelas
    public void init() {
        String URL = "http://192.168.194.48/Conexion/ajax/novedades.php?op=listar";
        RequestQueue queve = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*AQUI LE QUITO LOS CORCHETES Y LE PONGO UNA COMA PARA QUE
                 * SE HAGA UN STRING TIPO json */
                response=response.replace("][",",");
                ;                Log.i("DATO","NUMERO DE DATOS"+response);
                if (response.length() > 0) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        obtenerEscuelas(jsonArray);
                    } catch (JSONException jsnEx1) {
                        Toast.makeText(getApplicationContext(), "jsnEx1" + jsnEx1.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NovedadesActivity.this, "Error de Copilación", Toast.LENGTH_SHORT).show();
            }
        });
        queve.add(stringRequest);
    }


    //oBTENER   LAS Escuelas
    public  void   obtenerEscuelas(JSONArray  jsonArray){
        arrayListEscuelas   =   new ArrayList<String>();
        try{
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.i("OBJETO","OBJ DATOS"+jsonObject);
                String  Escuelas   =   jsonArray.getJSONObject(i).getString("nov_tipo") + "\n" +
                        jsonArray.getJSONObject(i).getString("nov_detalle") + "\n" +
                        jsonArray.getJSONObject(i).getString("esc_nombre") + " ";


                arrayListEscuelas.add(Escuelas);
            }
        }catch(JSONException jsnEx2){
            Toast.makeText(getApplicationContext(), "jsnEx2" + jsnEx2.getMessage(), Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, arrayListEscuelas);
        listado.setAdapter(adapter);
    }

    //Botones
    public void Atras(View novedades){
        Intent Atras = new Intent(this, MenuActivity.class);
        startActivity(Atras);

    }

}
