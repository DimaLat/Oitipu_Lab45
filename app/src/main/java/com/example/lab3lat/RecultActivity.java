package com.example.lab3lat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RecultActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    TextView tv, tv2, tv3;
    Button btnRestart;
    EditText edName;
    DatabaseReference mDataBase;
    String TOP_KEY = "Top";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recult);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        edName=(EditText)findViewById(R.id.editName);
        mDataBase = FirebaseDatabase.getInstance().getReference(TOP_KEY);
        tv = (TextView)findViewById(R.id.tvres);
        tv2 = (TextView)findViewById(R.id.tvres2);
        tv3 = (TextView)findViewById(R.id.tvres3);
        btnRestart = (Button) findViewById(R.id.btnRestart);

        StringBuffer sb = new StringBuffer();
        sb.append("Правильных ответов: " + QuestionsActivity.correct + "\n");
        StringBuffer sb2 = new StringBuffer();
        sb2.append("Ошибочных ответов: " + QuestionsActivity.wrong + "\n");
        StringBuffer sb3 = new StringBuffer();
        sb3.append(QuestionsActivity.correct);
        tv.setText(sb);
        tv2.setText(sb2);
        tv3.setText(sb3);



        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(in);
                QuestionsActivity.correct=0;
                QuestionsActivity.wrong=0;

            }
        });
    }

    public void onClickSave(View view){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        String id = mDataBase.getKey();
        String userName = edName.getText().toString();
        String points = tv3.getText().toString();
        Top newTop = new Top(id, userName, points);
        if (!TextUtils.isEmpty(userName)){
            mDataBase.push().setValue(newTop);
            Toast.makeText(getApplicationContext(), "Ваш результат сохранен", Toast.LENGTH_SHORT).show();
        } else{
            edName.setError("Введите имя");
        }


    }
    public void onClickTop(View view){
        Intent i = new Intent(getApplicationContext(), TopActivity.class);
        startActivity(i);

    }

}