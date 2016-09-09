package com.example.maciek.kalkulatorkalorii.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Maciek on 19.06.2016.
 * Klasa obslugujaca tabele PROFIL
 */
public class CBazaProfil {

    private CDatabaseManager dbManager;
    private SQLiteDatabase dbProfil;

    public CBazaProfil(Context context) {
        dbManager = new CDatabaseManager(context);
    }

    /**
     * otwiera baze
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        dbProfil = dbManager.getWritableDatabase();
    }

    /**
     * zamyka baze
     */
    public void close() {
        dbProfil.close();
    }

    /**
     * dodaje dane do bazy
     *
     * @param wiek        wiek
     * @param waga        wage
     * @param bialka      zapotrzboewnie na bialka
     * @param tluszcze    tluszcze
     * @param welgowodany wegle
     * @return
     * @throws SQLException
     */
    public boolean insertData(int wiek, double waga, double bialka, double tluszcze, double welgowodany) throws SQLException {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.PROFIL_WIEK, wiek);
        cv.put(CDatabaseManager.PROFIL_WAGA, waga);
        cv.put(CDatabaseManager.PROFIL_BIALKA_MAX, bialka);
        cv.put(CDatabaseManager.PROFIL_TLUSZCZE_MAX, tluszcze);
        cv.put(CDatabaseManager.PROFIL_WEGLOWODANY_MAX, welgowodany);
        cv.put(CDatabaseManager.PROFIL_BIALKA_CUR, bialka);
        cv.put(CDatabaseManager.PROFIL_TLUSZCZE_CUR, tluszcze);
        cv.put(CDatabaseManager.PROFIL_WEGLOWODANY_CUR, welgowodany);
        long result = dbProfil.insert(CDatabaseManager.TABLE_PROFIL, null, cv);
        close();
        if (result == -1)
            return false;
        else
            return true;
    }

    /**
     * sprqawdza czy jest juz uzytwkonik w bazie
     *
     * @param wiek wiek uzytkownia
     * @return
     */
    public boolean checkIfDBIsEmpty(int wiek) {
        boolean isEmpty = false;
        dbProfil = dbManager.getReadableDatabase();
        String Query = "Select * from PROFIL where PROFIL_WIEK=" + wiek;
        Cursor cursor = dbProfil.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            isEmpty = true;
        }
        close();
        return isEmpty;
    }

    /**
     * aktualizuje baze
     *
     * @param wiek        wiek
     * @param waga        wage
     * @param bialka      zapotrzboeqanie na bialko
     * @param tluszcze    tluszcze
     * @param welgowodany welge
     */
    public void updateDB(int wiek, double waga, double bialka, double tluszcze, double welgowodany) {
        try {
            open();
            dbProfil.execSQL("UPDATE PROFIL SET PROFIL_WIEK=" + wiek + ", PROFIL_WAGA=" + waga + ", PROFIL_BIALKA_MAX=" + bialka + ", PROFIL_TLUSZCZE_MAX=" +
                    tluszcze + ", PROFIL_WEGLOWODANY_MAX=" + welgowodany + " WHERE _id=1");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * ustawia dane w edit textach
     *
     * @param column
     * @return
     */
    public String setEditTexts(int column) {
        String value = "";
        dbProfil = dbManager.getReadableDatabase();
        String Query = "Select * from PROFIL where _id=1";
        Cursor cursor = dbProfil.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                value += cursor.getString(column);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return value;
    }

    /**
     * zapotrzebowanie na kazy z makroelementow
     *
     * @param macro nazwa makro
     * @return
     */
    public double sumOfMacros(String macro) {
        double grams = 0;
        dbProfil = dbManager.getReadableDatabase();
        String Query = "Select " + macro + " from PROFIL WHERE _id=1";
        Cursor cursor = dbProfil.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                grams += cursor.getDouble(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return grams;
    }

}
