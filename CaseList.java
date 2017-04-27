package com.clinton.yehuda.buyaphonecase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//should really be called listview
public class CaseList extends Activity {

    private static EbayApi ebayApi;

    private Uri uri;
    private String urlString;
    ListView listItemView;
    StrictMode.ThreadPolicy async = new StrictMode.ThreadPolicy.Builder().permitAll().build();//bypass using async

    final String[] image = new String[10];
    final String[] title = new String[10];
    final String[] price = new String[10];
    final String[] shipping = new String[10];
    final String[] itemUrl = new String[10];
    private int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);
        StrictMode.setThreadPolicy(async);//bypass using async
        populateList();
    }

    public void populateList() {

        if (ebayApi == null) {
            ebayApi = new EbayApi(this);//figured it out
        }
        String searchResponse;

        try {
            searchResponse = ebayApi.search(MainActivity.keyword);//
            Log.d("searchResponse", searchResponse);
            //parse the json all by myself
            JSONObject jsonObject = new JSONObject(searchResponse);
            JSONArray itemsResults = (JSONArray) jsonObject.get("findItemsAdvancedResponse");//but what if i not 'advance'
            JSONObject aResult = (JSONObject) itemsResults.get(0);
            JSONArray theSearchResult = (JSONArray) aResult.get("searchResult");
            JSONObject aaResult = (JSONObject) theSearchResult.get(0);
            length = Integer.parseInt((String) aaResult.get("@count"));//the long way
            //loop through all the itemshttps://
            for (int i = 0; i < length; i++) {
                try {
                    title[i] = this.jsonFixer(String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(i).get("title")));
                    image[i] = this.jsonFixer(String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(i).get("galleryURL")));
                    price[i] = "$"+String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(i).getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).get("__value__"));
                    shipping[i] = "$"+String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(i).getJSONArray("shippingInfo").getJSONObject(0).getJSONArray("shippingServiceCost").getJSONObject(0).get("__value__"));
                    itemUrl[i] = this.stripWrapper(String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(i).get("viewItemURL")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }//end of loop

//            Log.d("title", title[0]);
//            Log.d("viewItemURL", itemUrl[0]);

            listItemView = (ListView) findViewById(R.id.phoneCaseListView);

            listItemView.setAdapter(new List(this, title, price, shipping, image, length));//

            listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                //clicking on item to go to browser
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    uri = Uri.parse(itemUrl[position]);
                    urlString = itemUrl[position];
                    goToSite();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//end of populate list

    //get rid of the '[' and other stuff in json
    private String jsonFixer(String jf) {
        Log.d("jasonFixerBefore", jf);
        try {
//            jf = jf.replaceAll("[\\\\]", "/");//doesnt appear to help
            jf = jf.replaceAll("[^a-zA-Z1234567890 _.:/-]", "");
            Log.d("jasonFixerAfter", jf);
        } catch (Exception x) {
            Log.d("json fixer", "errrrror");
        }
        return (jf);
    }

    private String stripWrapper(String s) {
        try {
//            Log.d("stripWrapperbefore", s);
            int end = s.length() - 2;
            return (s.substring(2, end));
        } catch (Exception x) {
            Log.d("stripWrapper", "errrrror");
            return (s);
        }
    }

    public void goToSite() {//to open item in browser
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);

//        Log.d("url attempot", urlString);
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
//        startActivity(browserIntent);

//        try {
//            urlString = URLEncoder.encode(urlString, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        urlString = this.jsonFixer(urlString);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlString)));//"http://svcs.ebay.com/services/search/FindingService/v1?"+

    }

}
