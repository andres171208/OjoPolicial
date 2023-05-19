package com.ojopolicial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class Normaladmin extends AppCompatActivity {
    Spinner spEscuelas;
    Spinner spTipoN;
    ArrayList<String> arrayListEscuelas;
    ArrayAdapter<String> adapterEscuelas;

    CheckBox Enovedad;
    EditText DetalleN;
    Button btnEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normaladmin);

        spEscuelas = findViewById(R.id.spinnerescuelas);
        spTipoN = findViewById(R.id.spinnermedico);
        Enovedad = (CheckBox) findViewById(R.id.checknovedades);
        DetalleN = findViewById(R.id.detallenovedad);
        btnEnviar = findViewById(R.id.btnenviarnovedades);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    EnviarNovedad("http://192.168.194.48/Conexion/ajax/novedades.php?op=insertar");
                try {
                    Toast.makeText(Normaladmin.this, "REGISTRADO", Toast.LENGTH_SHORT).show();
                } catch (Exception jsnEx1) {
                    Toast.makeText(getApplicationContext(), "jsnEx1" + jsnEx1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

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
    public void obtenerEscuelas(JSONArray jsonArray) {
        arrayListEscuelas = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String Escuelas = jsonObject.getString("esc_nombre");
                arrayListEscuelas.add(Escuelas);
            }
        } catch (JSONException jsnEx2) {
            Toast.makeText(getApplicationContext(), "jsnEx2" + jsnEx2.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adapterEscuelas = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayListEscuelas);
        spEscuelas.setAdapter(adapterEscuelas);
    }


    private void EnviarNovedad(String URL) {
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()){
                    Toast.makeText(Normaladmin.this, "NOVEDAD REGISTRADA", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(getApplicationContext(),Normaladmin.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Normaladmin.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros  =   new HashMap<String,String>();
                parametros.put("nov_detalle", DetalleN.getText().toString());

                if (Enovedad.isChecked()) {
                    parametros.put("nov_tipo", spTipoN.getSelectedItem().toString());
                } else {
                    String spTipoN = "Ninguna";
                    parametros.put("nov_tipo", spTipoN);
                }

                String usuario =  "13";
                parametros.put("fk_usuario", usuario);
                String escuela =  "2";
                parametros.put("fk_escuelas", escuela);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}





    //Metodo  Insertar  Novedades





