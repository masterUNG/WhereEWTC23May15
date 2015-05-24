package appewtc.masterung.whereewtc23may15;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LatLng btnBangnaLatLng, btnUdomsukLatLng,
                    ewtcLatLng, userLatLng, bangnaSectionLatLng,
                    sieiumLatLng, tawonLatLng;
    private PolylineOptions udomsukPolylineOptions, bangnaPolylineOptions;
    private double douLat, douLng,
                    douUdomsukLat = 13.679892, douUdomsukLng = 100.609557,
                    douBangnaLat = 13.668298, douBangnaLng = 100.604772;
    private PolygonOptions myPolygonOptions;
    private boolean statusABoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Setup LatLng
        setupLatLng();


        setUpMapIfNeeded();
    }   // onCreate

    private void setupLatLng() {

        btnBangnaLatLng = new LatLng(douBangnaLat, douBangnaLng );   // นี่คือพิกัดของ BTS บางนา
        btnUdomsukLatLng = new LatLng(douUdomsukLat, douUdomsukLng );  // Location of BTS Udomsuk
        ewtcLatLng = new LatLng(13.667573, 100.621766);         // Location of EWTC

        //Receive From Intent to Create userLatlng
        douLat = getIntent().getExtras().getDouble("latUser");
        douLng = getIntent().getExtras().getDouble("lngUser");
        userLatLng = new LatLng(douLat, douLng);

        bangnaSectionLatLng = new LatLng(13.67332216, 100.60691357);    //Location ของแยกบางนา
        sieiumLatLng = new LatLng(13.66583705, 100.64433575);
        tawonLatLng = new LatLng(13.66959006, 100.62330723);



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

        //Create Navigator
        createNavigator();

        //Create Polygon
        createPolygon();

        //Show Get Event Click Map
        getEventClickMap();

    }   // setUpMap

    private void getEventClickMap() {

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                statusABoolean = !statusABoolean;
            }
        });

        if (statusABoolean) {
            showPolygon();
        }

    }

    private void createPolygon() {

        myPolygonOptions = new PolygonOptions();
        myPolygonOptions.add(btnUdomsukLatLng)
                .add(ewtcLatLng)
                .add(btnBangnaLatLng)
                .add(btnUdomsukLatLng)
                .strokeWidth(5)
                .strokeColor(Color.MAGENTA)
                .fillColor(Color.argb(50, 140, 197, 56));

        //showPolygon();

    }   // createPolygon

    private void showPolygon() {
        mMap.addPolygon(myPolygonOptions);
    }

    private void createNavigator() {

        //Udomsuk Polyline
        udomsukPolylineOptions = new PolylineOptions();
        udomsukPolylineOptions.add(userLatLng)
                .add(btnUdomsukLatLng)
                .add(bangnaSectionLatLng)
                .add(sieiumLatLng)
                .add(tawonLatLng)
                .add(ewtcLatLng)
                .width(10)
                .color(Color.RED);

        //Bangna Polyline
        bangnaPolylineOptions = new PolylineOptions();
        bangnaPolylineOptions.add(userLatLng)
                .add(btnBangnaLatLng)
                .add(bangnaSectionLatLng)
                .add(sieiumLatLng)
                .add(tawonLatLng)
                .add(ewtcLatLng)
                .width(10)
                .color(Color.BLUE);

        //เลือกเส้นทางที่ใกล้ที่สุด
        chooseNavigator();


    }   // createNavigator

    private void chooseNavigator() {

        double douDistant1 = Math.pow((douLat - douUdomsukLat), 2) + Math.pow((douLng - douUdomsukLng), 2);
        double douDistant2 = Math.pow((douLat - douBangnaLat), 2) + Math.pow((douLng - douBangnaLng), 2);

        if ((douDistant1 - douDistant2) <= 0) {

            mMap.addPolyline(udomsukPolylineOptions);

        } else {

            mMap.addPolyline(bangnaPolylineOptions);

        }   //if


    }   // chooseNavigator

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
