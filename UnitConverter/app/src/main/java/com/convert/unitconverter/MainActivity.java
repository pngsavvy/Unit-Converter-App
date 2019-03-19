package com.convert.unitconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.*;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText txtIn;
    private TextView txtOut;
    private UnitConverter u = new UnitConverter();
    private Spinner unitsIn, unitsOut;
    private TextView txt;
    private Button btn;
    private int positionOfSecondItem = 0, positionOfFirstItem = 0;
    private String startingUnits = "";
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###.####");

    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeIds();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsIn.setAdapter(adapter);
        unitsIn.setOnItemSelectedListener(this);

        unitsIn.setOnItemSelectedListener(this);

        unitsOut.setAdapter(adapter);
        unitsOut.setOnItemSelectedListener(this);
    }

    private void initializeIds(){
        unitsIn = findViewById(R.id.spinnerIn);
        unitsOut = findViewById(R.id.spinnerOut);
        txtIn = (EditText) findViewById(R.id.txtIn);
        txtOut = (TextView) findViewById(R.id.txtOut);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinnerIn) {
            positionOfFirstItem = position;
            updateOutput();

        }else if(parent.getId() == R.id.spinnerOut){
            positionOfSecondItem = position;
            updateOutput();

        }
    }

    private void updateOutput(){
        double output = 0;
        try {
            output = u.calculate(Double.parseDouble(txtIn.getText().toString()), unitsIn.getItemAtPosition(positionOfFirstItem).toString(), unitsOut.getItemAtPosition(positionOfSecondItem).toString());
        } catch(NumberFormatException e){
            Toast.makeText(this, "enter numbers only", Toast.LENGTH_SHORT).show();
        }
        String outString = "" + decimalFormat.format(output);
        txtOut.setText(outString);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
