package appewtc.masterung.whereewtc23may15;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng btnBangnaLatLng, btnUdomsukLatLng,
                    ewtcLatLng, userLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Setup LatLng
        setupLatLng();


        setUpMapIfNeeded();
    }   // onCreate

    private void setupLatLng() {

        btnBangnaLatLng = new LatLng(13.668298, 100.604772 );   // นี่คือพิกัดของ BTS บางนา
        btnUdomsukLatLng = new LatLng(13.679892, 100.609557 );  // Location of BTS Udomsuk
        ewtcLatLng = new LatLng(13.667573, 100.621766);         // Location of EWTC

        //Receive From Intent to Create userLatlng
        double douLat = getIntent().getExtras().getDouble("latUser");
        double douLng = getIntent().getExtras().getDouble("lngUser");
        userLatLng = new LatLng(douLat, douLng);
    }   // setupLatLng

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        //Setup Center Maps
        setupCenterMaps();

        //Create Maker
        createMaker();

    }   // setUpMap

    private void createMaker() {

        //default Maker
        mMap.addMarker(new MarkerOptions()
                .position(btnBangnaLatLng));

        //Change Color default Maker
        mMap.addMarker(new MarkerOptions()
                .position(btnUdomsukLatLng)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        //Change iCon Maker
        mMap.addMarker(new MarkerOptions()
                .position(ewtcLatLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.build5))
                .title("สถาบัน EWTC")
                .snippet("สถานที่อบรม แอนดรอยด์ โดย มาสเตอร์ อี่ง"));

        //Create User Maker
        mMap.addMarker(new MarkerOptions()
                .position(userLatLng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.friend))
                .title("คุณอยู่ที่นี่"));

    }   //createMaker

    private void setupCenterMaps() {

        //Receive Latitude, Longitude from MainActivity
        double douLat, douLng;
        douLat = getIntent().getExtras().getDouble("latCenter");
        douLng = getIntent().getExtras().getDouble("lngCenter");

        //Create Location
        LatLng centerLatLng = new LatLng(douLat, douLng);

        //Move Camera
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerLatLng, 16));

    }   // setupCenterMaps
}   // Main Class
