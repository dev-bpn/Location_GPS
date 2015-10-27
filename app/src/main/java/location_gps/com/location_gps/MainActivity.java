package location_gps.com.location_gps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import location_gps.com.location_gps.async_task.MyCity;
import location_gps.com.location_gps.interfaces.AddressInterface;
import location_gps.com.location_gps.interfaces.Location_interface;
import location_gps.com.location_gps.service.MyLocationService;
import location_gps.com.location_gps.utils.MyLog;

public class MainActivity extends AppCompatActivity implements Location_interface , AddressInterface {

    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        Intent intent = new Intent(this , MyLocationService.class);
        startService(intent);

        MyLocationService.responseData = this;
        MyCity.responseData = this;

        locationFromPrefs();

    }

    private void locationFromPrefs(){

        // use this latitude or longitude by default or if no location is found.
        SharedPreferences preferences = getSharedPreferences("LOCATION_PREFS" , Context.MODE_PRIVATE);
        String latitude = preferences.getString("LATITUDE", "");
        String longitude = preferences.getString("LONGITUDE" , "");
        MyLog.showLog("FromMainActivity: Latitude: " + latitude + " Longitude: " + longitude);

    }


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


    // for updated latitude and longitude use this data
    @Override
    public void onGettingLatitudeAndLongitude(String latitude, String longitude) {
        String lati = latitude;
        String longi = longitude;

        new MyCity(MainActivity.this , Double.parseDouble(lati) , Double.parseDouble(longi)).execute();

        MyLog.showLog("Data from Interface: "+ lati +"  "+ longi);
    }

    @Override
    public void getMyAddress(String address) {
        textView.setText(address);
    }
}
