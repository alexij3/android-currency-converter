package com.buzilov.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        final Spinner currencySpinnerFrom = findViewById(R.id.currencyFrom);
        final Spinner currencySpinnerTo = findViewById(R.id.currencyTo);
        final EditText currencyFromValueText = findViewById(R.id.currencyFromValue);
        final EditText currencyToValueText = findViewById(R.id.currencyToValue);
        final Button convertButton = findViewById(R.id.convertButton);

        convertButton.setOnClickListener(v -> new ChangeCurrencyToValueTask().execute(currencyFromValueText.getText().toString(),
                currencySpinnerFrom.getSelectedItem().toString(),
                currencySpinnerTo.getSelectedItem().toString()));

    }

    private enum Currency {
        USD, EUR, JPY, GBP, CAD, RUB, BRL, AUD, BGN, RON
    }

    public static String[] getCurrencyNames() {
        return Stream.of(Currency.values())
                .map(Currency::name)
                .toArray(String[]::new);

    }

    class ChangeCurrencyToValueTask extends AsyncTask<String, Integer, Double> {
        @Override
        protected Double doInBackground(String... strings) {
            Double oldValueFrom = Double.parseDouble(strings[0]);
            String currencyFrom = strings[1];
            String currencyTo = strings[2];
            Double newValue = null;
            HttpURLConnection con = null;
            try {
                String rate = "rates";
                URL url = new URL("https://api.exchangeratesapi.io/latest?symbols=" + currencyTo + "&base=" + currencyFrom);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(in);
                JsonNode priceNode = root.path(rate).path(currencyTo);

                newValue = (oldValueFrom * priceNode.doubleValue());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return newValue;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            final EditText currencyToValueText   = findViewById(R.id.currencyToValue);
            currencyToValueText.setText(aDouble.toString());
        }
    }
}
