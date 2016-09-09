package com.example.maciek.kalkulatorkalorii;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.maciek.kalkulatorkalorii.DataBase.CBazaPosilek;
import com.example.maciek.kalkulatorkalorii.DataBase.CBazaProfil;

/**
 * Klasa odpowiada za wyswietlanie statystyk aktualnego spozycia
 */
public class AProfile extends AppCompatActivity {


    private Toolbar toolbar;
    CBazaPosilek dbPosilek;
    CBazaProfil dbProfil;
    TextView tvProfileBialka, tvProfileTluszcze, tvProfileWegle, tvProfileKalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprofile);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_aprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbPosilek = new CBazaPosilek(this);
        dbProfil = new CBazaProfil(this);
        tvProfileBialka = (TextView) findViewById(R.id.tvProfileBialka);
        tvProfileTluszcze = (TextView) findViewById(R.id.tvProfileTluszcze);
        tvProfileWegle = (TextView) findViewById(R.id.tvProfileWegle);
        tvProfileKalorie = (TextView) findViewById(R.id.tvProfileKalorie);

        String date = CPomocnicza.getDate();
        double bialkaCur = dbPosilek.sumOfMacros("POS_BIALKA", date);
        double tluszczeCur = dbPosilek.sumOfMacros("POS_TLUSZCZE", date);
        double weglowodanyCur = dbPosilek.sumOfMacros("POS_WEGLOWODANY", date);
        double kalorieCur = CPomocnicza.round(dbPosilek.sumOfMacros("POS_KALORIE", date), 1);

        double bialkaMax = dbProfil.sumOfMacros("PROFIL_BIALKA_MAX");
        double tluszczeMax = dbProfil.sumOfMacros("PROFIL_TLUSZCZE_MAX");
        double weglowodanyMax = dbProfil.sumOfMacros("PROFIL_WEGLOWODANY_MAX");
        double kalorieMax = CPomocnicza.round(bialkaMax * 4 + tluszczeMax * 4 + weglowodanyMax * 4, 1);

        tvProfileBialka.setText("Białka: " + CPomocnicza.round(bialkaCur, 1) + "/" + bialkaMax);
        tvProfileTluszcze.setText("Tłuszcze: " + CPomocnicza.round(tluszczeCur, 1) + "/" + tluszczeMax);
        tvProfileWegle.setText("Węglowodany: " + CPomocnicza.round(weglowodanyCur, 1) + "/" + weglowodanyMax);
        tvProfileKalorie.setText("Kalorie: " + CPomocnicza.round(kalorieCur, 1) + "/" + kalorieMax);

        RoundCornerProgressBar progress1 = (RoundCornerProgressBar) findViewById(R.id.progress_1);
        progress1.setProgressColor(Color.parseColor("#1a8cff"));
        progress1.setProgressBackgroundColor(Color.parseColor("#66b3ff"));
        progress1.setMax(100);
        progress1.setProgress((float) (bialkaCur * 100 / bialkaMax));


        RoundCornerProgressBar progress2 = (RoundCornerProgressBar) findViewById(R.id.progress_2);
        progress2.setProgressColor(Color.parseColor("#1a8cff"));
        progress2.setProgressBackgroundColor(Color.parseColor("#66b3ff"));
        progress2.setMax(100);
        progress2.setProgress((float) (tluszczeCur * 100 / tluszczeMax));

        RoundCornerProgressBar progress3 = (RoundCornerProgressBar) findViewById(R.id.progress_3);
        progress3.setProgressColor(Color.parseColor("#1a8cff"));
        progress3.setProgressBackgroundColor(Color.parseColor("#66b3ff"));
        progress3.setMax(100);
        progress3.setProgress((float) (weglowodanyCur * 100 / weglowodanyMax));

        RoundCornerProgressBar progress4 = (RoundCornerProgressBar) findViewById(R.id.progress_4);
        progress4.setProgressColor(Color.parseColor("#4dff4d"));
        progress4.setProgressBackgroundColor(Color.parseColor("#00e600"));
        progress4.setMax(100);
        progress4.setProgress((float) (kalorieCur * 100 / kalorieMax));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aprofile, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AProfileSettings.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
