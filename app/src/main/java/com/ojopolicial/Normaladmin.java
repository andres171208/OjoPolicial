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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Normaladmin extends AppCompatActivity {
    Spinner spEscuelas;
    Spinner spTipoN;
    Bundle bundle;
    String  id_usuario;


    CheckBox Enovedad;
    EditText DetalleN;
    Button Salir;
    Button btnEnviar;
    String idescuelasE;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normaladmin);
        Salir= (Button) findViewById(R.id.btnSalir);
        spTipoN = findViewById(R.id.spinnermedico);
        Enovedad = (CheckBox) findViewById(R.id.checknovedades);
        DetalleN = findViewById(R.id.detallenovedad);
        btnEnviar = findViewById(R.id.btnenviarnovedades);
        bundle = getIntent().getExtras();
        id_usuario  = bundle.getString("id_usuario");

        Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CerrarSesion("http://192.168.194.48/Conexion/ajax/persona.php?op=salir");
                Toast.makeText(Normaladmin.this, "Cerrando Sesion...", Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


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
    //Agregar   Lista   de  escuelas
    public void init() {
        String URL = "http://192.168.194.48/Conexion/ajax/escuelas.php?op=mostrarescuelas&id="+id_usuario;
        RequestQueue queve = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        obtenerEscuelas(jsonObject);
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
    public void obtenerEscuelas(JSONObject  jsonObject2){

        try{
            for(int i=0;i<jsonObject2.length();i++) {
                idescuelasE=jsonObject2.getString("id_escuelas");
            }
        } catch (JSONException jsnEx2) {
            Toast.makeText(getApplicationContext(), "jsnEx2" + jsnEx2.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
                parametros.put("fk_usuario", id_usuario);
                parametros.put("fk_escuelas", idescuelasE);


                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void CerrarSesion(String URL){
        RequestQueue queve = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                    } catch (JSONException jsnEx1) {
                        Toast.makeText(getApplicationContext(), "Sesion Cerrada", Toast.LENGTH_SHORT).show();
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
}
