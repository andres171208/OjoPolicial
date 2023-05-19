package com.ojopolicial;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MenuActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    GoogleMap mMap;
    Button Novedades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Novedades= (Button) findViewById(R.id.btnnovedades);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    // botones
    public void Novedades(View view) {
        Intent Listado = new Intent(this,NovedadesActivity.class);
        startActivity(Listado);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Permisos localizacion
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        //UBICACION DE ESCUELAS
        //  Marcador GralAlbertoEnríquezGallo
        LatLng GralAlbertoEnríquezGallo = new LatLng(-0.06995083157674746, -78.47588198436173);
        mMap.addMarker(new MarkerOptions().position(GralAlbertoEnríquezGallo).title("ESP. Gral. Alberto Enríquez Gallo").snippet("Novedades: NO").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //  Marcador Fumisa
        LatLng Fumisa = new LatLng(-0.7281163574914187, -79.47103178807143);
        mMap.addMarker(new MarkerOptions().position(Fumisa).title("EFP. Fumisa").snippet("Novedades: NO").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //  Marcador La Delicia
        LatLng LaDelicia = new LatLng(-0.12654798142644486, -78.48165726901608);
        mMap.addMarker(new MarkerOptions().position(LaDelicia).title("EFP. La Delicia").snippet("Novedades: NO").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //  Marcador San Diego
        LatLng SanDiego = new LatLng(-0.22274587985121835, -78.52109110699658);
        mMap.addMarker(new MarkerOptions().position(SanDiego).title("EFP. San Diego").snippet("Novedades: NO").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //  Marcador San Pablo Del Lago
        LatLng SanPabloDelLago = new LatLng(0.18972642846751953, -78.1918484737539);
        mMap.addMarker(new MarkerOptions().position(SanPabloDelLago).title("EFP. San Pablo Del lago").snippet("Novedades: NO").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //  Marcador UER
        LatLng UER = new LatLng(-0.4247989655017759, -78.55183489118495);
        mMap.addMarker(new MarkerOptions().position(UER).title("EFP. UER").snippet("Novedades: NO").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        //  Marcador Eugenio Espejo
        LatLng EugenioEspejo = new LatLng(-0.183, -78.504);
        mMap.addMarker(new MarkerOptions().position(EugenioEspejo).title("EFP. Eugenio Espejo").snippet("Novedades: NO").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        // Marcador Tambillo
        LatLng Tambillo = new LatLng(-0.426, -78.553);
        mMap.addMarker(new MarkerOptions().position(Tambillo).title("EFP. Tambillo").snippet("Novedades: SI").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        this.mMap.setOnMapClickListener(this);
        this.mMap.setOnMapClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EugenioEspejo,7));



    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

}