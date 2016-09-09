package com.example.maciek.kalkulatorkalorii;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.maciek.kalkulatorkalorii.DataBase.CBazaPosilek;

/**
 * Glowna klasa programu
 * odpowiada za nawigacje po posilkach
 * z jej poziomy wchodzis sie takze do inncyh aktywonsci
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    ImageButton bPosilek1, bPosilek2, bPosilek3, bPosilek4, bPosilek5, bPosilek6;
    TextView tvMainB1, tvMainB2, tvMainB3, tvMainB4, tvMainB5, tvMainB6, tvMainT1, tvMainT2, tvMainT3, tvMainT4, tvMainT5, tvMainT6,
            tvMainW1, tvMainW2, tvMainW3, tvMainW4, tvMainW5, tvMainW6, tvMainK1, tvMainK2, tvMainK3, tvMainK4, tvMainK5, tvMainK6;
    CBazaPosilek dbPosilek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //inicjalizacja elementow aktywnosci
        bPosilek1 = (ImageButton) findViewById(R.id.bJadlospisPosilek1);
        bPosilek1.setOnClickListener(this);
        bPosilek2 = (ImageButton) findViewById(R.id.bJadlospisPosilek2);
        bPosilek2.setOnClickListener(this);
        bPosilek3 = (ImageButton) findViewById(R.id.bJadlospisPosilek3);
        bPosilek3.setOnClickListener(this);
        bPosilek4 = (ImageButton) findViewById(R.id.bJadlospisPosilek4);
        bPosilek4.setOnClickListener(this);
        bPosilek5 = (ImageButton) findViewById(R.id.bJadlospisPosilek5);
        bPosilek5.setOnClickListener(this);
        bPosilek6 = (ImageButton) findViewById(R.id.bJadlospisPosilek6);
        bPosilek6.setOnClickListener(this);

        tvMainB1 = (TextView) findViewById(R.id.tvMainB1);
        tvMainB2 = (TextView) findViewById(R.id.tvMainB2);
        tvMainB3 = (TextView) findViewById(R.id.tvMainB3);
        tvMainB4 = (TextView) findViewById(R.id.tvMainB4);
        tvMainB5 = (TextView) findViewById(R.id.tvMainB5);
        tvMainB6 = (TextView) findViewById(R.id.tvMainB6);

        tvMainT1 = (TextView) findViewById(R.id.tvMainT1);
        tvMainT2 = (TextView) findViewById(R.id.tvMainT2);
        tvMainT3 = (TextView) findViewById(R.id.tvMainT3);
        tvMainT4 = (TextView) findViewById(R.id.tvMainT4);
        tvMainT5 = (TextView) findViewById(R.id.tvMainT5);
        tvMainT6 = (TextView) findViewById(R.id.tvMainT6);

        tvMainW1 = (TextView) findViewById(R.id.tvMainW1);
        tvMainW2 = (TextView) findViewById(R.id.tvMainW2);
        tvMainW3 = (TextView) findViewById(R.id.tvMainW3);
        tvMainW4 = (TextView) findViewById(R.id.tvMainW4);
        tvMainW5 = (TextView) findViewById(R.id.tvMainW5);
        tvMainW6 = (TextView) findViewById(R.id.tvMainW6);

        tvMainK1 = (TextView) findViewById(R.id.tvMainK1);
        tvMainK2 = (TextView) findViewById(R.id.tvMainK2);
        tvMainK3 = (TextView) findViewById(R.id.tvMainK3);
        tvMainK4 = (TextView) findViewById(R.id.tvMainK4);
        tvMainK5 = (TextView) findViewById(R.id.tvMainK5);
        tvMainK6 = (TextView) findViewById(R.id.tvMainK6);
        //==========================================================

        dbPosilek = new CBazaPosilek(this);

        macroStats(1, tvMainB1, tvMainT1, tvMainW1, tvMainK1);
        macroStats(2, tvMainB2, tvMainT2, tvMainW2, tvMainK2);
        macroStats(3, tvMainB3, tvMainT3, tvMainW3, tvMainK3);
        macroStats(4, tvMainB4, tvMainT4, tvMainW4, tvMainK4);
        macroStats(5, tvMainB5, tvMainT5, tvMainW5, tvMainK5);
        macroStats(6, tvMainB6, tvMainT6, tvMainW6, tvMainK6);

    }


    /**
     * Oblicza sume gramow poszczegolnych posilkow i wyswietla wyniki w glownej aktywnosci
     *
     * @param posNumber   numer posilka
     * @param Bialka      gramy bialka
     * @param Tluszcze    gr tluszczy
     * @param Weglowodany gr wegli
     * @param Kalorie     gr kalorii
     */
    public void macroStats(int posNumber, TextView Bialka, TextView Tluszcze, TextView Weglowodany, TextView Kalorie) {
        final String date = CPomocnicza.getDate();
        double bialkaSuma = dbPosilek.sumOfMacrosInSingleMeal("POS_BIALKA", date, posNumber);
        double tluszczeSuma = dbPosilek.sumOfMacrosInSingleMeal("POS_TLUSZCZE", date, posNumber);
        double wegleSuma = dbPosilek.sumOfMacrosInSingleMeal("POS_WEGLOWODANY", date, posNumber);
        double kalorieSuma = dbPosilek.sumOfMacrosInSingleMeal("POS_KALORIE", date, posNumber);


        Bialka.setText("B: " + String.valueOf(CPomocnicza.round(bialkaSuma, 1)));
        Tluszcze.setText("T: " + String.valueOf(CPomocnicza.round(tluszczeSuma, 1)));
        Weglowodany.setText("W: " + String.valueOf(CPomocnicza.round(wegleSuma, 1)));
        Kalorie.setText("K: " + String.valueOf(CPomocnicza.round(kalorieSuma, 1)));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, AUstawienia.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_dodaj_produkt) {

            Intent intent = new Intent(this, ADodajProdukt.class);
            startActivity(intent);

        } else if (id == R.id.nav_moj_profil) {
            Intent intent = new Intent(this, AProfile.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, APosilek.class);
        intent.putExtra("intVariableName", v.getId());
        startActivity(intent);
    }


}
