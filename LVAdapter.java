package com.example.ashwanigupta.moh2go;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.ashwanigupta.moh2go.Profile.ADDRESS;

/**
 * Created by ashwani gupta on 26-08-2017.
 */

public class LVAdapter extends BaseAdapter {

    ArrayList<TutorialItem> posts= new ArrayList<>();
    Context c;

    public LVAdapter(){

    }

    public LVAdapter(ArrayList<TutorialItem> posts, Context c) {
        this.posts = posts;
        this.c = c;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public TutorialItem getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("nw", "onExecute: ");
        LayoutInflater li=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LVAdapter.ListHolder lholder;

        if(convertView==null)
        {
            convertView=li.inflate(R.layout.lvitem,null);
            lholder=new LVAdapter.ListHolder();

            convertView.setTag(lholder);
        }else{
            lholder = (LVAdapter.ListHolder) convertView.getTag();
        }

        lholder.tvName=(TextView)convertView.findViewById(R.id.tvName);
        lholder.tvName.setText(""+posts.get(position).getName());
        TutorialItem p =posts.get(position);


        lholder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse(posts.get(position).getLink()));
                i.setPackage("com.android.chrome");
                try{
                    c.startActivity(i);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        return convertView;
    }

    static class ListHolder
    {
        TextView tvName;
    }

}
