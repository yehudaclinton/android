package com.clinton.yehuda.buyaphonecase;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class EbayApi {// extends AppCompatActivity
    private static String appID = "yehudacl-phoneCas-PRD-d246ab013-afbfbdc4";
    private static String ebayURL = "http://svcs.ebay.com/";
    private Resources resources;//err
    private HttpURLConnection urlConnection = null;
    private String theChoice;


    public EbayApi(Context context) {
        this.resources = context.getResources();
    }

    public String search(String keyword) throws Exception {//
        String jsonResponse = null;
        jsonResponse = invokeEbayRest(keyword);

        if ((jsonResponse == null) || (jsonResponse.length() < 1)) {
            throw (new Exception("No result received from invokeEbayRest"));
        }
        return (jsonResponse);
    }

    private String getRequestURL(String keyword) {//
        theChoice = MainActivity.catChoice;
        Log.d("get choice", theChoice);
        Log.d("keyword",MainActivity.keyword);
//        Log.d("requestURL template", this.resources.getString(R.string.ebay_request_template));
        CharSequence requestURL = TextUtils.expandTemplate(theChoice, ebayURL, appID, keyword);//
        return (requestURL.toString());
    }

    private String invokeEbayRest(String keyword) throws Exception {//
        String result = null;

        URL url = new URL(this.getRequestURL(URLEncoder.encode(keyword, "UTF-8")));/////

        urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());

        if (in != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer temp = new StringBuffer();
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                temp.append(currentLine);
            }
            result = temp.toString();
            in.close();
        }
        return (result);
    }
}
