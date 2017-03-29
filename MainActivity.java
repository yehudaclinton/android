package com.clinton.yehuda.buyaphonecase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView devicetxt;
    ImageView imgv;
    public static String catChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devicetxt = (TextView) findViewById(R.id.textView);
        devicetxt.setText(android.os.Build.MODEL);
        imgv = (ImageView) findViewById(R.id.testview);
        //DeviceName.getDeviceName(); //i cant test this on emulator
        //I should also implement only ones that ship to your location location

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
                catChoice = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=^2&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD=true&categoryId=20349&aspectFilter.aspectName=Compatible+Model&aspectFilter.aspectValueName=For+OnePlus+2&paginationInput.entriesPerPage=3";
                clicked();//trying experimenting with the for the specific phone model////amp; not required
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
                        "aspectFilter(0).aspectValueName=For+OnePlus+3&" +//variable here also there are no wallet cases for 1+1
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
                        "aspectFilter(0).aspectValueName=For+OnePlus+3&" +//variable here
                        "aspectFilter(1).aspectName=Type&" +
                        "aspectFilter(1).aspectValueName=Clip&";
                clicked();//http://svcs.ebay.com/services/search/FindingService/v1?
                break;

            case R.id.water:
                catChoice = "http://webservices.amazon.com/onca/xml?" +
                        "  Service=AWSECommerceService" +//the answer to my problem is maxoutput
                        "  &Operation=ItemSearch" +
                        "  &ResponseGroup=Small" +
                        "  &SearchIndex=All" +
                        "  &Keywords=harry_potter" +
                        "  &AWSAccessKeyId=AKIAIKRF6RMVHONGZWZA";
                clicked();
                break;

            default:
                break;
        }
    }
}
