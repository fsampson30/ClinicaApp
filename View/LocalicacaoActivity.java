package br.com.sonus.sonuscliente.View;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.sonus.sonuscliente.R;

public class LocalicacaoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localicacao);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng cliente = new LatLng(-lat, -lon);
        mMap.addMarker(new MarkerOptions().position(client).title("Local Escolhido"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(client,15));
    }
}
