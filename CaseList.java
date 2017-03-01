package com.clinton.yehuda.buyaphonecase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

//should really be called listview
public class CaseList extends AppCompatActivity {

    private static EbayApi ebayApi;
    private static EbayParser ebayParser;
    private Uri uri;
    ListView listItemView;
    private Context context;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();//bypass using async
    private Listing selectedListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_list);
        StrictMode.setThreadPolicy(policy);//bypass using async
        doit();
    }

    public void doit() {
        Log.d("trying to", "doit");
        String searchResponse = "blank";//what the heck is thi
        context = this;//or this
        if (ebayApi == null) {
            ebayApi = new EbayApi(this);//not sure here
        }
        if (ebayParser == null) {
            ebayParser = new EbayParser(this);//this.context  here
        }

        String testTitle=null, testImage=null;
        try {
            searchResponse = ebayApi.search("s3");
            Log.d("search response", searchResponse);
            //parse the json myself
            try {
                JSONObject jsonObject = new JSONObject(searchResponse);
                JSONArray itemsResults = (JSONArray) jsonObject.get("findItemsAdvancedResponse");//
                JSONObject aResult = (JSONObject) itemsResults.get(0);
                JSONArray theSearchResult = (JSONArray) aResult.get("searchResult");
                JSONObject aaResult = (JSONObject) theSearchResult.get(0);
                JSONArray itemm = (JSONArray) aaResult.get("item");
                JSONObject oitem = (JSONObject) itemm.get(0);
                //Log.d("json parse result", String.valueOf(oitem.get("title")));//the long way
                //Log.d("single line jsonParse", String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(0).get("title")));
testTitle = this.stripWrapper(String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(0).get("title")));
testImage = this.stripWrapper(String.valueOf(jsonObject.getJSONArray("findItemsAdvancedResponse").getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).getJSONArray("item").getJSONObject(0).get("galleryURL")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("test string",this.stripWrapper("aahelloaa"));

//were going to get rid of ebayparser and Listing
            //get the image//where im working
//            URL url = new URL(testImage);
//            Bitmap myimage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            Bitmap myimage=null;
            try {

                URL url = new URL(testImage);
                InputStream in = url.openConnection().getInputStream();
                BufferedInputStream bis = new BufferedInputStream(in,1024*8);
                ByteArrayOutputStream out = new ByteArrayOutputStream();

                int len=0;
                byte[] buffer = new byte[1024];
                while((len = bis.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }
                out.close();
                bis.close();

                byte[] data = out.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                myimage = bitmap;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            //end of get the image

            final int[] image = new int[1];
            final String[] title = new String[1];
            final String[] price = new String[1];
            final String[] shipping = new String[1];

//      make dummy data
            Log.d("read image", String.valueOf(Integer.parseInt(String.valueOf(myimage))));
            image[0] = Integer.parseInt(String.valueOf(myimage));//R.drawable.clip
            title[0] = testTitle;//"Blue Case for samsung"
            price[0] = "32.10";
            shipping[0] = "2.65";
// end of example data

            listItemView = (ListView) findViewById(R.id.phoneCaseListView);

            listItemView.setAdapter(new List(this, title, price, shipping, image));


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

    //get rid of the '[' and stuff
    private String stripWrapper(String s){
        try{
            int end=s.length()-2;
            return(s.substring(2,end));
        }catch(Exception x){
Log.d("stripWrapper","errrrror");
            return(s);
        }
    }
    //get rid of the '[' and stuff

    public void goToSite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
