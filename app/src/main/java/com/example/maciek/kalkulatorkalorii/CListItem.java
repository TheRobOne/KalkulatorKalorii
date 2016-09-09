package com.example.maciek.kalkulatorkalorii;

/**
 * Created by Maciek on 04.06.2016.
 */

/**
 * Klasa tworzaca liste potrzebna do wyswietlania produktow wchodzacych w skald posilku
 */
public class CListItem {
    private String produkt;
    private String gramy;
    private String makro;
    private String numer;
    private boolean isChecked;

    public CListItem(String produkt, String gramy, String makro, String numer) {
        this.produkt = produkt;
        this.gramy = gramy;
        this.makro = makro;
        this.numer = numer;
        isChecked = false;
    }

    public String getGramy() {
        return gramy;
    }

    public void setGramy(String gramy) {
        this.gramy = gramy;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String getProdukt() {
        return produkt;
    }

    public void setProdukt(String produkt) {
        this.produkt = produkt;
    }

    public String getMakro() {
        return makro;
    }

    public void setMakro(String makro) {
        this.makro = makro;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }
}
