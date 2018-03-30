package com.example.bloodline.forgeofempiresfpcalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    //TODO radiobutton készítése két lehetőséggel, egyik nyeremény biztosítása, másik profit megadásával mikor kell és mennyi belerakni hogy a megadott profitot el lehessen érni
    //TODO kalkláció a reklám alá, jelenlegi arany bemeneti paraméterrel és jelenlegi fp árával, hogy mennyi fpt tud még a felhasználó venni

    private static final String TAG = "MainActivity";
    private AdView mAdView;
    //Double variables
    private double dblyourFP;
    private double dblcompFP;
    private double dblbuildingFP;
    private double dblcurrentFP;
    private double dblrewardFP;
    private double dblarcbonus;
    private double dblrequiredFP;
    private double dblprofit;

    //Edit Texts
    private EditText edtyourFP;
    private EditText edtcompFP;
    private EditText edtbuildingFP;
    private EditText edtcurrentFP;
    private EditText edtrewardFP;
    private EditText edtarcbonus;
    private EditText edtrequiredFP;
    private EditText edtprofit;

    //Buttons
    private Button btnCalculate;

    //Radiobuttons
    private RadioGroup rdbgrp1;
    private RadioButton rdbprofit;
    private RadioButton rdbreward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        //on screen items define
        define_items();

        //on startup edittext wont be focused
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put parameters to double variable
                pull_params();
                //check parameters
                if (check_params()) {
                    if (rdbreward.isChecked()) {
                        calculate_reqFP();
                        calculate_profit();
                    } else if (rdbprofit.isChecked()) {

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Check input parameters!", Toast.LENGTH_LONG).show();
                }
            }
        });

        rdbgrp1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rdbreward.isChecked()) {
                    edtcurrentFP.setEnabled(true);
                    edtprofit.setEnabled(false);
                    edtrequiredFP.setEnabled(false);
                } else if (rdbprofit.isChecked()) {
                    edtcurrentFP.setEnabled(false);
                    edtprofit.setEnabled(true);
                }
            }
        });
    }

    private void calculate_profit() {
        dblprofit = Math.ceil(dblrewardFP * dblarcbonus) - dblrequiredFP - dblyourFP;
        edtprofit.setText(String.valueOf(dblprofit));
    }

    private void calculate_reqFP() {
        dblrequiredFP = Math.ceil((dblbuildingFP - ((dblcompFP - dblyourFP) + dblcurrentFP)) / 2 + (dblcompFP - dblyourFP));
        edtrequiredFP.setText(String.valueOf(dblrequiredFP));
    }


    private boolean check_params() {
        if (dblarcbonus == 0 || dblarcbonus > 100) {
            edtarcbonus.setBackgroundColor(Color.RED);
            return false;
        } else {
            edtarcbonus.setBackgroundColor(Color.GREEN);
        }

        if (dblyourFP + dblcompFP >= dblbuildingFP || dblyourFP >= dblbuildingFP || dblcompFP >= dblbuildingFP) {
            edtyourFP.setBackgroundColor(Color.RED);
            edtcompFP.setBackgroundColor(Color.RED);
            return false;
        } else {
            edtyourFP.setBackgroundColor(Color.GREEN);
            edtcompFP.setBackgroundColor(Color.GREEN);
        }
        return true;
    }

    private void pull_params() {
        dblyourFP = Double.parseDouble(edtyourFP.getText().toString());
        dblcompFP = Double.parseDouble(edtcompFP.getText().toString());
        dblbuildingFP = Double.parseDouble(edtbuildingFP.getText().toString());
        dblcurrentFP = Double.parseDouble(edtcurrentFP.getText().toString());
        dblrewardFP = Double.parseDouble(edtrewardFP.getText().toString());
        dblarcbonus = Double.parseDouble(edtarcbonus.getText().toString());
    }

    private void define_items() {
        edtyourFP = (EditText) findViewById(R.id.yourFP);
        edtcompFP = (EditText) findViewById(R.id.compFP);
        edtbuildingFP = (EditText) findViewById(R.id.totalFP);
        edtcurrentFP = (EditText) findViewById(R.id.currentFP);
        edtrewardFP = (EditText) findViewById(R.id.rewardFP);
        edtarcbonus = (EditText) findViewById(R.id.arcbonus);
        edtrequiredFP = (EditText) findViewById(R.id.requiredFP);
        edtprofit = (EditText) findViewById(R.id.profit);
        btnCalculate = (Button) findViewById(R.id.calculate);
        mAdView = (AdView) findViewById(R.id.adView);
        rdbprofit = (RadioButton) findViewById(R.id.rdbprofit);
        rdbreward = (RadioButton) findViewById(R.id.rdbreward);
        rdbgrp1 = (RadioGroup) findViewById(R.id.rdbgrp1);
        edtcurrentFP.setEnabled(true);
        edtprofit.setEnabled(false);
        edtrequiredFP.setEnabled(false);
    }

    private void clear_items() {
        edtyourFP.setText(null);
        edtcompFP.setText(null);
        edtbuildingFP.setText(null);
        edtcurrentFP.setText(null);
        edtrewardFP.setText(null);
        edtarcbonus.setText(null);
        edtrequiredFP.setText(null);
        edtprofit.setText(null);

    }
}
