package kg.studio9.location;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> places = new ArrayList<>();
    SharedPreferences.Editor edit;
    int index = 0;
    SharedPreferences local;
    int sizeOfPlaces;

    String latt;
    String lngg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        local = getPreferences(MODE_PRIVATE);
        edit = local.edit();
        edit.remove("sizeOfPlaces");
        edit.clear();
        sizeOfPlaces = local.getInt("sizeOfPlaces", 0);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
     @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng bishkek = new LatLng(42.8746, 74.5698);
        edit.putInt("sizeOfPlaces", places.size());

        //mMap.addMarker(new MarkerOptions().position(bishkek).title("Marker in Bishkek"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bishkek));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(fromPosition, getMaxZoomLevel()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bishkek, 14));
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        //mMap.addMarker(new MarkerOptions().position(random).title("Marker in random"));


        int indexSaved = 0;
        for (int i = 5; i < sizeOfPlaces; i++) {
            Float lat = local.getFloat(Integer.toString(indexSaved), 1);
            Log.d("First tag", Integer.toString(indexSaved));
            Log.d("First float", Float.toString(lat));
            indexSaved++;
            Float lng = local.getFloat(Integer.toString(indexSaved), 0);
            Log.d("Second tag", Integer.toString(indexSaved));
            Log.d("Second float", Float.toString(lng));
            indexSaved++;
            LatLng tempMarker = new LatLng(lat, lng);
            places.add(tempMarker);
            mMap.addMarker(new MarkerOptions().position(tempMarker).title("Marker saved prev"));
        }


            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    places.add(latLng);
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Your marker"));

                    edit.putInt("sizeOfPlaces", places.size());


                    latt = Double.toString(latLng.latitude);
                    lngg = Double.toString(latLng.longitude);
                            Float lat = Float.parseFloat(Double.toString(latLng.latitude));
                    Float lng = Float.parseFloat(Double.toString(latLng.longitude));
                    Log.d("Latitude to be saved", Float.toString(lat));
                    Log.d("Longitude to be saved", Float.toString(lng));

                    edit.putFloat(Integer.toString(index), lat);
                    Log.d("First tag", Integer.toString(index));
                    index++;

                    edit.putFloat(Integer.toString(index), lng);
                    Log.d("Second tag", Integer.toString(index));
                    index++;

                }
            });

    }
}






