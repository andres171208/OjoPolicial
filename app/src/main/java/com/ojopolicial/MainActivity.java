package com.ojopolicial;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    AlertDialog alerta = null;
    EditText usuario, contraseña;
    String usuario2,contraseña2;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        setTheme(R.style.Theme_OjoPolicial);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contrasena);

        btnlogin= (Button) findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario2 =usuario.getText().toString();
                contraseña2=contraseña.getText().toString();
                if(!usuario2.isEmpty()  && !contraseña2.isEmpty()) {
                    logueo(" http://192.168.194.48/Conexion/ajax/persona.php?op=log");
                }else{
                    Toast.makeText(MainActivity.this, "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
                }
        });

        getAlertaNotGps();
    }

    private void getAlertaNotGps() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("¿Permitir que OjoPolicial pueda acceder a la ubicación de este dispositivo?")
                .setCancelable(false)
                .setIcon(R.drawable.mapaicon)
                .setTitle("GPS")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused")final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused")final int id) {
                        dialog.cancel();
                    }
                });
        alerta = builder.create();
        alerta.show();
    }

    private void logueo(String URL) {
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.isEmpty()){
                    Toast.makeText(MainActivity.this, "CREDENCIALES CORRECTAS", Toast.LENGTH_SHORT).show();
                  Intent intent =  new Intent(getApplicationContext(),Normaladmin.class);
                  startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "CREDENCIALES ERRONEAS", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros  =   new HashMap<String,String>();
                parametros.put("usuario", usuario.getText().toString());
                parametros.put("password", contraseña.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}