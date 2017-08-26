package com.example.ashwanigupta.moh2go;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

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

public class VideoAct extends AppCompatActivity {

    ListView lvVideo;
    ArrayList<TutorialItem> resPostList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        lvVideo=(ListView) findViewById(R.id.lvVideo);
        Intent i= getIntent();
        //String un= i.getStringExtra("user");

        if(! isConnected()) {
            Toast.makeText(VideoAct.this, "Internet not connected", Toast.LENGTH_SHORT).show();
        }
        String myURL = "https://"+Profile.ADDRESS+".ngrok.io/tutorial";
        new VideoAct.LoadUrlDataTask().execute(myURL);
    }

    class LoadUrlDataTask extends AsyncTask<String, Void,ArrayList<TutorialItem>>
    {
        @Override
        protected ArrayList<TutorialItem> doInBackground(String... params) {

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
                //httpURLConnection.setConnectTimeout(5000);   //for connecting
                // httpURLConnection.setReadTimeout(10000); //for getting the response

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
        protected void onPostExecute(ArrayList<TutorialItem> postList) {

            //show post list in list view

            LVAdapter lva=new LVAdapter(postList,VideoAct.this);
            lvVideo.setAdapter(lva);

        }
    }

    ArrayList<TutorialItem> getPostListFromResponse(String response) throws JSONException
        {
            JSONArray jsonArray=new JSONArray(response);
            ArrayList<TutorialItem> postArrayList =new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject postObj=jsonArray.getJSONObject(i);
            postArrayList.add(new TutorialItem(
                    postObj.getString("category"),
                    postObj.getString("name"),
                    postObj.getString("link")
            ));
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
