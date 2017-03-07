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
                catChoice = "regular";
                clicked();
                break;

            case R.id.wallet:
                catChoice = "wallet";
                clicked();
                break;

            case R.id.clip:
                catChoice = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&amp;SERVICE-VERSION=1.0.0&amp;SECURITY-APPNAME=^2&amp;RESPONSE-DATA-FORMAT=JSON&amp;REST-PAYLOAD=true&amp;categoryId=20349&amp;aspectFilter.aspectName=Type&amp;aspectFilter.aspectValueName=Clip&amp;paginationInput.entriesPerPage=3";
                clicked();
                break;

            case R.id.water:
                catChoice = "water";
                clicked();
                break;

            default:
                break;
        }
    }
}
