package location_gps.com.location_gps.async_task;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import location_gps.com.location_gps.interfaces.AddressInterface;

/**
 * Created by Dell on 10/27/2015.
 */
public class MyCity extends AsyncTask<String , String , String >{

    Activity act;
    double latitude;
    double longitude;

    public static AddressInterface responseData;

    public MyCity(Activity act, double latitude, double longitude) {

        this.act = act;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        Geocoder geocoder = new Geocoder(act, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            String addressLine = addresses.get(0).getAddressLine(0);
            String locality = addresses.get(0).getLocality();
            String subLocality = addresses.get(0).getSubLocality();
            String countryName = addresses.get(0).getCountryName();
            String featuredName = addresses.get(0).getFeatureName();
            String adminArea = addresses.get(0).getAdminArea();
            String subAdminArea = addresses.get(0).getSubAdminArea();

            result = addressLine+ "  "+ locality + "  "+ subLocality + "  " + subAdminArea +"  "+ adminArea;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result != null && !result.isEmpty()){
            responseData.getMyAddress(result);
        }
    }

}
