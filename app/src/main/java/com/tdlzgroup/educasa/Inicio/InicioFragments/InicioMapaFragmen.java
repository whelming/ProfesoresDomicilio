package com.tdlzgroup.educasa.Inicio.InicioFragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.tdlzgroup.educasa.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class InicioMapaFragmen extends FragmentActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    static final int DEFAULT_ZOOM = 15;
    static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private LatLng ubicacionLatLng;
    //private LatLng puntofinal;
    private GoogleMap mMap;
    private Button button;
    private double latitudeDefault;
    private double longitudeDefault;
    private double latitudeBundle;
    private double longitudeBundle;
    private float zoom;
    private Context context;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mapa);
        context = this;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //fetchLastLocation();

        latitudeDefault = -16.4010344;
        longitudeDefault = -71.533039;
        latitudeBundle = 0;
        longitudeBundle = 0;
        //ubicacionLatLng = new LatLng(-16.4010344, -71.533039);
        zoom = 12f;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.containsKey("latitude")) {
                // when LatLng Bundle exist
                latitudeBundle = extras.getDouble("latitude", 0);
                longitudeBundle = extras.getDouble("longitude", 0);
            }
        }
        if (latitudeBundle != 0 && longitudeBundle != 0) {
            ubicacionLatLng = new LatLng(latitudeBundle, longitudeBundle);
            zoom = 18f;
            Toast.makeText(context, "ifbundle", Toast.LENGTH_SHORT).show();
        }
        else {
            ubicacionLatLng = new LatLng(latitudeDefault, longitudeDefault);
            Toast.makeText(context, "elsebundle", Toast.LENGTH_SHORT).show();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this); //referencia para acceder a la aplicacion
        button = findViewById(R.id.mapa_btn_listo);
    }

    private void fetchLastLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(InicioMapaFragmen.this, "" + currentLocation.getLatitude() + "//" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(InicioMapaFragmen.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.mapa_style));
        getLocationPermission();
        updateLocationUI();
        if (mLocationPermissionGranted) {
            if (latitudeBundle == 0 && longitudeBundle == 0) {
                getDeviceLocation(false);
            }
            else {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionLatLng, zoom));
            }

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
            }
        });

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);getDeviceLocation
                activarUbicacion();
                getDeviceLocation(true);
                //fetchLastLocation();
                return false;
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });

        button.setOnClickListener(mibtn -> {
            LatLng center = mMap.getCameraPosition().target;
            Intent resultIntent = new Intent();
            resultIntent.putExtra("latitude", center.latitude);
            resultIntent.putExtra("longitude", center.longitude);
            setResult(RESULT_OK, resultIntent);
            finish();
        });


    }

    private void activarUbicacion() {
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            new AlertDialog.Builder(context)
                    .setTitle("GPS Desactivado !")
                    .setMessage("Debe activar el GPS de su celular...")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
    }

    private void getDeviceLocation(boolean move) {
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionLatLng,zoom));
        try {
            if (mLocationPermissionGranted) {
                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            currentLocation = location;
                            latitudeDefault = currentLocation.getLatitude();
                            longitudeDefault = currentLocation.getLongitude();
                            if (move) {
                                CameraUpdate locationLatLng = CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeDefault,longitudeDefault),18);
                                mMap.animateCamera(locationLatLng);

                                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeDefault,longitudeDefault), 17));
                            }
                            else {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionLatLng, zoom));
                            }

                            Toast.makeText(InicioMapaFragmen.this, "if task", Toast.LENGTH_SHORT).show();
                            //SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                            //supportMapFragment.getMapAsync(InicioMapaFragmen.this);
                        }
                        else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionLatLng, zoom));
                            Toast.makeText(InicioMapaFragmen.this, "else task", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }

    }

    public Bitmap resizeMapIcons(Bitmap drawable, int width, int height) {
        Bitmap resizeBitmap = Bitmap.createScaledBitmap(drawable, width, height, false);
        return resizeBitmap;
    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    updateLocationUI();
                }
            }
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //fetchLastLocation();
                }
            }
            break;
        }
    }

    private void updateLocationUI() {
        if (mMap == null) return;
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                getDeviceLocation(false);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
