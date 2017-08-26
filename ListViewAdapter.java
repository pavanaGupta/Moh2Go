package com.example.ashwanigupta.moh2go;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashwani gupta on 25-08-2017.
 */

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Post> posts= new ArrayList<>();
    Context c;

    public ListViewAdapter(){

    }

    public ListViewAdapter(ArrayList<Post> posts, Context c) {
        this.posts = posts;
        this.c = c;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
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
        ListHolder lholder;

        if(convertView==null)
        {
            convertView=li.inflate(R.layout.lvitem,null);
            lholder=new ListHolder();

            convertView.setTag(lholder);
        }else{
            lholder = (ListHolder) convertView.getTag();
        }

        lholder.tvName=(TextView)convertView.findViewById(R.id.tvName);
        lholder.tvName.setText(""+posts.get(position).getName());
Post p =posts.get(position);
        Log.d("nw", "getView: " + p.getName()+" " + p.getPhone() + " " + p.getEmail() + " " + p.getRating());

        lholder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(c, BusinessProf.class);



                i.putExtra("name",posts.get(position).getName());
                i.putExtra("desp",posts.get(position).getDescription());
                i.putExtra("phone",posts.get(position).getPhone()+"");
                i.putExtra("email",posts.get(position).getEmail());
                i.putExtra("rating",posts.get(position).getRating()+"");
                i.putExtra("image",posts.get(position).getImg());

                c.startActivity(i);

            }
        });

        return convertView;
    }

    static class ListHolder
    {
        TextView tvName;
    }

}
