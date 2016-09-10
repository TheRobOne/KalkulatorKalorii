package com.example.maciek.kalkulatorkalorii;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Maciek on 04.06.2016.
 * Adapter do listy produktów
 */
public class CListAdapter extends ArrayAdapter<CListItem> {

    private Activity context;
    private int id;
    ArrayList<CListItem> array;

    public CListAdapter(Activity context, int resource, ArrayList<CListItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.id = resource;
        this.array = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(id, null);
        }
        final CListItem item = array.get(position);
        TextView tvPosilekNazwaProduktu = (TextView) convertView.findViewById(R.id.tvPosilekNazwaProduktu);
        TextView tvPosilekGramy = (TextView) convertView.findViewById(R.id.tvPosilekGramy);
        TextView tvPosilekMakro = (TextView) convertView.findViewById(R.id.tvPosilekMakro);
        CheckBox chbPosilek = (CheckBox) convertView.findViewById(R.id.chbPosliek);
        chbPosilek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setIsChecked(isChecked);
            }
        });


        tvPosilekNazwaProduktu.setText(item.getProdukt());
        tvPosilekGramy.setText(item.getGramy());
        tvPosilekMakro.setText(item.getMakro());
        chbPosilek.setChecked(item.isChecked());
        return convertView;
    }
}
