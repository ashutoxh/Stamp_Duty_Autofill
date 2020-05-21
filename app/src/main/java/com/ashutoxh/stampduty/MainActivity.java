package com.ashutoxh.stampduty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner partyDropDown;
    public static StampDutyPartyBean selectedPartyBean;
    public static String boeNoString;
    public static String boeDateString;
    public static String igmNoString;
    public static String itemNoString;
    public static String amountString;
    private ArrayList<StampDutyPartyBean> partyBeanArrayList;
    private long back_pressed;
    private Toast exitToast;


    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        partyBeanArrayList = loadPartyBeanList();
        Log.d("DB", partyBeanArrayList.toString());

        partyDropDown = findViewById(R.id.partyDropDown);
        partyDropDown.setOnItemSelectedListener(this);

        ArrayList<String> nameList = new ArrayList<>();
        for (StampDutyPartyBean bean : partyBeanArrayList) {
            nameList.add(bean.getKEY_NAME());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nameList);
        partyDropDown.setAdapter(dataAdapter);

        /*try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(partyDropDown);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }*/

        EditText boeNoTxt = findViewById(R.id.boeNoTxt);
        EditText boeDateTxt = findViewById(R.id.boeDateTxt);
        EditText igmNoTxt = findViewById(R.id.igmNoTxt);
        EditText itemNoTxt = findViewById(R.id.itemNoTxt);
        EditText amountTxt = findViewById(R.id.amountTxt);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }

    public void openNewActivity() {
        Intent intent = new Intent(this, StampDutyActivity.class);
        //intent.putExtra("selectedPartyBean", selectedPartyBean.toString());
        startActivity(intent);
    }

    public ArrayList<StampDutyPartyBean> loadPartyBeanList() {
        ArrayList<StampDutyPartyBean> partyBeanArrayList = new ArrayList<>();
        StampDutyDatabaseDaoImpl stampDutyDatabaseDaoImpl = new StampDutyDatabaseDaoImpl(this);
        SQLiteDatabase database = stampDutyDatabaseDaoImpl.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT KEY_ID, KEY_NAME, PAN_NO, BLOCK_NO, ROAD, CITY, PIN FROM stampDutyPartyInfo", null);

        if (cursor != null) {
            cursor.moveToFirst();
        } else {
            Log.d("DB", "Null database");
        }
        do {
            partyBeanArrayList.add(new StampDutyPartyBean(
                    cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6)));
        } while (cursor.moveToNext());
        //database.close();
        //cursor.close();
        return partyBeanArrayList;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        if(!item.equals("None"))
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

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
        }
        else{
            exitToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            exitToast.show();
        }
        back_pressed = System.currentTimeMillis();
    }


}
