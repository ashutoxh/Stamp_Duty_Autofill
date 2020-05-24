package com.ashutoxh.stampduty;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class ViewAllPartyInfoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        Log.d("ViewAllPartyInfoAct", "start");

        ListView listView = findViewById(R.id.listView);
        //listView.setDivider(getDrawable(R.drawable.divider));
        //listView.setDividerHeight(1);

        ViewAllPartyInfoAdapter viewAllPartyInfoAdapter = new ViewAllPartyInfoAdapter(this);
        listView.setAdapter(viewAllPartyInfoAdapter);

    }
}
