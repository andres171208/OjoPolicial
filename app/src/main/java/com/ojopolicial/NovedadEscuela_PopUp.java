package com.ojopolicial;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NovedadEscuela_PopUp extends AppCompatActivity {

    Bundle bundle;
    String id;//idnovedad
    String id_usuario;//idusuario
    String telefonoUsuario;
    TextView NombreE;
    TextView DetalleN;
    TextView Autor;
    TextView FechaN;
    TextView NovedadT;
    Button EliminarN;
    Button LLamar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedad_escuela_pop_up);
        NombreE = (TextView) findViewById(R.id.titulo_superior);
        DetalleN = (TextView) findViewById(R.id.descripcion_inferior);
        Autor = (TextView) findViewById(R.id.Autor);
        FechaN = (TextView) findViewById(R.id.Fecha);
        NovedadT = (TextView) findViewById(R.id.NovedadTipo);
        EliminarN = (Button) findViewById(R.id.btneliminar);
        LLamar = (Button) findViewById(R.id.btnllamar);

        //especificaciones  de  estilos
        DisplayMetrics medidasVentanas = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentanas);
        int ancho = medidasVentanas.widthPixels;
        int alto = medidasVentanas.heightPixels;
        getWindow().setLayout((int) (ancho * 0.85), (int) (alto * 0.5));
        bundle = getIntent().getExtras();
        id = bundle.getString("id_novedad");
        init(id);

    }

    //Mostrar  Novedad
    public void init(String id) {
        String URL = "http://192.168.194.48/Conexion/ajax/novedades.php?op=mostrar&id=" + id;
        RequestQueue queve = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        obtenerNovedad(jsonObject);
                    } catch (JSONException jsnEx1) {
                        Toast.makeText(getApplicationContext(), "jsnEx1" + jsnEx1.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NovedadEscuela_PopUp.this, "Error de Copilación", Toast.LENGTH_SHORT).show();
            }
        });
        queve.add(stringRequest);
        EliminarN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar("http://192.168.194.48/Conexion/ajax/novedades.php?op=eliminar&id=" + id);
                Toast.makeText(NovedadEscuela_PopUp.this, "NOVEDAD ELIMINADA", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NovedadesActivity.class);
                startActivity(intent);
            }
        });

    }


    //oBTENER   LAS Escuelas

    public void obtenerNovedad(JSONObject jsonObject2) {

        try {
            for (int i = 0; i < jsonObject2.length(); i++) {

                String DeNovedad = jsonObject2.getString("nov_detalle");
                String DeEscuela = jsonObject2.getString("esc_nombre");
                String DeDirector = jsonObject2.getString("Nombres");
                String DeTipo = jsonObject2.getString("nov_tipo");
                String DeFecha = jsonObject2.getString("Fecha_Registro");
                id_usuario = jsonObject2.getString("fk_usuario");

                NombreE.setText(DeEscuela);
                DetalleN.setText(DeNovedad);
                Autor.setText("Director:  " + DeDirector);
                FechaN.setText("Fecha de Reporte:  " + DeFecha);
                NovedadT.setText("Tipo:  " + DeTipo);
                // Telefono(id_usuario);


            }
        } catch (JSONException jsnEx2) {
            Toast.makeText(getApplicationContext(), "jsnEx2" + jsnEx2.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(String URL) {
        RequestQueue queve = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        obtenerNovedad(jsonObject);
                    } catch (JSONException jsnEx1) {
                        Toast.makeText(getApplicationContext(), "jsnEx1" + jsnEx1.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NovedadEscuela_PopUp.this, "Error de Copilación", Toast.LENGTH_SHORT).show();
            }
        });
        queve.add(stringRequest);
    }


    //METODO    llAMAR
    public void Telefono(String id_usuario) {
        String URL = "http://192.168.194.48/Conexion/ajax/persona.php?op=llamar&id=" + id_usuario;
        RequestQueue queve = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        obtenerTelefono(jsonObject);
                    } catch (JSONException jsnEx1) {
                        Toast.makeText(getApplicationContext(), "jsnEx1" + jsnEx1.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NovedadEscuela_PopUp.this, "Error de Copilación", Toast.LENGTH_SHORT).show();
            }
        });
        queve.add(stringRequest);

    }

    //oBTENER  Telefono Usuario

    public void obtenerTelefono(JSONObject jsonObject2) {

        try {
            for (int i = 0; i < jsonObject2.length(); i++)
                telefonoUsuario = jsonObject2.getString("usu_telefono");
            Intent i = new Intent();
            i.setAction(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:"+telefonoUsuario));
            startActivity(i);
            Toast.makeText(getApplicationContext(), telefonoUsuario, Toast.LENGTH_SHORT).show();
        } catch (JSONException jsnEx2) {
            Toast.makeText(getApplicationContext(), "jsnEx2" + jsnEx2.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    //Boton LLamar
    //LLAMAR
    public void onClickLlamada(View v) {
        Telefono(id_usuario);
    }
}
