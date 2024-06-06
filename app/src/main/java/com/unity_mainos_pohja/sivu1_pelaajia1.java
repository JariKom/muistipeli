package com.unity_mainos_pohja;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

public class sivu1_pelaajia1 extends AppCompatActivity implements View.OnClickListener {




    private ImageView[] cards;
    private int[] cardImages;
    private int[] cardTags;
    private int flippedCards;
    private int firstFlippedCard;


    String GameId = "5484227";
    //String AdsId = "Palkkiovideo";  // palkkiovideo
    String AdsId = "Interstitial_Android"; // yhdensivun mainos

    Boolean TestMode = Boolean.FALSE; // Vaihda Falselle, kun käyttää ihan oikeita mainoksia. True, kun on testaus
    int Kuinka_monta_pelia_sitten_mainos=0;



    MediaPlayer mediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sivu1_pelaajia1);

        mediaPlayer = MediaPlayer.create(sivu1_pelaajia1.this,R.raw.kokeilu_musiikki);
        mediaPlayer.start();



        UnityAds.initialize(sivu1_pelaajia1.this, GameId, TestMode, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {

                //         Toast.makeText(sivu2.this, "SDK toimii", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {

                //         Toast.makeText(sivu2.this, "SDK ei toimi", Toast.LENGTH_SHORT).show();

            }
        });



        UnityAds.load(AdsId);    // lisää tietyn mainoksen ID


















        // ylhäällä alustaa mainoksen




        cards = new ImageView[8];
        cardImages = new int[]{R.drawable.koira, R.drawable.kissoja, R.drawable.orava, R.drawable.tiikeri,
                R.drawable.koira, R.drawable.kissoja, R.drawable.orava, R.drawable.tiikeri};
        cardTags = new int[8];
        flippedCards = 0;
        firstFlippedCard = -1;


        for (int i = 0; i < 8; i++) {
            String cardId = "card" + i;
            int resId = getResources().getIdentifier(cardId, "id", getPackageName());
            cards[i] = findViewById(resId);
            cards[i].setOnClickListener(this);
            cards[i].setImageResource(R.drawable.luonto_kortin_takaosa);
            cardTags[i] = -1;  // Tag -1 tarkoittaa käännettyä korttia
        }

        shuffleCards();

    }

    @Override
    public void onClick(View view) {
        int selectedCard = findCardIndex(view.getId());

        if (cardTags[selectedCard] == -1 && flippedCards < 2) {
            cards[selectedCard].setImageResource(cardImages[selectedCard]);
            cardTags[selectedCard] = cardImages[selectedCard];

            if (flippedCards == 0) {
                firstFlippedCard = selectedCard;
            }

            flippedCards++;

            if (flippedCards == 2) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkForMatch(selectedCard);
                    }
                }, 1000); // 1000 millisekuntia eli 1 sekunti
            }
        }
    }

    private int findCardIndex(int id) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private void shuffleCards() {
        // Yksinkertainen korttien sekoituslogiikka
        for (int i = 0; i < cardImages.length; i++) {
            int randomIndex = (int) (Math.random() * cardImages.length);

            // Vaihda paikkaa korttien välillä
            int temp = cardImages[i];
            cardImages[i] = cardImages[randomIndex];
            cardImages[randomIndex] = temp;
        }
    }

    private void checkForMatch(final int selectedCard) {
        if (cardTags[firstFlippedCard] == cardTags[selectedCard]) {




            // Tarkista, onko peli päättynyt
            if (isGameFinished()) {
                showGameOverDialog();
            } else {
                // Jos pelaaja löysi oikean parin, hänen vuoronsa jatkuu
                flippedCards = 0;
                // Ei vaihdeta pelaajaa, jotta sama pelaaja voi jatkaa vuoroaan
            }
        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    flipBackCards(selectedCard);
                }
            }, 50); // 500 millisekuntia eli 0.5 sekuntia
        }
    }

    private void flipBackCards(int selectedCard) {
        // Käännä kortit takaisin piiloon
        cards[firstFlippedCard].setImageResource(R.drawable.luonto_kortin_takaosa);
        cards[selectedCard].setImageResource(R.drawable.luonto_kortin_takaosa);

        flippedCards = 0;
        cardTags[firstFlippedCard] = -1;
        cardTags[selectedCard] = -1;


    }

    private boolean isGameFinished() {
        for (int tag : cardTags) {
            if (tag == -1) {
                // Kaikki kortit eivät ole vielä löydetty
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        // Nollaa kaikki pelimuuttujat ja päivitä näkymä
        flippedCards = 0;
        firstFlippedCard = -1;


        for (int i = 0; i < cards.length; i++) {
            cards[i].setImageResource(R.drawable.luonto_kortin_takaosa);
            cardTags[i] = -1;
        }

        shuffleCards();

    }





    private void showGameOverDialog() {
        // Luo ponnahdusikkuna pelin päättyessä
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Peli päättyi!");


        builder.setPositiveButton("Uudestaan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resetGame(); // Aloita uusi peli OK-painikkeesta
                kutsumainos();
                Kuinka_monta_pelia_sitten_mainos++;
                if (Kuinka_monta_pelia_sitten_mainos==5) {
                    AlustaUusiMainos();
                    Kuinka_monta_pelia_sitten_mainos=0;
                }
            }
        });

        builder.setCancelable(false); // Estä dialogin sulkeminen taustalle painamalla

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    // alle tulee mainos asiaa
    private void kutsumainos() {
        UnityAds.show(sivu1_pelaajia1.this, AdsId,new UnityAdsShowOptions(), showListener);
    }




    private void AlustaUusiMainos() {
        UnityAds.load(AdsId);    // lisää tietyn mainoksen ID
        UnityAds.show(sivu1_pelaajia1.this, AdsId,new UnityAdsShowOptions(), showListener);
    }










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


}



