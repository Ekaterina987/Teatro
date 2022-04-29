package com.example.beeradviser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;

import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView marcas;
    private Spinner color;
    private ExpertoCerveza experto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        experto = new ExpertoCerveza();
        marcas = (TextView) findViewById(R.id.marcasCerveza);
        color = (Spinner) findViewById(R.id.color);
        /*color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String valor = String.valueOf(color.getSelectedItem());
                List<String> marcasArray = (ArrayList<String>) experto.getBrands(valor);
                StringBuilder brandsFormatted = new StringBuilder();
                for (String brand : marcasArray) {
                    brandsFormatted.append(brand).append('\n');
                }
                marcas.setText("Te recomendamos cerveza " + valor + " de las marcas:\n" + brandsFormatted);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


    }
    public void onClickEncuentraCerveza(View vista){
        String valor = String.valueOf(color.getSelectedItem());
        List<String> marcasArray = (ArrayList<String>) experto.getBrands(valor);
        StringBuilder brandsFormatted = new StringBuilder();
        for (String brand : marcasArray) {
            brandsFormatted.append(brand).append('\n');
        }
        marcas.setText("Te recomendamos cerveza " + valor + " de las marcas:\n" + brandsFormatted);
    }

}