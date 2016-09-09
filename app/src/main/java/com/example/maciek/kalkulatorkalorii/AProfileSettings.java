package com.example.maciek.kalkulatorkalorii;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maciek.kalkulatorkalorii.DataBase.CBazaProfil;

import java.sql.SQLException;

/**
 * Klasa ktora pozwala na zmiane wartosci wagi i wieku oraz ustawieniu zapotrzbeowania
 */
public class AProfileSettings extends AppCompatActivity {

    CBazaProfil dbProfil;
    private Toolbar toolbar;
    private EditText etProfileSettingsWiek, etProfileSettingsWaga, etProfileSettingsBialka, etProfileSettingsTluszcze, etProfileSettingsWeglowodany;
    private Button bProfileSettingsZapisz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprofile_settings);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_aprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbProfil = new CBazaProfil(this);

        etProfileSettingsWiek = (EditText) findViewById(R.id.etProfileSettingsWiek);
        etProfileSettingsWaga = (EditText) findViewById(R.id.etProfileSettingsWaga);
        etProfileSettingsBialka = (EditText) findViewById(R.id.etProfileSettingsBialka);
        etProfileSettingsTluszcze = (EditText) findViewById(R.id.etProfileSettingsTluszcze);
        etProfileSettingsWeglowodany = (EditText) findViewById(R.id.etProfileSettingsWeglowodany);
        bProfileSettingsZapisz = (Button) findViewById(R.id.bProfileSettingsZapisz);

        insertToDB();
        setEidtTexts();
        updateDB();
    }

    /**
     * metoda opowiadajaca za ladowanie do bazy ustawien profilu
     */
    public void insertToDB() {
        try {
            boolean isEmpty = dbProfil.checkIfDBIsEmpty(0);
            if (isEmpty)
                dbProfil.insertData(0, 0, 0, 0, 0);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Brak połączenie z bazą", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * metoda uaktualniajaca baze
     */
    public void updateDB() {
        bProfileSettingsZapisz.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbProfil.updateDB(Integer.parseInt(etProfileSettingsWiek.getText().toString()), Double.parseDouble(etProfileSettingsWaga.getText().toString()),
                                Double.parseDouble(etProfileSettingsBialka.getText().toString()), Double.parseDouble(etProfileSettingsTluszcze.getText().toString()),
                                Double.parseDouble(etProfileSettingsWeglowodany.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Zapisano", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /**
     * ustawia edit Texty po wejsciu w aktywnosc
     */
    public void setEidtTexts() {
        etProfileSettingsWiek.setText(dbProfil.setEditTexts(1));
        etProfileSettingsWaga.setText(dbProfil.setEditTexts(2));
        etProfileSettingsBialka.setText(dbProfil.setEditTexts(3));
        etProfileSettingsTluszcze.setText(dbProfil.setEditTexts(4));
        etProfileSettingsWeglowodany.setText(dbProfil.setEditTexts(5));
    }
}
