package location_gps.com.location_gps.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import location_gps.com.location_gps.utils.MyLog;

/**
 * Created by Dell on 10/27/2015.
 */
public class MyLocationService extends Service
        implements GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mApiClient;
    private double latitude , longitude;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(com.google.android.gms.location.LocationServices.API)
                .build();
        mApiClient.connect();

        MyLog.showLog(mApiClient.toString());
    }


    @Override
    public void onConnected(Bundle bundle) {
        MyLog.showLog("onConnected()");

        Location location = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        MyLog.showLog("Latitude: "+ latitude +"\nlongitude: " + longitude);

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        MyLog.showLog("onConnectionFailed");

        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult((Activity) getApplicationContext(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            MyLog.showLog(String.valueOf(connectionResult.getErrorCode()));
        }

    }


    @Override
    public void onConnectionSuspended(int i) {
        MyLog.showLog("onConnectionSuspended");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MyLog.showLog("onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
        MyLog.showLog("onDestroy Service");
    }
}
