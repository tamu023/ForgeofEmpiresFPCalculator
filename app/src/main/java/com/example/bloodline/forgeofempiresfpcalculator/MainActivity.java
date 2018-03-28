package com.example.bloodline.forgeofempiresfpcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //on screen items define
        define_items();


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //put parameters to double variable
                pull_params();
                //check parameters
                check_params();
            }
        });
    }

    private void check_params() {

    }

    private void pull_params() {
        dblyourFP = Double.parseDouble(edtyourFP.toString());
        dblcompFP = Double.parseDouble(edtcompFP.toString());
        dblbuildingFP = Double.parseDouble(edtbuildingFP.toString());
        dblcurrentFP = Double.parseDouble(edtcurrentFP.toString());
        dblrewardFP = Double.parseDouble(edtrewardFP.toString());
        dblarcbonus = Double.parseDouble(edtarcbonus.toString());
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
    }
}
