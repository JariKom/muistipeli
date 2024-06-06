package com.unity_mainos_pohja;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;


public class MainActivity extends AppCompatActivity {



/*
    //palkkio video
    String GameId = "5484227";
    String AdsId = "Palkkiovideo";

    Boolean TestMode = Boolean.TRUE; // Vaihda falselle, kun käyttää ihan oikeita mainoksia.


    Button nappi1;


    private  boolean onko_ostettu= false;















    MediaPlayer mediaPlayer;



 */





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



      //  mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.kokeilu_musiikki);
      //  mediaPlayer.start();







//Tarkista onko internettiä, jos on, niin pakota päivitys, jos ei ole, niin mene toiselle sivulle.



        // Tarkista Internet-yhteys
        if (isInternetAvailable()) {
            // Internet-yhteys on käytettävissä
            // Voit suorittaa tarvittavat toiminnot tässä
                   paivita();
        } else {
            // Internet-yhteys ei ole käytettävissä
            // Voit esimerkiksi näyttää käyttäjälle ilmoituksen
            toinenSivu();
        }




















/*
   //Palkkiovideon näyttäminen



        UnityAds.initialize(MainActivity.this, GameId, TestMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {

                Toast.makeText(MainActivity.this, "SDK toimii", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {

                Toast.makeText(MainActivity.this, "SDK ei toimi", Toast.LENGTH_SHORT).show();

            }
        });



    nappi1 = findViewById(R.id.musiikki_seis);

    nappi1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {



            UnityAds.load(AdsId);    // lisää tietyn mainoksen ID
            UnityAds.show(MainActivity.this, AdsId,new UnityAdsShowOptions(), showListener);




        }
    });



 */


















    }

    private void toinenSivu() {

        // Viivästytä siirtymistä toiselle sivulle 3 sekuntia
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Luo Intent siirtyäksesi toiselle sivulle
                Intent intent = new Intent(MainActivity.this, valikko.class);
                startActivity(intent);
                finish(); // Sulje tämä sivu, jotta käyttäjä ei voi palata takaisin
            }
        }, 4000); // 3000 millisekuntia eli 3 sekuntia



    }

    private void paivita() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(result -> {

            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
//                requestUpdate(result);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Päivitä sovellus");


                builder.setPositiveButton("Päivitä", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+getPackageName())));
                        }
                        catch (ActivityNotFoundException e){
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                        }

                    }
                });

                builder.setCancelable(false); // Estä dialogin sulkeminen taustalle painamalla

                AlertDialog alertDialog = builder.create();
                alertDialog.show();





            } else {

                // käytössä on uusin versio, voi jatkaa eteenpäin.
                toinenSivu();

            }
        });


    }




    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        return false;
    }




/*
    private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
        @Override
        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
            Log.e("UnityAdsExample", "Unity Ads failed to show ad for " + placementId + " with error: [" + error + "] " + message);
        }

        @Override
        public void onUnityAdsShowStart(String placementId) {
            mediaPlayer.pause();
            Log.v("UnityAdsExample", "onUnityAdsShowStart: " + placementId);
        }

        @Override
        public void onUnityAdsShowClick(String placementId) {
            Log.v("UnityAdsExample", "onUnityAdsShowClick: " + placementId);
        }

        @Override
        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
            Log.v("UnityAdsExample", "onUnityAdsShowComplete: " + placementId);
            if (state.equals(UnityAds.UnityAdsShowCompletionState.COMPLETED)) {
                // Reward the user for watching the ad to completion
                mediaPlayer.start();
            } else {
                // Do not reward the user for skipping the ad
                mediaPlayer.start();
            }

        }
    };



 */


}