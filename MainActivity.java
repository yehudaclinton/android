package com.clinton.yehuda.buyaphonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    //when i go back to the mainactivity from the list view it doesnt change the search. now it does

    TextView devicetxt;
    String deviceName;
    public static String catChoice;
    public static String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devicetxt = (TextView) findViewById(R.id.textView);
        deviceName = android.os.Build.MANUFACTURER+" "+android.os.Build.MODEL;
        devicetxt.setText("your device name is "+deviceName);//
        //I should also implement only ones that ship to your location location
        //android.os.Build.MANUFACTURER+"+"+android.os.Build.MODEL

        ImageButton one = (ImageButton) findViewById(R.id.regular);
        one.setOnClickListener(this); // calling onClick() method
        ImageButton two = (ImageButton) findViewById(R.id.wallet);
        two.setOnClickListener(this);
        ImageButton three = (ImageButton) findViewById(R.id.clip);
        three.setOnClickListener(this);
        ImageButton four = (ImageButton) findViewById(R.id.water);
        four.setOnClickListener(this);
    }

    //wallet-case also known as flip-case (ebay)
    public void clicked() {
        startActivity(new Intent(MainActivity.this, CaseList.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.regular:
                keyword = deviceName;//i probably need to replace the space with a plus
                catChoice = "http://svcs.ebay.com/services/search/FindingService/v1?" +
                        "OPERATION-NAME=findItemsAdvanced&" +
                        "SERVICE-VERSION=1.0.0&" +
                        "SECURITY-APPNAME=^2&" +
                        "RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=true&" +
                        "categoryId=20349&" +
                        "paginationInput.entriesPerPage=6&" +
                        "aspectFilter.aspectName=Compatible+Model&" +
                        "aspectFilter.aspectValueName=For+"+android.os.Build.MANUFACTURER+"+"+android.os.Build.MODEL;
                clicked();///amp; not required// there are no A5 only A8//insted it keywords it
                break;

            case R.id.wallet:// the search strings shouldnt be in mainactivity
                catChoice = "http://svcs.ebay.com/services/search/FindingService/v1?" +
                        "OPERATION-NAME=findItemsAdvanced&" +
                        "SERVICE-VERSION=1.0.0&" +
                        "SECURITY-APPNAME=^2&" +
                        "RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=true&" +
                        "categoryId=20349&" +
                        "paginationInput.entriesPerPage=6&" +
                        "aspectFilter(0).aspectName=Compatible+Model&" +
                        "aspectFilter(0).aspectValueName=For+"+android.os.Build.MANUFACTURER+"+"+android.os.Build.MODEL+"&" +//variable here also there are no wallet cases for 1+1
                        "aspectFilter(1).aspectName=Type&" +
                        "aspectFilter(1).aspectValueName(0)=Wallet&" +
                        "aspectFilter(1).aspectValueName(1)=Pouch/Sleeve&";
                clicked();
                break;

            case R.id.clip:
                catChoice = "http://svcs.ebay.com/services/search/FindingService/v1?" +
                        "OPERATION-NAME=findItemsAdvanced&" +
                        "SERVICE-VERSION=1.0.0&" +
                        "SECURITY-APPNAME=^2&" +
                        "RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=true&" +
                        "categoryId=20349&" +
                        "paginationInput.entriesPerPage=6&" +
                        "aspectFilter(0).aspectName=Compatible+Model&" +
                        "aspectFilter(0).aspectValueName=For+"+android.os.Build.MANUFACTURER+"+"+android.os.Build.MODEL+"&" +//OnePlus+3
                        "aspectFilter(1).aspectName=Type&" +
                        "aspectFilter(1).aspectValueName=Clip&";
                clicked();//http://svcs.ebay.com/services/search/FindingService/v1?
                break;

            case R.id.water:
                catChoice = "http://svcs.ebay.com/services/search/FindingService/v1?" +
                        "OPERATION-NAME=findItemsAdvanced&" +
                        "SERVICE-VERSION=1.0.0&" +
                        "SECURITY-APPNAME=^2&" +
                        "RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=true&" +
                        "categoryId=20349&" +
                        "paginationInput.entriesPerPage=6&" +
                        "aspectFilter(0).aspectName=Compatible+Model&" +
                        "aspectFilter(0).aspectValueName=For+"+android.os.Build.MANUFACTURER+"+"+android.os.Build.MODEL+"&" +//variable here
                        "aspectFilter(1).aspectName=Features&" +
                        "aspectFilter(1).aspectValueName(0)=Waterproof&" +
                        "aspectFilter(1).aspectValueName(1)=Water+Resistant&";
                clicked();
                break;

            default:
                break;
        }
    }
}
