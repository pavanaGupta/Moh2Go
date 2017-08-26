package com.example.ashwanigupta.moh2go;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FranchiseList extends AppCompatActivity {
    ArrayList<String> resPostList=new ArrayList<>();
ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise_list);
        Intent i = getIntent();
        String un= i.getStringExtra("name");
        lv = (ListView) findViewById(R.id.lvFranchise);
        new LoadUrlDataTask().execute("https://"+Profile.ADDRESS+"ngrok.io/franchise/"+un);

    }

    class LoadUrlDataTask extends AsyncTask<String, Void,ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(String... params) {

            URL url=null;
            HttpURLConnection httpURLConnection=null;
            try {
                url= new URL(params[0]);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }

            try {
                httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);   //for connecting
                httpURLConnection.setReadTimeout(10000); //for getting the response

            } catch (IOException e) {

                e.printStackTrace();
            }

            try {

                InputStream inStr= httpURLConnection.getInputStream();
                InputStreamReader iStrRd= new InputStreamReader(inStr);
                BufferedReader bfRd= new BufferedReader(iStrRd);
                StringBuilder sBuilder= new StringBuilder();
                String str=null;
                while((str= bfRd.readLine())!=null)
                {
                    sBuilder.append(str);
                }
                resPostList= getPostListFromResponse(sBuilder.toString());

                Log.d("nw", "doInBackground: "+ sBuilder);
                return resPostList;

            } catch (IOException |JSONException e ) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> postList) {

            //show post list in list view
//            for(Franchise p:postList){
//                Log.d("nw", "onPostExecute: "+p.name+" " + p.password);
//            }

            ArrayAdapter<String> myAdapter =new ArrayAdapter<String>(FranchiseList.this,android.R.layout.simple_list_item_1,android.R.id.text1,postList);
            lv.setAdapter(myAdapter);

        }

    }

    ArrayList<String> getPostListFromResponse(String response) throws JSONException
    {
        JSONArray jsonArray=new JSONArray(response);
        ArrayList<String> postArrayList =new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject postObj=jsonArray.getJSONObject(i);
            postArrayList.add(postObj.get("name")+"");
        }

        return  postArrayList;

    }

    boolean isConnected()
    {
        ConnectivityManager connMan= (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo= connMan.getActiveNetworkInfo();
        if(netInfo !=null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }
}
