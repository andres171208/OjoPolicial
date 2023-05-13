package com.ojopolicial;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

public class Normaladmin extends AppCompatActivity {
    Spinner spEscuelas;
    ArrayList<String> arrayListEscuelas;
    ArrayAdapter<String> adapterEscuelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normaladmin);

        spEscuelas = findViewById(R.id.spinnerescuelas);
        init();
        setupSpinnerMedico();

    }

    private void setupSpinnerMedico() {
        Spinner spinner = findViewById(R.id.spinnermedico);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.medico, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    //Agregar   Lista   de  escuelas
    public void init() {
        String URL = "http://192.168.194.48/Conexion/ajax/escuelas.php?op=listar";
        RequestQueue queve = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                Toast.makeText(Normaladmin.this, "Error de Copilación", Toast.LENGTH_SHORT).show();
            }
        });
        queve.add(stringRequest);
    }


    //oBTENER   LAS Escuelas
    public  void   obtenerEscuelas(JSONArray  jsonArray){
        arrayListEscuelas   =   new ArrayList<>();
        try{
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String  Escuelas   =   jsonObject.getString("esc_nombre");
                arrayListEscuelas.add(Escuelas);
            }
        }catch(JSONException jsnEx2){
            Toast.makeText(getApplicationContext(), "jsnEx2" + jsnEx2.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adapterEscuelas =  new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayListEscuelas);
        spEscuelas.setAdapter(adapterEscuelas);
    }


    public void enviaDatos(String escuela){

    }



}

