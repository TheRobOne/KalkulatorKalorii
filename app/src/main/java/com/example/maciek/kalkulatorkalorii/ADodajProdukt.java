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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maciek.kalkulatorkalorii.DataBase.CBazaProdukt;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.SQLException;

/**
 * Klasa ktora odpowiada za dodawanie produktow do bazy
 */
public class ADodajProdukt extends AppCompatActivity {

    CBazaProdukt dbProdukt;
    EditText etDodajProduktNazwa, etDodajProduktBialka, etDodajProduktTluszcze, etDodajProduktWeglowodany, etDodajProduktBarCode;
    Button bDodajProdukt, b1;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adodaj_produkt);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_adodaj_produkt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbProdukt = new CBazaProdukt(this);

        etDodajProduktNazwa = (EditText) findViewById(R.id.etDodajProduktNazwa);
        etDodajProduktBialka = (EditText) findViewById(R.id.etDodajProduktBialka);
        etDodajProduktTluszcze = (EditText) findViewById(R.id.etDodajProduktTluszcze);
        etDodajProduktWeglowodany = (EditText) findViewById(R.id.etDodajProduktWeglowodany);
        etDodajProduktBarCode = (EditText) findViewById(R.id.etDodajProduktBarCode);
        bDodajProdukt = (Button) findViewById(R.id.bDodProdukt);

        insertToDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dodaj_produkt, menu);
        return true;//
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_scanner) {
            actionScanner();
        }

        return super.onOptionsItemSelected(item);
    }

    public void actionScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Zeskanuj kod kreskowy");
        integrator.setResultDisplayDuration(0);
        integrator.setOrientation(1);
        integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }

    /**
     * dodawanie produktu do bazy, dane z edittextow
     */
    public void insertToDB() {
        bDodajProdukt.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        boolean isInserted = false;
                        if (etDodajProduktNazwa.getText().toString().equals("")) {
                            builderView("Puste pole nazwa", "Uzupełnij nazwę...");
                        } else {
                            try {
                                String nazwa = etDodajProduktNazwa.getText().toString();
                                double bialka = Double.parseDouble(etDodajProduktBialka.getText().toString());
                                double tluszcze = Double.parseDouble(etDodajProduktTluszcze.getText().toString());
                                double weglowodany = Double.parseDouble(etDodajProduktWeglowodany.getText().toString());
                                String barCode = etDodajProduktBarCode.getText().toString();

                                boolean ifNameExists = dbProdukt.chechIfExist(nazwa);

                                if (ifNameExists == true) {

                                    isInserted = dbProdukt.insertData(nazwa, bialka, tluszcze, weglowodany, barCode);
                                    resetEditTexts();
                                } else if (ifNameExists == false) {
                                    builderView("Nazwa już istnieje", "Podaj inną nazwę...");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Brak połączenia z bazą", Toast.LENGTH_SHORT).show();
                            }
                            if (isInserted == true)
                                Toast.makeText(getApplicationContext(), "Produkt dodany", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
        );
    }

    /**
     * metoda odpowiada za tworzenie builderView
     *
     * @param title tytul buildera
     * @param text  text w nim sie znajdujacy
     */
    public void builderView(String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ADodajProdukt.this);
        builder.setTitle(title);
        builder.setMessage(text);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    /**
     * resetuje edit texty po dodaniu produktu do bazy
     */
    public void resetEditTexts() {
        etDodajProduktNazwa.setText("");
        etDodajProduktBialka.setText("");
        etDodajProduktWeglowodany.setText("");
        etDodajProduktTluszcze.setText("");
        etDodajProduktBarCode.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String toast;
            if (result.getContents() == null) {
                toast = "Anulowano skanowanie";
            } else {
                //toast = "Scanned from fragment: " + result.getContents();
                boolean ifBarCodeExists = dbProdukt.chechIfBarCodeExist(result.getContents());
                if (ifBarCodeExists == false)
                    builderView("Produkt o tym kodzie już istnieje", "Zeskanuj inny produkt...");

                else {
                    etDodajProduktBarCode.setText(result.getContents());
                    Toast.makeText(this, "Uzupełnij dane produktu", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }
}
