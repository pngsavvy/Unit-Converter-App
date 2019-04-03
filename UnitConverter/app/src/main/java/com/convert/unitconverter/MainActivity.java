/*
To add units to the unit converter you have to both update the strings.xml
file with the new units you are adding and the UnitConverter.java file in
the initializeUnits function. Make sure that the spelling or abreviation
for the units you add match in both files.
 */


package com.convert.unitconverter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    private EditText txtIn;     // user enters text here
    private TextView txtOut;    // displays result here
    private Button distancesBtn, weightsBtn, volumesBtn;
    private UnitConverter u = new UnitConverter();      // does processing to get result
    private Spinner unitsIn, unitsOut;      // stors different options for the user to choose
    private int positionOfSecondItem = 0, positionOfFirstItem = 0;      // saves postion of item selected to pass into Unit converter
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###.##########");     // formats output
    private String lastInput = "";    // variable that helps with displaying the correct toast message at the correct time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = getBaseContext();

        u.setUnitType("distances");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeIds(); // link variables with widgets

        configureSpinners(ArrayAdapter.createFromResource(this, R.array.distances, android.R.layout.simple_spinner_item));
        
        weightsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.weights, android.R.layout.simple_spinner_item);
                configureSpinners(adapter);
                u.setUnitType("weights");
            }
        });
        
        distancesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.distances, android.R.layout.simple_spinner_item);
                configureSpinners(adapter);
                u.setUnitType("distances");
            }
        });

        volumesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.volumes, android.R.layout.simple_spinner_item);
                configureSpinners(adapter);
                u.setUnitType("volumes");
            }
        });

        txtIn.addTextChangedListener(new TextWatcher() { // updates output as soon as user enters data
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateOutput();     // performs calulation and updates output
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    
    private void configureSpinners(ArrayAdapter<CharSequence> adapter){
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
        distancesBtn = findViewById(R.id.distancesBtn);
        weightsBtn = findViewById(R.id.weightsBtn);
        volumesBtn = findViewById(R.id.volumesBtn);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinnerIn) {
            positionOfFirstItem = position;
            updateOutput();     // performs calulation and updates output

        }else if(parent.getId() == R.id.spinnerOut){
            positionOfSecondItem = position;
            updateOutput();     // performs calulation and updates output
        }
    }

    private void updateOutput(){
        double output = 0;
        if(!TextUtils.isEmpty(txtIn.getText()) && !lastInput.contains(txtIn.getText().toString())) { // keeps toast from displaying if text is empty or you are backspacing non nuymerical values
            try {
                output = u.calculate(Double.parseDouble(txtIn.getText().toString()), unitsIn.getItemAtPosition(positionOfFirstItem).toString(), unitsOut.getItemAtPosition(positionOfSecondItem).toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "enter numbers only", Toast.LENGTH_SHORT).show();
                lastInput = txtIn.getText().toString();
            }
            String outString = "" + decimalFormat.format(output);
            txtOut.setText(outString);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
