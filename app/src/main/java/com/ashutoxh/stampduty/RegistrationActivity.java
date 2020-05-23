package com.ashutoxh.stampduty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends Activity {

    private EditText regNameTxt;
    private EditText regPanTxt;
    private EditText regBlockTxt;
    private EditText regRoadTxt;
    private EditText regCityTxt;
    private EditText regPinTxt;
    private StampDutyPartyBean stampDutyPartyBean;

    /*private String regNameString;
    private String regPanString;
    private String regBlockString;
    private String regRoadString;
    private String regCityString;
    private String regPinString;*/

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regNameTxt = findViewById(R.id.regNameTxt);
        regPanTxt = findViewById(R.id.regPanTxt);
        regBlockTxt = findViewById(R.id.regBlockTxt);
        regRoadTxt = findViewById(R.id.regRoadTxt);
        regCityTxt = findViewById(R.id.regCityTxt);
        regPinTxt = findViewById(R.id.regPinTxt);
        Button registerButton = findViewById(R.id.regButton);
        context = getApplicationContext();

        stampDutyPartyBean = new StampDutyPartyBean();

        regNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (regNameTxt.getText().toString().isEmpty() || !regNameTxt.getText().toString().matches("[A-Za-z0-9 ]+"))
                    regNameTxt.setError("Only Alphabets and Numbers are allowed");
                else {
                    regNameTxt.setError(null);
                    stampDutyPartyBean.setKEY_NAME(regNameTxt.getText().toString().toUpperCase().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regPanTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (regPanTxt.getText().toString().isEmpty() || !regPanTxt.getText().toString().matches("[A-Za-z0-9  ]+"))
                    regPanTxt.setError("Only Alphabets and Numbers are allowed");
                else {
                    regPanTxt.setError(null);
                    stampDutyPartyBean.setPAN_NO(regPanTxt.getText().toString().toUpperCase().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regBlockTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (regBlockTxt.getText().toString().isEmpty() || !regBlockTxt.getText().toString().matches("[A-Za-z0-9  ]+"))
                    regBlockTxt.setError("Only Alphabets and Numbers are allowed");
                else {
                    regBlockTxt.setError(null);
                    stampDutyPartyBean.setBLOCK_NO(regBlockTxt.getText().toString().toUpperCase().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regRoadTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (regRoadTxt.getText().toString().isEmpty() || !regRoadTxt.getText().toString().matches("[A-Za-z0-9  ]+"))
                    regRoadTxt.setError("Only Alphabets and Numbers are allowed");
                else {
                    regRoadTxt.setError(null);
                    stampDutyPartyBean.setROAD(regRoadTxt.getText().toString().toUpperCase().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regCityTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (regCityTxt.getText().toString().isEmpty() || !regCityTxt.getText().toString().matches("[A-Za-z0-9  ]+"))
                    regCityTxt.setError("Only Alphabets and Numbers are allowed");
                else {
                    regCityTxt.setError(null);
                    stampDutyPartyBean.setCITY(regCityTxt.getText().toString().toUpperCase().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regPinTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (regPinTxt.getText().toString().length() != 6 || !regPinTxt.getText().toString().matches("[0-9]+"))
                    regPinTxt.setError("Only 6 digit numbers are allowed");
                else {
                    regPinTxt.setError(null);
                    stampDutyPartyBean.setPIN(regPinTxt.getText().toString().toUpperCase().trim());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //insertDataInLocal(regNameString, regPanString, regBlockString, regRoadString, regCityString, regPinString);
                    insertDataInFirebaseDB(stampDutyPartyBean);
                    Intent intent = new Intent(context, MainActivity.class);
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Log.e("RegistrationActivity", "registerButton : onClick() : " + e.getMessage());
                }
            }
        });
    }

    private void insertDataInFirebaseDB(StampDutyPartyBean stampDutyPartyBean) {
        DatabaseReference stampDutyDatabaseReference;
        try {
            stampDutyDatabaseReference = FirebaseDatabase.getInstance().getReference().child("StampDutyPartyInfo");
            stampDutyDatabaseReference.child(String.valueOf(stampDutyPartyBean.getKEY_NAME())).setValue(stampDutyPartyBean);
        } catch (Exception e) {
            Log.e("RegistrationActivity", "insertDataInFirebaseDB : " + e.getMessage());
        }
    }
}
