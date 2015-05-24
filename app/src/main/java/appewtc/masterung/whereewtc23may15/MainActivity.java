package appewtc.masterung.whereewtc23may15;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    //Explicit
    private TextView latTextView, lngTextView;
    private LocationManager objLocationManager;
    private Criteria objCriteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initial Widget
        initialWidget();

        //Setup Location
        setupLocation();

    }   // onCreate

    @Override
    protected void onResume() {
        super.onResume();

        afterResume();

    }   // onResume

    private void afterResume() {

        objLocationManager.removeUpdates(objLocationListener);
        String strLat = "Unknow";
        String strLng = "Unknow";

        //Check Connected Internet
        Location objNetworkProvider = requestUpdateFromProvider(LocationManager.NETWORK_PROVIDER, "Cannot Connected Internet");
        if (objNetworkProvider != null) {

            strLat = String.format("%.7f", objNetworkProvider.getLatitude());
            strLng = String.format("%.7f", objNetworkProvider.getLongitude());

        }   //if

        //Check GPS OK?
        Location objGPSprovider = requestUpdateFromProvider(LocationManager.GPS_PROVIDER, "Cannot connected GPS");
        if (objGPSprovider != null) {
            strLat = String.format("%.7f", objGPSprovider.getLatitude());
            strLng = String.format("%.7f", objGPSprovider.getLongitude());
        }

        latTextView.setText(strLat);
        lngTextView.setText(strLng);
    }   // afterResume

    @Override
    protected void onStop() {
        super.onStop();

        objLocationManager.removeUpdates(objLocationListener);

    }   // onStop

    //Create Request From Provider
    public Location requestUpdateFromProvider(String strProvider, String strError) {

        Location objLocation = null;

        //Check Provider
        if (objLocationManager.isProviderEnabled(strProvider)) {

            objLocationManager.requestLocationUpdates(strProvider, 1000, 10, objLocationListener);
            objLocation = objLocationManager.getLastKnownLocation(strProvider);

        } else {

            Toast.makeText(MainActivity.this, strError, Toast.LENGTH_SHORT).show();

        } //if

        return objLocation;
    }   // requestUpdate

    //Create Listener
    public final LocationListener objLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            latTextView.setText(String.format("%.7f", location.getLatitude()));
            lngTextView.setText(String.format("%.7f", location.getLongitude()));

        }   // onLocationChange

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void setupLocation() {
        objLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objCriteria = new Criteria();
        objCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        objCriteria.setAltitudeRequired(false);
        objCriteria.setBearingRequired(false);
    }

    private void initialWidget() {
        latTextView = (TextView) findViewById(R.id.txtShowLat);
        lngTextView = (TextView) findViewById(R.id.txtShowLng);
    }

    public void clickWhere(View view) {

        double latUser = Double.parseDouble(latTextView.getText().toString());
        double lngUser = Double.parseDouble(lngTextView.getText().toString());

        myIntentToMaps(latUser, lngUser, latUser, lngUser);

    }   // clickWhere

    public void clickEWTC(View view) {

        double latEWTC = 13.667573, lngEWTC = 100.621766;

        myIntentToMaps(latEWTC, lngEWTC,
                Double.parseDouble(latTextView.getText().toString()),
                Double.parseDouble(lngTextView.getText().toString()));

    }   // clickEWTC

    private void myIntentToMaps(double latCenter, double lngCenter, double latUser, double lngUser) {

        Intent objIntent = new Intent(MainActivity.this, MapsActivity.class);

        objIntent.putExtra("latCenter", latCenter);
        objIntent.putExtra("lngCenter", lngCenter);
        objIntent.putExtra("latUser", latUser);
        objIntent.putExtra("lngUser", lngUser);

        startActivity(objIntent);

    }   // myIntentToMaps

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}   // Main Class
