package location_gps.com.location_gps.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import location_gps.com.location_gps.utils.MyLog;

/**
 * Created by Dell on 10/27/2015.
 */
public class MyLocationService extends Service
        implements GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mApiClient;
    private static double latitude , longitude;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(com.google.android.gms.location.LocationServices.API)
                .build();
        MyLog.showLog(mApiClient.toString());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mApiClient.connect();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onConnected(Bundle bundle) {
        MyLog.showLog("onConnected()");


    }

    @Override
    public void onConnectionSuspended(int i) {
        MyLog.showLog("onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        MyLog.showLog("onConnectionFailed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MyLog.showLog("onBind");
        return null;
    }
}
