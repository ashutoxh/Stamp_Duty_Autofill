package com.ashutoxh.stampduty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class StampDutyActivity extends Activity {

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String domainUrl = getResources().getString(R.string.domainURL);
        WebView mWebView;

        setContentView(R.layout.activity_stamp_duty);
        mWebView = findViewById(R.id.activity_stamp_duty_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("WebView", "your current url when webpage loading.. finish" + url);
                super.onPageFinished(view, url);

                if (url.contains(getResources().getString(R.string.indexURI))) {
                    view.loadUrl("javascript:setTimeout(funCall('unreg'), 50)");
                }
                else if (url.contains(getResources().getString(R.string.revenueindexURI))) {
                    view.loadUrl("javascript:setTimeout(jfundepartment('REV'), 50)");
                }
                else if (url.contains(getResources().getString(R.string.formURI))) {
                    injectScriptFile(view); // see below ...

                    // test if the script was loaded
                    //view.loadUrl("javascript:setTimeout(injectScript(), 50)");
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("your current url when you click on any interlink on webview that time you got url :-" + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            private void injectScriptFile(WebView view) {
                InputStream input;
                try {
                    if(MainActivity.selectedPartyBean != null) {
                        String modeOfConsignmetDistrict = (MainActivity.modeOfConsignmet.equals("SEA")) ?
                                "       document.getElementById(\"cmbtrea_code\").value = \"1301\";\n"                           //RAIGAD (SEA)
                                : "       document.getElementById(\"cmbtrea_code\").value = \"7101\";\n";                        //MUMBAI (AIR)
                        String modeOfConsignmetDistrictOffice = (MainActivity.modeOfConsignmet.equals("SEA")) ?
                                "     document.getElementById(\"office_code_list\").value = \"IGR110~IGR03\";\n"                 //ALD_COLL OF STAMPS JDR RAIGAD
                                : "       document.getElementById(\"office_code_list\").value = \"IGR181~IGR09\";\n";           //AOB_SBR AND ADM OFF MUMBAI SUBURBAN

                        String injectingScript = "document.getElementById(\"Dept_Code_Tax_id\").value = \"IGR|N|C\";\n" +       //Inspector General Of Registration
                                "       onChangeDepartment();\n" +
                                "       document.getElementById(\"cmbRec_Type_list\").value = \"12|||N\";\n" +                  //Stamp Duty on Delivery of Goods
                                "       onChangePaymentType();\n" +
                                modeOfConsignmetDistrict +
                                "       onDistrictChange();\n" + modeOfConsignmetDistrictOffice +
                                "       onChangeOffice();\n" +
                                "       document.getElementById(\"cmbScheme_code\").value = \"0030046401\";\n" +                //Inspector General Of Registration
                                "       onChangeScheme(\"0030046401\");\n" +
                                "       document.getElementById(\"rperiod\").value = \"O\";\n" +                                //One Time/Adhoc
                                "       document.getElementById(\"cmbFormID\").value = \"29\";\n" +                             //29-Stamp Duty on Delivery Order in Respect of Goods
                                "       document.getElementById(\"amount1\").value = \"" + MainActivity.amountString.toUpperCase() +"\";\n" +
                                "       document.getElementById(\"Gross_Tot\").value = \"" + MainActivity.amountString.toUpperCase() +".00\";\n" +
                                "       document.getElementById(\"txtPANNo\").value = \"" + MainActivity.selectedPartyBean.getPAN_NO().toUpperCase() +"\";\n" +
                                "       document.getElementById(\"txtpartyname\").value = \"" + MainActivity.selectedPartyBean.getKEY_NAME().toUpperCase() +"\";\n" +
                                "       document.getElementById(\"txtprimise\").value = \"" + MainActivity.selectedPartyBean.getBLOCK_NO().toUpperCase() +"\";\n" +
                                "       document.getElementById(\"txtroad\").value = \"" + MainActivity.selectedPartyBean.getROAD().toUpperCase() +"\";\n" +
                                "       document.getElementById(\"txtdist\").value = \"" + MainActivity.selectedPartyBean.getCITY().toUpperCase() +"\";\n" +
                                "       document.getElementById(\"txtPIN\").value = \"" + MainActivity.selectedPartyBean.getPIN() +"\";\n" +
                                "       document.getElementById(\"txtMobileNo\").value = \"9870226388\";\n" +
                                "       document.getElementById(\"remarks\").value = \"" + MainActivity.remarksString.toUpperCase() +"\";\n";

                        System.out.println("injectingScript : \n " + injectingScript);
                        input = new ByteArrayInputStream(injectingScript.getBytes());

                        byte[] buffer = new byte[input.available()];
                        input.read(buffer);
                        input.close();

                        // String-ify the script byte-array using BASE64 encoding !!!
                        String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
                        view.loadUrl("javascript:(function() {" +
                                "var parent = document.getElementsByTagName('head').item(0);" +
                                "var script = document.createElement('script');" +
                                "script.type = 'text/javascript';" +
                                // Tell the browser to BASE64-decode the string into your script !!!
                                "script.innerHTML = window.atob('" + encoded + "');" +
                                "parent.appendChild(script)" +
                                "})()");
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        mWebView.loadUrl(domainUrl + getResources().getString(R.string.indexURI));
    }
}