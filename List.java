package com.clinton.yehuda.buyaphonecase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class List extends BaseAdapter {

    Context context;
    String[] theImage;
    String[] firsttxt;
    String[] secondtxt;
    String[] thirdtxt;
    int listLength;
    private static LayoutInflater inflater = null;

    public List(CaseList mainActivity, String[] itemName, String[] itemPrice, String[] shippingCost, String[] itemImage, int legnth) {
        context = mainActivity;
        theImage = itemImage;
        firsttxt = itemName;
        secondtxt = itemPrice;
        thirdtxt = shippingCost;
        listLength = legnth;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return listLength;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        ImageView img;
        TextView tv1;
        TextView tv2;
        TextView tv3;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView = inflater.inflate(R.layout.activity_list, null);
        Log.d("theImage", theImage[position]);
        if(theImage[position] != "http://thumbs1.ebaystatic.com/pict/04040_0.jpg") {
            holder.img = (ImageView) rowView.findViewById(R.id.imageView1);
            holder.tv1 = (TextView) rowView.findViewById(R.id.textView1);
            holder.tv2 = (TextView) rowView.findViewById(R.id.textView2);
            holder.tv3 = (TextView) rowView.findViewById(R.id.textView3);

            //holder.img.setImageResource(theImage[0]);// but i dont know how to int image resource so i just picasso
            Picasso.with(context.getApplicationContext()).load(theImage[position]).fit().centerInside().into(holder.img);//

            holder.tv1.setText(firsttxt[position]);
            holder.tv2.setText(secondtxt[position]);
            holder.tv3.setText(thirdtxt[position]);

        }
        return rowView;
    }
}
