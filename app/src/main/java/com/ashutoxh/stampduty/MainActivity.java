package com.ashutoxh.stampduty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static StampDutyPartyBean selectedPartyBean;
    public static String remarksString;
    public static String amountString;
    public static String modeOfConsignment;
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

    private boolean isDropDownProper = false;
    private boolean isBoeNoProper = false;
    private boolean isBoeDateProper = false;
    private boolean isIgmNoProper = false;
    private boolean isItemNoProper = false;
    private boolean isAmountProper = false;

    private StampDutyPartyBean stampDutyPartyBean;
    private Spinner partyDropDown;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        partyDropDown = findViewById(R.id.partyDropDown);
        partyDropDown.setOnItemSelectedListener(this);

        partyBeanArrayList = loadPartyBeanList();

        submitButton = findViewById(R.id.submitButton);
        submitButton.setEnabled(false);
        Button registrationButton = findViewById(R.id.registrationButtonMain);

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
                if (boeNoTxt.getText().toString().length() != 7 || !boeNoTxt.getText().toString().matches("[0-9]+")) {
                    boeNoTxt.setError("BOE NO should be a number and 7 digit long");
                    isBoeNoProper = false;
                } else {
                    boeNoTxt.setError(null);
                    boeNoString = boeNoTxt.getText().toString().trim();
                    isBoeNoProper = true;
                    submitButton.setEnabled(isDropDownProper && isBoeDateProper && isIgmNoProper && isItemNoProper && isAmountProper);
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
                    isBoeDateProper = false;
                } else {
                    boeDateTxt.setError(null);
                    boeDateString = boeDateTxt.getText().toString().trim();
                    isBoeDateProper = true;
                    submitButton.setEnabled(isDropDownProper && isBoeNoProper && isIgmNoProper && isItemNoProper && isAmountProper);
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
                    isIgmNoProper = false;
                } else {
                    igmNoTxt.setError(null);
                    igmNoString = igmNoTxt.getText().toString().trim();
                    isIgmNoProper = true;
                    submitButton.setEnabled(isDropDownProper && isBoeNoProper && isBoeDateProper && isItemNoProper && isAmountProper);
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
                    isItemNoProper = false;
                } else {
                    itemNoTxt.setError(null);
                    itemNoString = itemNoTxt.getText().toString().trim();
                    isItemNoProper = true;
                    submitButton.setEnabled(isDropDownProper && isBoeNoProper && isBoeDateProper && isIgmNoProper && isAmountProper);
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
                    isAmountProper = false;
                } else {
                    amountTxt.setError(null);
                    amountString = amountTxt.getText().toString().trim();
                    isAmountProper = true;
                    submitButton.setEnabled(isDropDownProper && isBoeNoProper && isBoeDateProper && isIgmNoProper && isItemNoProper);
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
        final ArrayList<StampDutyPartyBean> partyBeanArrayList = new ArrayList<>();
        stampDutyPartyBean = new StampDutyPartyBean();
        final Context context = getApplicationContext();
        //Default i.e. None
        stampDutyPartyBean.setPAN_NO("");
        stampDutyPartyBean.setBLOCK_NO("");
        stampDutyPartyBean.setKEY_NAME("None");
        stampDutyPartyBean.setCITY("");
        stampDutyPartyBean.setPIN("");
        stampDutyPartyBean.setROAD("");
        partyBeanArrayList.add(stampDutyPartyBean);

        try {
            DatabaseReference stampDutyDatabaseReference = FirebaseDatabase.getInstance().getReference().child("StampDutyPartyInfo");
            stampDutyDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map<String, StampDutyPartyBean> stampDutyDataMap = (Map<String, StampDutyPartyBean>) dataSnapshot.getValue();
                    if (stampDutyDataMap != null) {
                        for (Map.Entry<String, StampDutyPartyBean> entry : stampDutyDataMap.entrySet()) {
                            Map singleParty = (Map) entry.getValue();
                            if (singleParty != null) {
                                stampDutyPartyBean = new StampDutyPartyBean();
                                stampDutyPartyBean.setPAN_NO(singleParty.get("pan_NO").toString());
                                stampDutyPartyBean.setBLOCK_NO(singleParty.get("block_NO").toString());
                                stampDutyPartyBean.setKEY_NAME(singleParty.get("key_NAME").toString());
                                stampDutyPartyBean.setCITY(singleParty.get("city").toString());
                                stampDutyPartyBean.setPIN(singleParty.get("pin").toString());
                                stampDutyPartyBean.setROAD(singleParty.get("road").toString());
                                partyBeanArrayList.add(stampDutyPartyBean);
                                Log.d("DB", "partyBeanArrayList loop : " + partyBeanArrayList.toString());
                            }
                        }
                    }
                    ArrayList<String> nameList = new ArrayList<>();
                    for (StampDutyPartyBean bean : partyBeanArrayList) {
                        nameList.add(bean.getKEY_NAME());
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nameList);
                    partyDropDown.setAdapter(dataAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Log.d("DB", "partyBeanArrayList before : " + partyBeanArrayList.toString());
        } catch (Exception e) {
            Log.e("MainActivity", "loadPartyBeanList() : " + e.getMessage());
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
            isDropDownProper = true;
            submitButton.setEnabled(isBoeNoProper && isBoeDateProper && isIgmNoProper && isItemNoProper && isAmountProper);
        } else {
            isDropDownProper = false;
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
