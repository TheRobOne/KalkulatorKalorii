package com.example.maciek.kalkulatorkalorii;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Klasa ustawien w ktorych mozna podgladnac baze danych oraz napisac maila do tworcy aplikacjii
 */
public class AUstawienia extends AppCompatActivity {

    private Toolbar toolbar;
    Button bPomoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_austawienia);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_aposilek);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bPomoc = (Button) findViewById(R.id.bPomoc);


        bPomoc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setType("plain/text");
                    sendIntent.setData(Uri.parse("kalkulatorkalorii.pomoc@gmail.com"));
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kalkulatorkalorii.pomoc@gmail.com"});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Problem z ...");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Witam\n\nMam problem z...");
                    startActivity(sendIntent);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Brak aplikacji Gmail na urządzeniu", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
