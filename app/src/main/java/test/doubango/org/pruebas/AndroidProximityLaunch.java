package test.doubango.org.pruebas;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

/**
 * Created by gerardo on 01/04/2016.
 */
public class AndroidProximityLaunch extends Application implements BootstrapNotifier {
    private static final String TAG = "AndroidProximityLaunch";
    private RegionBootstrap regionBootstrap;

    public void onCreate() {
        super.onCreate();
        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
                Region region = new Region("myBeaons",
                Identifier.parse("e2c56db5-dffb-48d2-b060-d0f5a71096e0"), null, null);
        beaconManager.setForegroundBetweenScanPeriod(500);
        beaconManager.setForegroundScanPeriod(500);
        regionBootstrap = new RegionBootstrap(this, region);


    }

    @Override
    public void didEnterRegion(Region arg0) {
        Log.d(TAG, "did enter region.");
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        // Important:  make sure to add android:launchMode="singleInstance" in the manifest
        // to keep multiple copies of this activity from getting created if the user has
        // already manually launched the app.
        this.startActivity(intent);


    }



    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }

}