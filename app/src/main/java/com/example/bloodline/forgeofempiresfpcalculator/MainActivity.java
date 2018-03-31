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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    //TODO radiobutton készítése két lehetőséggel, egyik nyeremény biztosítása, másik profit megadásával mikor kell és mennyi belerakni hogy a megadott profitot el lehessen érni
    //TODO kalkláció a reklám alá, jelenlegi arany bemeneti paraméterrel és jelenlegi fp árával, hogy mennyi fpt tud még a felhasználó venni

    private AdView mAdView;
    private AdView mAdView2;
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
    private EditText edtgoldamount;
    private EditText edtfpcurrprice;
    private EditText edtbuyableFP;

    //Buttons
    private Button btnCalculate;
    private Button btnCalculate2;

    //Radiobuttons
    private RadioGroup rdbgrp1;
    private RadioButton rdbprofit;
    private RadioButton rdbreward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //on screen items define
        define_items();

        AdRequest adRequest2 = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView2.loadAd(adRequest2);

        //on startup edittext wont be focused
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check empty
                if (check_empty()) {
                    //put parameters to double variable
                    pull_params();
                    //check parameters
                    if (check_params()) {
                        if (rdbreward.isChecked()) {
                            calculate_reqFP();
                            calculate_profit();
                        } else if (rdbprofit.isChecked()) {
                            //másik kalkuláció
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Check input parameters!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Check input parameters!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCalculate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtfpcurrprice.getText().toString().matches("") && !edtgoldamount.getText().toString().matches("")) {
                    double dblcurrprice = Double.parseDouble(edtfpcurrprice.getText().toString());
                    double dblgoldamount = Double.parseDouble(edtgoldamount.getText().toString());
                    double dblbuyablefp = calculate_buyablefp(dblcurrprice, dblgoldamount);
                    edtbuyableFP.setText(String.valueOf(dblbuyablefp));
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
                    clear_items();
                } else if (rdbprofit.isChecked()) {
                    edtcurrentFP.setEnabled(false);
                    edtprofit.setEnabled(true);
                    clear_items();
                }
            }
        });
    }

    private double calculate_buyablefp(double currprice, double goldamount) {
        double db = 0;
        while (goldamount - currprice >= 0) {
            goldamount = goldamount - currprice;
            currprice += 50;
            db++;
        }
        return db;
    }

    private boolean check_empty() {
        boolean seged;
        seged = true;
        //if empty fills with value
        fill_empty(edtyourFP, "0");
        fill_empty(edtcompFP, "0");
        fill_empty(edtcurrentFP, "0");
        fill_empty(edtarcbonus, "1");

        if (edtbuildingFP.getText().toString().matches("")) {
            edtbuildingFP.setBackgroundColor(Color.RED);
            seged = false;
        }
        if (edtrewardFP.getText().toString().matches("")) {
            edtrewardFP.setBackgroundColor(Color.RED);
            seged = false;
        }

        return seged;
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
        boolean seged;
        seged = true;

        if (dblarcbonus == 0 || dblarcbonus > 100) {
            edtarcbonus.setBackgroundColor(Color.RED);
            seged = false;
        } else {
            edtarcbonus.setBackgroundColor(Color.GREEN);
        }
        if (dblyourFP + dblcompFP >= dblbuildingFP || dblyourFP >= dblbuildingFP || dblcompFP >= dblbuildingFP) {
            edtyourFP.setBackgroundColor(Color.RED);
            edtcompFP.setBackgroundColor(Color.RED);
            seged = false;
        } else {
            edtyourFP.setBackgroundColor(Color.GREEN);
            edtcompFP.setBackgroundColor(Color.GREEN);
        }
        if (dblcurrentFP >= dblbuildingFP) {
            edtcurrentFP.setBackgroundColor(Color.RED);
            seged = false;
        } else {
            edtcurrentFP.setBackgroundColor(Color.GREEN);
        }
        return seged;
    }

    private void fill_empty(EditText edt, String value) {
        if (edt.getText().toString().matches("")) {
            edt.setText(value);
        }
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
        edtyourFP = findViewById(R.id.yourFP);
        edtcompFP = findViewById(R.id.compFP);
        edtbuildingFP = findViewById(R.id.totalFP);
        edtcurrentFP = findViewById(R.id.currentFP);
        edtrewardFP = findViewById(R.id.rewardFP);
        edtarcbonus = findViewById(R.id.arcbonus);
        edtrequiredFP = findViewById(R.id.requiredFP);
        edtprofit = findViewById(R.id.profit);
        edtgoldamount = findViewById(R.id.edtgoldammount);
        edtfpcurrprice = findViewById(R.id.edtfpcurrprice);
        edtbuyableFP = findViewById(R.id.buyablefp);
        btnCalculate = findViewById(R.id.calculate);
        btnCalculate2 = findViewById(R.id.calculate2);
        mAdView = findViewById(R.id.adView);
        mAdView2 = findViewById(R.id.adView2);
        rdbprofit = findViewById(R.id.rdbprofit);
        rdbreward = findViewById(R.id.rdbreward);
        rdbgrp1 = findViewById(R.id.rdbgrp1);
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
