package com.clinton.yehuda.buyaphonecase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView devicetxt;
    ImageView imgv;


    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        devicetxt = (TextView) findViewById(R.id.textView);
        devicetxt.setText(android.os.Build.MODEL);
        imgv = (ImageView) findViewById(R.id.testview);
        //DeviceName.getDeviceName(); //i cant test this on emulator
        //I should also implement only ones that ship to your location location
    }

    //wallet-case also known as flip-case (ebay)
    public void clicked(View view){
        startActivity(new Intent(MainActivity.this, CaseList.class));
    }


        public void ebay(View view) {

    }
}
