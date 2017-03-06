package com.clinton.yehuda.buyaphonecase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

//should really be called listview
public class CaseList extends AppCompatActivity {

    private static EbayApi ebayApi;
    private static EbayParser ebayParser;
    private Uri uri;
    private HttpURLConnection urlConnection = null;
    ListView listItemView;
    StrictMode.ThreadPolicy async = new StrictMode.ThreadPolicy.Builder().permitAll().build();//bypass using async

    //final int[] image = new int[1];
    //ImageView img;//
    final String[] image = new String[1];
    final String[] title = new String[1];
    final String[] price = new String[1];
    final String[] shipping = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);
        StrictMode.setThreadPolicy(async);//bypass using async
        //img = (ImageView) findViewById(R.id.imageView1);//view is also commented
        doit();
    }

    public void doit() {

        String searchResponse = "blank";//this is unprofessional
        if (ebayApi == null) {
            ebayApi = new EbayApi(this);//figured it out
        }
        if (ebayParser == null) {
            ebayParser = new EbayParser(this);//this.context  here
        }

        int length;
        try {//this needs to be in a loop to get like 10 items
            searchResponse = ebayApi.search("s3");

            //parse the json all by myself
            JSONObject jsonObject = new JSONObject(searchResponse);
            JSONArray itemsResults = (JSONArray) jsonObject.get("findItemsAdvancedResponse");//
            JSONObject aResult = (JSONObject) itemsResults.get(0);
            JSONArray theSearchResult = (JSONArray) aResult.get("searchResult");
            JSONObject aaResult = (JSONObject) theSearchResult.get(0);
            //Log.d("json parse result", String.valueOf(aaResult.get("@count")));//the long way
            length = (int) aaResult.get("@count");
            try {
                title[0] = this.stripWrapper(String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(0).get("title")));
                image[0] = this.stripWrapper(String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(0).get("galleryURL")));
            } catch (JSONException e) {
                e.printStackTrace();
            }


//were going to get rid of ebayparser and Listing


            Log.d("test image", image[0]);
            Log.d("title", title[0]);
            price[0] = "32.10";
            shipping[0] = "2.65";
//should get rid of example data

            listItemView = (ListView) findViewById(R.id.phoneCaseListView);

            listItemView.setAdapter(new List(this, title, price, shipping, image));//


            listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                //clicking on title to go to browser
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //make url intent
                    //uri = Uri.parse(itemLinks[position]);
                    ////goToSite();
                }

            });
            //}//end of populate list

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //get rid of the '[' and stuff in json
    private String stripWrapper(String s) {
        try {
            s = s.replaceAll("[^a-zA-Z1234567890 _.:/]", "");
            //int end = s.length() - 2;
            return (s);//.substring(2, end)
        } catch (Exception x) {
            Log.d("stripWrapper", "errrrror");
            return (s);
        }
    }
    //get rid of the '[' and stuff
    //image getter
//    public Drawable loadImageFromURL(String url, String name) {
////        urlConnection = (HttpURLConnection) url.openConnection();
////        InputStream in = new BufferedInputStream(urlConnection.getInputStream());///////////
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, name);
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }
    //end of image getter

    public void goToSite() {//to open item in browser
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
