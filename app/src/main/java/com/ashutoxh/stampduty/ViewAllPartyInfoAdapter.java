package com.ashutoxh.stampduty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ViewAllPartyInfoAdapter extends BaseAdapter {

    private Context context;
    private TextView regNameTxt;
    private TextView regPanTxt;
    private TextView regBlockTxt;
    private TextView regRoadTxt;
    private TextView regCityTxt;
    private TextView regPinTxt;

    public ViewAllPartyInfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return MainActivity.nameList.size() - 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ViewAllPartyInfoAdapter", "start");
        convertView = LayoutInflater.from(context).inflate(R.layout.view_details_adapter, parent, false);
        regNameTxt = convertView.findViewById(R.id.regNameTxt);
        regPanTxt = convertView.findViewById(R.id.regPanTxt);
        regBlockTxt = convertView.findViewById(R.id.regBlockTxt);
        regRoadTxt = convertView.findViewById(R.id.regRoadTxt);
        regCityTxt = convertView.findViewById(R.id.regCityTxt);
        regPinTxt = convertView.findViewById(R.id.regPinTxt);

        ArrayList<String> partyNameList = MainActivity.nameList;
        partyNameList.remove("-None-");

        if (MainActivity.partyBeanMap != null) {
            String key = partyNameList.get(position);
            if (key.equals("-None-"))
                return convertView;
            regNameTxt.setText(MainActivity.partyBeanMap.get(key).getKEY_NAME());
            regPanTxt.setText(MainActivity.partyBeanMap.get(key).getPAN_NO());
            regBlockTxt.setText(MainActivity.partyBeanMap.get(key).getBLOCK_NO());
            regRoadTxt.setText(MainActivity.partyBeanMap.get(key).getROAD());
            regCityTxt.setText(MainActivity.partyBeanMap.get(key).getCITY());
            regPinTxt.setText(MainActivity.partyBeanMap.get(key).getPIN());
        } else {
            Log.d("ViewAllPartyInfoAdapter", "partyBeanMap : null");
        }

        return convertView;
    }
}
