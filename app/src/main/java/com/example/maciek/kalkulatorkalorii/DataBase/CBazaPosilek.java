package com.example.maciek.kalkulatorkalorii.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciek on 09.06.2016.
 * Klasa odpowiada za tabele POSILKI w bazie danych oraz operacje na tej tabeli
 */
public class CBazaPosilek {
    private CDatabaseManager dbManager;
    private SQLiteDatabase dbPosilek;

    public CBazaPosilek(Context context) {
        dbManager = new CDatabaseManager(context);
    }

    /**
     * otwierqanie bazy
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        dbPosilek = dbManager.getWritableDatabase();
    }

    /**
     * zamykanie bazy
     */
    public void close() {
        dbPosilek.close();
    }

    /**
     * dodawanie nowych posilkow do bazy
     *
     * @param posilekNumer   numer posilku
     * @param prodNazwa      nazwa produktu
     * @param posGramy       ilosc gramow produktu
     * @param posBialka      gramy bialka
     * @param posTluszcze    gramy tlusczy
     * @param posWelgowodany gramy wegli
     * @param data           aktualan data
     * @return
     * @throws SQLException
     */
    public boolean insertData(int posilekNumer, String prodNazwa, double posGramy, double posBialka, double posTluszcze, double posWelgowodany, String data) throws SQLException {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.POSILEK_NUMER, posilekNumer);
        cv.put(CDatabaseManager.PROD_NAZWA, prodNazwa);
        cv.put(CDatabaseManager.POSILEK_GRAMY, posGramy);
        cv.put(CDatabaseManager.POSILEK_WEGLOWODANY, posWelgowodany);
        cv.put(CDatabaseManager.POSILEK_TLUSZCZE, posTluszcze);
        cv.put(CDatabaseManager.POSILEK_BIALKO, posBialka);
        cv.put(CDatabaseManager.POSILEK_KALORIE, (posBialka * 4 + posTluszcze * 9 + posWelgowodany * 4));
        cv.put(CDatabaseManager.POSILEK_DATA, data);
        long result = dbPosilek.insert(CDatabaseManager.TABLE_POSILKI, null, cv);
        close();
        if (result == -1)
            return false;
        else
            return true;
    }

    /**
     * dodaje posilki z bazy do listy
     *
     * @param date        aktualna data
     * @param numOfColumn numer kolumny
     * @param posNumber   numer posilku
     * @return
     */
    public List<String> populateListView(String date, int numOfColumn, int posNumber) {
        List<String> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM POSILKI WHERE POS_NUMER=" + posNumber + "";
        dbPosilek = dbManager.getReadableDatabase();
        Cursor cursor = dbPosilek.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(numOfColumn));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return list;
    }

    /**
     * sprawdza czy istnieje juz posilek na aktulny dzien
     *
     * @param date   aktualna data
     * @param posNum numer posilka
     * @return
     */
    public boolean chechIfExist(String date, int posNum) {
        boolean exists = false;
        dbPosilek = dbManager.getReadableDatabase();
        String Query = "Select * from POSILKI where POS_DATA = " + "'" + date + "' AND POS_NUMER=" + posNum + "";
        Cursor cursor = dbPosilek.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            exists = true;
        }
        close();
        return exists;
    }

    /**
     * usuwa produkty zposilku
     *
     * @param name  nazwa produktu
     * @param gramy
     * @param numer
     */
    public void deleteFromList(String name, String gramy, String numer) {
        try {
            open();
            double grams = Double.parseDouble(gramy);
            int number = Integer.parseInt(numer);
            dbPosilek.execSQL("DELETE FROM POSILKI WHERE PROD_NAZWA='" + name + "' AND POS_GRAMY=" + grams + " AND POS_NUMER=" + number);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }

    /**
     * suma makroelementow w posilkach
     *
     * @param macro nazwa makroelementu
     * @param date  data
     * @return
     */
    public double sumOfMacros(String macro, String date) {
        double grams = 0;
        dbPosilek = dbManager.getReadableDatabase();
        String Query = "Select " + macro + " from POSILKI WHERE POS_DATA='" + date + "'";
        Cursor cursor = dbPosilek.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                grams += cursor.getDouble(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return grams;
    }

    /**
     * suma makroelementow w posilkau
     *
     * @param macro   nazwa makro
     * @param date    data
     * @param mealNum numer posilu
     * @return
     */
    public double sumOfMacrosInSingleMeal(String macro, String date, int mealNum) {
        double grams = 0;
        dbPosilek = dbManager.getReadableDatabase();
        String Query = "Select " + macro + " from POSILKI WHERE POS_DATA='" + date + "' AND POS_NUMER=" + mealNum;
        Cursor cursor = dbPosilek.rawQuery(Query, null);
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
