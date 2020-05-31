package com.buzilov.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner currencySpinnerFrom = findViewById(R.id.currencyFrom);
        final Spinner currencySpinnerTo = findViewById(R.id.currencyTo);

        final List<String> currencyValues = Arrays.asList(getCurrencyNames());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, currencyValues);

        currencySpinnerFrom.setAdapter(arrayAdapter);
        currencySpinnerTo.setAdapter(arrayAdapter);

        final EditText currencyFromValueText = findViewById(R.id.currencyFromValue);
        final EditText currencyToValueText = findViewById(R.id.currencyToValue);

        currencyFromValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        currencyToValueText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private enum Currency {

        USD, EUR, JPY, GBP, CAD, RUB, UAH, BYR, MDL, RON

    }

    public static String[] getCurrencyNames() {
        return Stream.of(Currency.values())
                .map(Currency::name)
                .toArray(String[]::new);

    }
}
