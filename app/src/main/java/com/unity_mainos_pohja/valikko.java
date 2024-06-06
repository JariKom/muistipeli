package com.unity_mainos_pohja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class valikko extends AppCompatActivity {

    Button pelaajia1, pelaajia2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valikko);

        pelaajia1 = findViewById(R.id.pelaajaNappi1);
        pelaajia2 = findViewById(R.id.pelaajaNappi2);


        pelaajia1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent yksinpeli = new Intent(valikko.this, sivu1_pelaajia1.class);

                startActivity(yksinpeli);
            }
        });


        pelaajia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent kaksinpeli = new Intent(valikko.this, sivu2.class);

                startActivity(kaksinpeli);
            }
        });




    }
}