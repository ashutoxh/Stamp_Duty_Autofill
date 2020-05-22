package com.ashutoxh.stampduty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

    private EditText regNameTxt;
    private EditText regPanTxt;
    private EditText regBlockTxt;
    private EditText regRoadTxt;
    private EditText regCityTxt;
    private EditText regPinTxt;

    private String regNameString;
    private String regPanString;
    private String regBlockString;
    private String regRoadString;
    private String regCityString;
    private String regPinString;

    private Context context;
    private Button registerButton;


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
        registerButton = findViewById(R.id.regButton);
        context = getApplicationContext();

        regNameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (regNameTxt.getText().toString().isEmpty() || !regNameTxt.getText().toString().matches("[A-Za-z0-9 ]+"))
                    regNameTxt.setError("Only Alphabets and Numbers are allowed");
                else {
                    regNameTxt.setError(null);
                    regNameString = regNameTxt.getText().toString();
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
                    regPanString = regPanTxt.getText().toString();
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
                    regBlockString = regBlockTxt.getText().toString();
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
                    regRoadString = regRoadTxt.getText().toString();
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
                    regCityString = regCityTxt.getText().toString();
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
                    regPinString = regPinTxt.getText().toString();
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
                    StampDutyDatabaseDaoImpl stampDutyDatabaseDaoImpl = new StampDutyDatabaseDaoImpl(context);
                    SQLiteDatabase database = stampDutyDatabaseDaoImpl.getReadableDatabase();
                    stampDutyDatabaseDaoImpl.insertData(regNameString, regPanString, regBlockString, regRoadString, regCityString, regPinString, database);

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
}
