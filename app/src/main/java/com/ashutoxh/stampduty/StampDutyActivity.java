package com.ashutoxh.stampduty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

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
                        String injectingScript = "document.getElementById(\"Dept_Code_Tax_id\").value = \"IGR|N|C\";\n" +
                                "       onChangeDepartment();\n" +
                                "       document.getElementById(\"cmbRec_Type_list\").value = \"12|||N\";\n" +
                                "       onChangePaymentType();\n" +
                                "       document.getElementById(\"cmbtrea_code\").value = \"1301\";\n" +
                                "       onDistrictChange();\n" +
                                "       document.getElementById(\"office_code_list\").value = \"IGR110~IGR03\";\n" +
                                "       onChangeOffice();\n" +
                                "       document.getElementById(\"cmbScheme_code\").value = \"0030046401\";\n" +
                                "       onChangeScheme(\"0030046401\");\n" +
                                "       document.getElementById(\"rperiod\").value = \"O\";\n" +
                                "       document.getElementById(\"cmbFormID\").value = \"29\";\n" +
                                "       document.getElementById(\"amount1\").value = \"10\";\n" +
                                "       document.getElementById(\"Gross_Tot\").value = \"10.00\";\n" +
                                "       document.getElementById(\"txtPANNo\").value = \"" + MainActivity.selectedPartyBean.getPAN_NO() +"\";\n" +
                                "       document.getElementById(\"txtpartyname\").value = \"" + MainActivity.selectedPartyBean.getKEY_NAME() +"\";\n" +
                                "       document.getElementById(\"txtprimise\").value = \"" + MainActivity.selectedPartyBean.getBLOCK_NO() +"\";\n" +
                                "       document.getElementById(\"txtroad\").value = \"" + MainActivity.selectedPartyBean.getROAD() +"\";\n" +
                                "       document.getElementById(\"txtdist\").value = \"" + MainActivity.selectedPartyBean.getCITY() +"\";\n" +
                                "       document.getElementById(\"txtPIN\").value = \"" + MainActivity.selectedPartyBean.getPIN() +"\";\n" +
                                "       document.getElementById(\"txtMobileNo\").value = \"9870226388\";\n" +
                                "       document.getElementById(\"remarks\").value = \"imported vide BOE No 7277312 Dtd 17.03.2020  IGM  2249542 ITEM 109\";\n";

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