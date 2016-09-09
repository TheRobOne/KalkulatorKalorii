package com.example.maciek.kalkulatorkalorii;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maciek.kalkulatorkalorii.DataBase.CBazaPosilek;
import com.example.maciek.kalkulatorkalorii.DataBase.CBazaProdukt;
import com.example.maciek.kalkulatorkalorii.DataBase.CBazaProfil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa w ktorej tworzone sa nowe posilki z produktow z bazy
 */
public class APosilek extends AppCompatActivity implements View.OnClickListener {

    private CBazaPosilek dbPosilek;
    private CBazaProdukt dbProdukt;
    private CBazaProfil dbProfil;
    private Toolbar toolbar;
    private Spinner spinner;
    private EditText etPosilekGramy;
    private Button bPosilekDodajProdukt;
    private ListView lvList;
    private ArrayList<CListItem> array;
    private CListAdapter adapter;
    private TextView tvPosilekKalorie, tvPosilekBialka, tvPosilekTluszcze, tvPosilekWeglowodany, tvPosilekGramy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aposilek);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_aposilek);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final int posNumber = setMealNumber();

        dbPosilek = new CBazaPosilek(this);
        dbProdukt = new CBazaProdukt(this);
        dbProfil = new CBazaProfil(this);

        //inicjazlizacja elementów layoutu
        spinner = (Spinner) findViewById(R.id.spinner);
        etPosilekGramy = (EditText) findViewById(R.id.etPosilekGramy);
        bPosilekDodajProdukt = (Button) findViewById(R.id.bPosilekDodajProdukt);
        lvList = (ListView) findViewById(R.id.lvList);
        tvPosilekKalorie = (TextView) findViewById(R.id.tvPosilekKalorie);
        tvPosilekBialka = (TextView) findViewById(R.id.tvPosilekBiaka);
        tvPosilekTluszcze = (TextView) findViewById(R.id.tvPosilekTluszcze);
        tvPosilekWeglowodany = (TextView) findViewById(R.id.tvPosilekWeglowodany);
        tvPosilekGramy = (TextView) findViewById(R.id.tvPosilekGramy);
        //==========================================================================


        loadSpinnerData();

        array = new ArrayList<CListItem>();
        adapter = new CListAdapter(this, R.layout.posilek_listview, array);
        lvList.setAdapter(adapter);


        loadFromDB(posNumber);
        macroStats(posNumber);

        bPosilekDodajProdukt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPosilekToList(posNumber);
                macroStats(posNumber);
            }
        });
    }

    /**
     * ustala numer posliku
     *
     * @return zwraca numer posilku
     */
    public int setMealNumber() {
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("intVariableName", 0);
        if (intValue == 0)
            Toast.makeText(this, "No pieknie", Toast.LENGTH_SHORT).show();
        else {
            if (intValue == R.id.bJadlospisPosilek1) {
                setTitle("Posiłek 1");
                intValue = 1;
            } else if (intValue == R.id.bJadlospisPosilek2) {
                setTitle("Posiłek 2");
                intValue = 2;
            } else if (intValue == R.id.bJadlospisPosilek3) {
                setTitle("Posiłek 3");
                intValue = 3;
            } else if (intValue == R.id.bJadlospisPosilek4) {
                setTitle("Posiłek 4");
                intValue = 4;
            } else if (intValue == R.id.bJadlospisPosilek5) {
                setTitle("Posiłek 5");
                intValue = 5;
            } else if (intValue == R.id.bJadlospisPosilek6) {
                setTitle("Posiłek 6");
                intValue = 6;
            }

        }
        return intValue;
    }

    /**
     * laduje produkty do posilu z bazy
     *
     * @param posNum numer posilku
     */
    public void loadFromDB(int posNum) {
        String date = CPomocnicza.getDate();
        if (dbPosilek.chechIfExist(date, posNum) == false)
            showList(posNum);
    }

    /**
     * laduje produkty do listy
     *
     * @param posNumer numer posilku
     */
    public void showList(int posNumer) {
        String date = CPomocnicza.getDate();
        List<String> names = dbPosilek.populateListView(date, 2, posNumer);
        List<String> gramy = dbPosilek.populateListView(date, 3, posNumer);
        List<String> wegle = dbPosilek.populateListView(date, 4, posNumer);
        List<String> tluszcze = dbPosilek.populateListView(date, 5, posNumer);
        List<String> bialko = dbPosilek.populateListView(date, 6, posNumer);
        List<String> kalorie = dbPosilek.populateListView(date, 7, posNumer);
        String numer = new Integer(posNumer).toString();

        for (int i = 0; i < names.size(); i++) {
            String makro = "B: " + bialko.get(i) + "\nT: " + tluszcze.get(i) + "\nW: " + wegle.get(i) + "\nK: " + kalorie.get(i);
            CListItem item = new CListItem(names.get(i), gramy.get(i), makro, numer);
            array.add(0, item);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * po klikniecuy buttonu dodaje nowe produkty do listy i tym samym do bazy
     *
     * @param posNumer numer posilku
     */
    public void addPosilekToList(int posNumer) {
        if (etPosilekGramy.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(APosilek.this);
            builder.setTitle("Puste pole");
            builder.setMessage("Uzupełnij gramy...");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        } else {
            final String posilek = spinner.getSelectedItem().toString();
            final String gramyString = etPosilekGramy.getText().toString();
            final double gramy = Double.parseDouble(etPosilekGramy.getText().toString());
            // final String makro = dbProdukt.getAllMacro(posilek, Double.parseDouble(gramy));
            final double makroBialka = dbProdukt.getSingleMacro("PROD_BIALKA", posilek, gramy);
            final double makroTluszcze = dbProdukt.getSingleMacro("PROD_TLUSZCZE", posilek, gramy);
            final double makroWeglowodany = dbProdukt.getSingleMacro("PROD_WEGLOWODANY", posilek, gramy);
            final double makroKalorie = dbProdukt.getSingleMacro("PROD_KALORIE", posilek, gramy);
            final String makro = "B: " + makroBialka + "\nT: " + makroTluszcze + "\nW: " + makroWeglowodany + "\nK: " + makroKalorie;
            final String date = CPomocnicza.getDate();
            String numer = new Integer(posNumer).toString();


            CListItem item = new CListItem(posilek, gramyString, makro, numer);
            array.add(0, item);
            adapter.notifyDataSetChanged();

            insertToDB(posNumer, posilek, gramy, makroBialka, makroTluszcze, makroWeglowodany, date);
            etPosilekGramy.setText("");
            etPosilekGramy.requestFocus();
        }
    }

    /**
     * laduje produkty z bazy do spinnera
     */
    private void loadSpinnerData() {
        List<String> names = dbProdukt.getAllNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }


    /**
     * dodaje produkty do bazy danych
     *
     * @param posilekNumer   numer posilku
     * @param prodNazwa      nazwa produktu
     * @param posGramy       ilosc gramow produktu
     * @param posBialka      gramy bialka
     * @param posTluszcze    gramy tluszzy
     * @param posWelgowodany gramy wegle
     * @param data           aktualna data
     */
    public void insertToDB(int posilekNumer, String prodNazwa, double posGramy, double posBialka, double posTluszcze, double posWelgowodany, String data) {
        try {
            boolean insert = dbPosilek.insertData(posilekNumer, prodNazwa, posGramy, posBialka, posTluszcze, posWelgowodany, data);

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Brak połączenia z bazą", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * podaje ilosc poszczeglonych makroelemetnow
     *
     * @param posNumber numer posilku
     */
    public void macroStats(int posNumber) {
        final String date = CPomocnicza.getDate();
        double gramySuma = dbPosilek.sumOfMacrosInSingleMeal("POS_GRAMY", date, posNumber);
        double bialkaSuma = dbPosilek.sumOfMacrosInSingleMeal("POS_BIALKA", date, posNumber);
        double tluszczeSuma = dbPosilek.sumOfMacrosInSingleMeal("POS_TLUSZCZE", date, posNumber);
        double wegleSuma = dbPosilek.sumOfMacrosInSingleMeal("POS_WEGLOWODANY", date, posNumber);
        double kalorieSuma = dbPosilek.sumOfMacrosInSingleMeal("POS_KALORIE", date, posNumber);

        double bialkaMax = dbProfil.sumOfMacros("PROFIL_BIALKA_MAX");
        double tluszczeMax = dbProfil.sumOfMacros("PROFIL_TLUSZCZE_MAX");
        double weglowodanyMax = dbProfil.sumOfMacros("PROFIL_WEGLOWODANY_MAX");
        double kalorieMax = CPomocnicza.round(bialkaMax * 4 + tluszczeMax * 4 + weglowodanyMax * 4, 1);

        tvPosilekBialka.setText("Białka: " + String.valueOf(CPomocnicza.round(bialkaSuma, 1)) + "/" + bialkaMax);
        tvPosilekTluszcze.setText("Tłuszcze: " + String.valueOf(CPomocnicza.round(tluszczeSuma, 1)) + "/" + tluszczeMax);
        tvPosilekWeglowodany.setText("Węglowodany: " + String.valueOf(CPomocnicza.round(wegleSuma, 1)) + "/" + weglowodanyMax);
        tvPosilekGramy.setText("Gramy: " + String.valueOf(CPomocnicza.round(gramySuma, 1)));
        tvPosilekKalorie.setText("Kalorie: " + String.valueOf(CPomocnicza.round(kalorieSuma, 1)) + "/" + kalorieMax);
    }

    /**
     * metoda potrzebna przy ustawieniu odpowiedniego elementu spinnera
     *
     * @param spinner  spinner
     * @param myString element kroy ma byc ustawiony
     * @return zwraca indeks elementu
     */
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aposilek, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int posNumber = setMealNumber();
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            if (array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    if (i > array.size()) {
                        break;
                    }
                    if (array.get(i).isChecked()) {
                        dbPosilek.deleteFromList(array.get(i).getProdukt(), array.get(i).getGramy(), array.get(i).getNumer());
                        array.remove(i);
                        adapter.notifyDataSetChanged();
                        macroStats(posNumber);
                        continue;
                    }
                }
            }
        } else if (id == R.id.action_scanner) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            integrator.setPrompt("Zeskanuj kod kreskowy");
            integrator.setResultDisplayDuration(0);
            integrator.setOrientation(1);
            integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.initiateScan();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String toast;
            if (result.getContents() == null) {
                toast = "Anulowano skanowanie";
            } else {

                String code = result.getContents();
                String nazwa = dbProdukt.getNameByBarCode(code);
                toast = "Zeskanowano: " + nazwa;
                int idx = getIndex(spinner, nazwa);
                spinner.setSelection(idx);
            }

            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
    }
}
