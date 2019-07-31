package com.example.educasa.Inicio.InicioFragments;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.educasa.Inicio.Inicio;
import com.example.educasa.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class InicioSolicitarCurso extends Fragment
        implements OnMapReadyCallback {
    private String bundlerecibido;
    private Button btn_confirmarclase;
    private Button btn_cancelar;
    private GoogleMap Map;
    private Spinner materia;
    FusedLocationProviderClient mFusedLocationProviderClient;
    Location mLastKnownLocation;
    Boolean mLocationPermissionGranted;
    static final int DEFAULT_ZOOM = 15;
    static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    public InicioSolicitarCurso() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio_solicitar_curso, container, false);
        btn_confirmarclase = v.findViewById(R.id.btn_confirmar_solicitar_curso);
        btn_cancelar = v.findViewById(R.id.btn_cancelar_curso);
        materia = (Spinner) v.findViewById(R.id.inicio_solicitud_curso_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Materia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        materia.setAdapter(adapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this); //referencia para acceder a la aplicacion


        bundlerecibido = getArguments().getString("datosolicitarcurso");
        Toolbar toolbar = v.findViewById(R.id.inicio_toolbar_solicitar_curso);
        toolbar.setTitle("Solicitud de " + bundlerecibido);
        if (getActivity() != null) {
            ((Inicio) getActivity()).setSupportActionBar(toolbar);
            ((Inicio) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Inicio) getActivity()).getSupportActionBar().show();
        }

        btn_confirmarclase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Confirmaste clase de " + bundlerecibido, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return v;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Map = googleMap;

        LatLng sydney = new LatLng(-16.32, -71.5188325);

        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

        Map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Map.addMarker(new MarkerOptions().position(latLng).title("Mark2"));
            }
        });//agregar un marcador

        Map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Map.addMarker(new MarkerOptions().position(latLng).title("Mark3").icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(BitmapFactory.decodeResource(getResources(), R.drawable.carro), 100, 100)))); //cambiar el icono de nuestro marcador
            }
        });

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)//pregunta si hay permiso para acceder a nuestra ubicacion
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},//de lo contrario tratamoss de acceder a nuestro permiso
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode){
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }
    private void updateLocationUI() {
        if (Map == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                Map.setMyLocationEnabled(true); //para mostrar mi ubicacion
                Map.getUiSettings().setMyLocationButtonEnabled(true); //para poner el botoncito de obtener nuestra aplicacion  getUiSettings() modificar la parte grafica de nuestro mapa
            } else {
                Map.setMyLocationEnabled(false);
                Map.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            LatLng miubicacion=new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
                            Map.moveCamera(CameraUpdateFactory.newLatLngZoom(miubicacion,15));
                            mMap.addMarker(new MarkerOptions().position(miubicacion).title("Marke"));
                        } else {

                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
