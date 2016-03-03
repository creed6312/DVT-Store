package com.jhb.dvt.dvt_store;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;

import com.jhb.dvt.dvt_store.Fragment.MainActivityFragment;
import com.jhb.dvt.dvt_store.Utils.Utilities;

public class MainActivity extends BaseActivity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utilities.getBasket(getApplicationContext());
       // GCM();

        loadMainFragment();
    }

    private void loadMainFragment()
    {
        MainActivityFragment frag = new MainActivityFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, frag);
        transaction.commit();
    }

  /*  private void GCM()
    {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences.getBoolean("sentTokenToServer", false);
                if (sentToken)
                {
                    Log.i("GCM","Success");
                }
                else
                {

                }
            }
        };

        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }*/

  /*  private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("MAIN", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
*/
    @Override
    protected void onStop() {
     //   mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("registrationComplete"));
        super.onResume();
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
