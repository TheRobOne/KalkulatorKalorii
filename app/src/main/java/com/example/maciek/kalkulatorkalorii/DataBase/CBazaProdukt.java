package com.example.maciek.kalkulatorkalorii.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maciek on 24.05.2016.
 * Klasa, obsługująca tabele PRODUKTY
 */
public class CBazaProdukt {
    private CDatabaseManager dbManager;
    private SQLiteDatabase dbProdukt;

    public CBazaProdukt(Context context) {
        dbManager = new CDatabaseManager(context);
    }

    /**
     * otwieranie bazy danych potrzebnej do operacji z produktami
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        dbProdukt = dbManager.getWritableDatabase();

    }

    /**
     * zamykanie bazy danych
     */
    public void close() {
        dbProdukt.close();
    }

    /**
     * Dodawanie nowych produktów do bazy danych
     *
     * @param nazwa       nazwa produktu
     * @param bialka      ilosc bialka na 100 gram produktu
     * @param tluszcze    ilosc tluszczy na 100 gram produktu
     * @param welgowodany ilosc weglowodanow na 100 gram produktu
     * @param kod         kod kreskowy produktu, defaultowo 0
     * @return zwraca informacje o pomyslnym badz nie dodaniu produktu
     * @throws SQLException
     */
    public boolean insertData(String nazwa, double bialka, double tluszcze, double welgowodany, String kod) throws SQLException {
        open();
        ContentValues cv = new ContentValues();
        cv.put(CDatabaseManager.PRODUKT_NAZWA, nazwa);
        cv.put(CDatabaseManager.PRODUKT_BIALKA, bialka);
        cv.put(CDatabaseManager.PRODUKT_TLUSZCZE, tluszcze);
        cv.put(CDatabaseManager.PRODUKT_WEGLOWODANY, welgowodany);
        cv.put(CDatabaseManager.PRODUKT_KALORIE, (bialka * 4 + tluszcze * 9 + welgowodany * 4));
        cv.put(CDatabaseManager.PRODUKT_KOD, kod);
        long result = dbProdukt.insert(CDatabaseManager.TABLE_PRODUKTY, null, cv);
        close();
        if (result == -1)
            return false;
        else
            return true;
    }


    /**
     * tworzenie listy nazw wszystkich produktów w bazie
     *
     * @return
     */
    public List<String> getAllNames() {
        List<String> names = new ArrayList<>();

        String selectQuery = "SELECT * FROM PRODUKTY ORDER BY PROD_NAZWA ASC";
        dbProdukt = dbManager.getReadableDatabase();
        Cursor cursor = dbProdukt.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return names;
    }


    /**
     * metoda pozwalajaca zaokroglac liczby
     *
     * @param value  liczba do zaokroglenia
     * @param places ilosc miejsc po przecinku
     * @return zwraca zaokrolona liczbe
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * zwraca ilosc gramow w podanym produkcie podanego makroelementu
     *
     * @param macro   nazwa makroelementu
     * @param produkt nazwa produktu z ktoego ma byc pobrana wartosc
     * @param gramy   na tyle gramow maja byc obliczone makroelementy
     * @return
     */
    public double getSingleMacro(String macro, String produkt, double gramy) {
        double makro = 0;
        double wspolczynnik = gramy / 100;

        String Query = "SELECT " + macro + " FROM PRODUKTY WHERE PROD_NAZWA=" + "'" + produkt + "'";
        dbProdukt = dbManager.getReadableDatabase();
        Cursor cursor = dbProdukt.rawQuery(Query, null);

        if (cursor.moveToFirst()) {
            do {
                makro += round(Double.parseDouble(cursor.getString(0)) * wspolczynnik, 1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return makro;
    }

    /**
     * sprawdza czy podany produkt istnieje juz w bazie
     *
     * @param nazwa nazwa sprawdzanego produtu
     * @return zwraca informacje czy produkt juz istnieje
     */
    public boolean chechIfExist(String nazwa) {
        boolean exists = false;
        dbProdukt = dbManager.getReadableDatabase();
        String Query = "Select * from PRODUKTY where PROD_Nazwa = " + "'" + nazwa + "'";
        Cursor cursor = dbProdukt.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            exists = true;
        }
        close();
        return exists;
    }

    /**
     * sprawdza czy podany kod kreskowy istnieje juz w bazie
     *
     * @param barCode kod kreskowy sprawdzany
     * @return zwraca informacje czy kod kreskowy juz istnieje
     */
    public boolean chechIfBarCodeExist(String barCode) {
        boolean exists = false;
        dbProdukt = dbManager.getReadableDatabase();
        String Query = "Select * from PRODUKTY where PROD_KOD = " + "'" + barCode + "'";
        Cursor cursor = dbProdukt.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            exists = true;
        }
        close();
        return exists;
    }


    /**
     * zwraca nazwe po podaniu kodu kreskowego
     *
     * @param barCode kod krekowy produktu
     * @return nazwa produktu
     */
    public String getNameByBarCode(String barCode) {
        String name = "";
        dbProdukt = dbManager.getReadableDatabase();
        String Query = "Select * from PRODUKTY where PROD_KOD = " + "'" + barCode + "'";
        Cursor cursor = dbProdukt.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                name += cursor.getString(1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return name;
    }


}
