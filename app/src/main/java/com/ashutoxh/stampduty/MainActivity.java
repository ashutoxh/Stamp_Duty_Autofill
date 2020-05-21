package com.ashutoxh.stampduty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static StampDutyPartyBean selectedPartyBean;
    public static String remarksString;
    private ArrayList<StampDutyPartyBean> partyBeanArrayList;
    private long back_pressed;
    private Toast exitToast;
    private Button submitButton;
    private EditText boeNoTxt;
    private EditText boeDateTxt;
    private EditText igmNoTxt;
    private EditText itemNoTxt;
    private EditText amountTxt;
    private String igmNoString;
    private String boeNoString;
    private String boeDateString;
    private String itemNoString;
    public static String amountString;
    public static String modeOfConsignment;
    private Button registrationButton;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        partyBeanArrayList = loadPartyBeanList();
        Log.d("DB", partyBeanArrayList.toString());
        Spinner partyDropDown;
        partyDropDown = findViewById(R.id.partyDropDown);
        partyDropDown.setOnItemSelectedListener(this);

        ArrayList<String> nameList = new ArrayList<>();
        for (StampDutyPartyBean bean : partyBeanArrayList) {
            nameList.add(bean.getKEY_NAME());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameList);
        partyDropDown.setAdapter(dataAdapter);

        submitButton = findViewById(R.id.submitButton);
        registrationButton = findViewById(R.id.registrationButtonMain);

        boeNoTxt = findViewById(R.id.boeNoTxt);
        boeDateTxt = findViewById(R.id.boeDateTxt);
        igmNoTxt = findViewById(R.id.igmNoTxt);
        itemNoTxt = findViewById(R.id.itemNoTxt);
        amountTxt = findViewById(R.id.amountTxt);


        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("chk", "id" + checkedId);

                if (checkedId == R.id.seaRadioButton) {
                    modeOfConsignment = "SEA";
                } else if (checkedId == R.id.airRadioButton) {
                    modeOfConsignment = "AIR";
                }
            }
        });

        RadioButton seaRadioButton = findViewById(R.id.seaRadioButton);
        seaRadioButton.setChecked(true);

        boeNoTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (boeNoTxt.getText().toString().length() != 7 || !boeNoTxt.getText().toString().matches("[0-9]+"))
                    boeNoTxt.setError("BOE NO should be a number and 7 digit long");
                else {
                    boeNoTxt.setError(null);
                    boeNoString = boeNoTxt.getText().toString();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boeDateTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (boeDateTxt.getText().toString().isEmpty()) {
                    boeDateTxt.setError("Please enter a date");
                } else {
                    boeDateTxt.setError(null);
                    boeDateString = boeDateTxt.getText().toString();
                }
            }
        });

        igmNoTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (igmNoTxt.getText().toString().length() != 7 || !igmNoTxt.getText().toString().matches("[0-9]+") || igmNoTxt.getText().toString().isEmpty()) {
                    igmNoTxt.setError("IGM NO should be a number and 7 digit long");
                } else {
                    igmNoTxt.setError(null);
                    igmNoString = igmNoTxt.getText().toString();
                }
            }
        });

        itemNoTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (itemNoTxt.getText().toString().isEmpty() || !itemNoTxt.getText().toString().matches("[0-9 ]+")) {
                    itemNoTxt.setError("ITEM NO should be a number");
                } else {
                    itemNoTxt.setError(null);
                    itemNoString = itemNoTxt.getText().toString();
                }
            }
        });

        amountTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (amountTxt.getText().toString().isEmpty() || !amountTxt.getText().toString().matches("[0-9]+")) {
                    amountTxt.setError("AMOUNT should be a number");
                } else {
                    amountTxt.setError(null);
                    amountString = amountTxt.getText().toString();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remarksString = "IMPORTED VIDE BOE NO " + boeNoString + " DT " + boeDateString
                        + " IGM NO " + igmNoString + " ITEM NO " + itemNoString;
                openStampDutyActivity();
            }
        });

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegistrationActivity();
            }
        });
    }

    public void openStampDutyActivity() {
        Intent intent = new Intent(this, StampDutyActivity.class);
        startActivity(intent);
    }

    public void openRegistrationActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        //intent.putExtra("selectedPartyBean", selectedPartyBean.toString());
        startActivity(intent);
    }

    public ArrayList<StampDutyPartyBean> loadPartyBeanList() {
        Cursor cursor = null;
        ArrayList<StampDutyPartyBean> partyBeanArrayList = new ArrayList<>();
        try {
            StampDutyDatabaseDaoImpl stampDutyDatabaseDaoImpl = new StampDutyDatabaseDaoImpl(this);
            SQLiteDatabase database = stampDutyDatabaseDaoImpl.getReadableDatabase();

            cursor = database.rawQuery("SELECT KEY_ID, KEY_NAME, PAN_NO, BLOCK_NO, ROAD, CITY, PIN FROM stampDutyPartyInfo", null);

            if (cursor != null) {
                cursor.moveToFirst();
            } else {
                Log.d("DB", "Null database");
            }
            do {
                System.out.println("Cursor " + cursor.getString(0));
                System.out.println("Cursor " + cursor.getString(1));
                partyBeanArrayList.add(new StampDutyPartyBean(
                        cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e("MainActivity", "loadPartyBeanList() : " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return partyBeanArrayList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        if (!item.equals("None")) {
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
        }

        //int partyID = Integer.parseInt(item.substring(0,item.indexOf("_")));
        for (int i = 0; i < partyBeanArrayList.size(); i++)
            if (partyBeanArrayList.get(i).getKEY_NAME() == item)
                selectedPartyBean = partyBeanArrayList.get(i);
        Log.d("DB", selectedPartyBean.toString());
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            this.finishAffinity();
            exitToast.cancel();
        } else {
            exitToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            exitToast.show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
