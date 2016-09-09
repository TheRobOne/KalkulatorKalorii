package com.example.maciek.kalkulatorkalorii.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maciek on 24.05.2016.
 * Klasa obsulugjaca baze danych
 */
public class CDatabaseManager extends SQLiteOpenHelper {

    private static final int DB_WERSJA = 3;

    //NAZWA TABELI PRODUKTY I POLA
    public static final String TABLE_PRODUKTY = "PRODUKTY";

    public static final String PRODUKT_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String PRODUKT_NAZWA = "PROD_NAZWA";
    public static final String NAZWA_OPTIONS = "TEXT NOT NULL";

    public static final String PRODUKT_BIALKA = "PROD_BIALKA";
    public static final String BIALKA_OPTIONS = "REAL NOT NULL";

    public static final String PRODUKT_TLUSZCZE = "PROD_TLUSZCZE";
    public static final String TLUSZCZE_OPTIONS = "REAL NOT NULL";

    public static final String PRODUKT_WEGLOWODANY = "PROD_WEGLOWODANY";
    public static final String WEGLOWODANY_OPTIONS = "REAL NOT NULL";

    public static final String PRODUKT_KALORIE = "PROD_KALORIE";
    public static final String KALORIE_OPTIONS = "REAL NOT NULL";

    public static final String PRODUKT_KOD = "PROD_KOD";
    public static final String KOD_OPTIONS = "TEXT";
    //===================================================================
    //NAZWA TAEBELI POSILKI I POLA
    public static final String TABLE_POSILKI = "POSILKI";

    public static final String POSILEK_ID = "_id";
    public static final String POSILEK_ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String POSILEK_NUMER = "POS_NUMER";
    public static final String POSILEK_NUMER_OPTIONS = "INTEGER NOT NULL";

    public static final String PROD_NAZWA = "PROD_NAZWA";
    public static final String PROD_NAZWA_OPTIONS = "TEXT NOT NULL";

    public static final String POSILEK_GRAMY = "POS_GRAMY";
    public static final String POSILEK_GRAMY_OPTIONS = "REAL NOT NULL";

    public static final String POSILEK_WEGLOWODANY = "POS_WEGLOWODANY";
    public static final String POSILEK_WEGLOWODANY_OPTIONS = "REAL NOT NULL";

    public static final String POSILEK_TLUSZCZE = "POS_TLUSZCZE";
    public static final String POSILEK_TLUSZCZE_OPTIONS = "REAL NOT NULL";

    public static final String POSILEK_BIALKO = "POS_BIALKA";
    public static final String POSILEK_BIALKO_OPTIONS = "REAL NOT NULL";

    public static final String POSILEK_KALORIE = "POS_KALORIE";
    public static final String POSILEK_KALORIE_OPTIONS = "REAL NOT NULL";

    public static final String POSILEK_DATA = "POS_DATA";
    public static final String POSILEK_DATA_OPTIONS = "TEXT NOT NULL";

    //===================================================================

    //NAZWA TAEBELI PROFIL I POLA
    public static final String TABLE_PROFIL = "PROFIL";

    public static final String PROFIL_ID = "_id";
    public static final String PROFIL_ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";

    public static final String PROFIL_WIEK = "PROFIL_WIEK";
    public static final String PROFIL_WIEK_OPTIONS = "INTEGER NOT NULL";

    public static final String PROFIL_WAGA = "PROFIL_WAGA";
    public static final String PROFIL_WAGA_OPTIONS = "REAL NOT NULL";

    public static final String PROFIL_BIALKA_MAX = "PROFIL_BIALKA_MAX";
    public static final String PROFIL_BIALKA_MAX_OPTIONS = "REAL NOT NULL";

    public static final String PROFIL_TLUSZCZE_MAX = "PROFIL_TLUSZCZE_MAX";
    public static final String PROFIL_TLUSZCZE_MAX_OPTIONS = "REAL NOT NULL";

    public static final String PROFIL_WEGLOWODANY_MAX = "PROFIL_WEGLOWODANY_MAX";
    public static final String PROFIL_WEGLOWODANY_MAX_OPTIONS = "REAL NOT NULL";

    public static final String PROFIL_BIALKA_CUR = "PROFIL_BIALKA_CUR";
    public static final String PROFIL_BIALKA_CUR_OPTIONS = "REAL NOT NULL";

    public static final String PROFIL_TLUSZCZE_CUR = "PROFIL_TLUSZCZE_CUR";
    public static final String PROFIL_TLUSZCZE_CUR_OPTIONS = "REAL NOT NULL";

    public static final String PROFIL_WEGLOWODANY_CUR = "PROFIL_WEGLOWODANY_CUR";
    public static final String PROFIL_WEGLOWODANY_CUR_OPTIONS = "REAL NOT NULL";

    //===================================================================

    private static final String DB_NAZWA = "kalkkalorii.db"; //nazwa pliku w którym będzie przetrzymywana baza danych

    private static final String CREATE_TABLE_PRODUKTY = "CREATE TABLE " + TABLE_PRODUKTY + " ( " + PRODUKT_ID + " " + ID_OPTIONS +
            ", " + PRODUKT_NAZWA + " " + NAZWA_OPTIONS + ", " + PRODUKT_BIALKA + " " + BIALKA_OPTIONS +
            ", " + PRODUKT_TLUSZCZE + " " + TLUSZCZE_OPTIONS + ", " + PRODUKT_WEGLOWODANY + " " + WEGLOWODANY_OPTIONS +
            ", " + PRODUKT_KALORIE + " " + KALORIE_OPTIONS + ", " + PRODUKT_KOD + " " + KOD_OPTIONS + ");";

    private static final String CREATE_TABLE_POSILKI = "CREATE TABLE " + TABLE_POSILKI + " ( " + POSILEK_ID + " " + POSILEK_ID_OPTIONS +
            ", " + POSILEK_NUMER + " " + POSILEK_NUMER_OPTIONS + ", " + PROD_NAZWA + " " + PROD_NAZWA_OPTIONS +
            ", " + POSILEK_GRAMY + " " + POSILEK_GRAMY_OPTIONS + ", " + POSILEK_WEGLOWODANY + " " + POSILEK_WEGLOWODANY_OPTIONS +
            ", " + POSILEK_TLUSZCZE + " " + POSILEK_TLUSZCZE_OPTIONS + ", " + POSILEK_BIALKO + " " + POSILEK_BIALKO_OPTIONS +
            ", " + POSILEK_KALORIE + " " + POSILEK_KALORIE_OPTIONS + ", " + POSILEK_DATA + " " + POSILEK_DATA_OPTIONS + ");";

    private static final String CREATE_TABLE_PROFIL = "CREATE TABLE " + TABLE_PROFIL + " ( " + PROFIL_ID + " " + PROFIL_ID_OPTIONS +
            ", " + PROFIL_WIEK + " " + PROFIL_WIEK_OPTIONS + ", " + PROFIL_WAGA + " " + PROFIL_WAGA_OPTIONS +
            ", " + PROFIL_BIALKA_MAX + " " + PROFIL_BIALKA_MAX_OPTIONS + ", " + PROFIL_TLUSZCZE_MAX + " " + PROFIL_TLUSZCZE_MAX_OPTIONS +
            ", " + PROFIL_WEGLOWODANY_MAX + " " + PROFIL_WEGLOWODANY_MAX_OPTIONS + ", " + PROFIL_BIALKA_CUR + " " + PROFIL_BIALKA_CUR_OPTIONS +
            ", " + PROFIL_TLUSZCZE_CUR + " " + PROFIL_TLUSZCZE_CUR_OPTIONS + ", " + PROFIL_WEGLOWODANY_CUR + " " + PROFIL_WEGLOWODANY_CUR_OPTIONS + ");";


    public CDatabaseManager(Context context) {
        super(context, DB_NAZWA, null, DB_WERSJA);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROFIL);
        db.execSQL(CREATE_TABLE_PRODUKTY);
        db.execSQL(CREATE_TABLE_POSILKI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSILKI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUKTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFIL);
        onCreate(db);
    }


}
