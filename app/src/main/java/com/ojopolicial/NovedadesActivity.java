package com.ojopolicial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NovedadesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novedades);
    }
    //Botones
    public void Atras(View novedades){
        Intent Atras = new Intent(this, MenuActivity.class);
        startActivity(Atras);

    }

}