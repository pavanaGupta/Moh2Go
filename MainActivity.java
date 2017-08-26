package com.example.ashwanigupta.moh2go;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class MainActivity extends AppCompatActivity {

    ArrayList<Post> resPostList;
    ListView lvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvResult=(ListView) findViewById(R.id.lvResult);

        Intent i= getIntent();
        final String category=i.getStringExtra("Category");
        Log.d("nw", "onCreate: "+category);
        if(! isConnected()) {
            Toast.makeText(MainActivity.this, "Internet not connected", Toast.LENGTH_SHORT).show();
        }
        String myURL = "https://"+Profile.ADDRESS+".ngrok.io/shops/"+category.toLowerCase();
        new LoadUrlDataTask().execute(myURL);
    }


    class LoadUrlDataTask extends AsyncTask<String, Void,ArrayList<Post>>
    {
        @Override
        protected ArrayList<Post> doInBackground(String... params) {

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
        protected void onPostExecute(ArrayList<Post> postList) {

            //show post list in list view
            for(Post p:postList){
                Log.d("nw", "onPostExecute: "+p.name+" " + p.description + " " +p.getCategory()+" "+p.getPhone()+" "+ p.getEmail());
            }
            ListViewAdapter lva=new ListViewAdapter(postList,MainActivity.this);
            lvResult.setAdapter(lva);

        }
    }

    ArrayList<Post> getPostListFromResponse(String response) throws JSONException
    {
        JSONArray jsonArray=new JSONArray(response);
        ArrayList<Post> postArrayList =new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject postObj=jsonArray.getJSONObject(i);
            postArrayList.add(new Post(
                    postObj.getString("name"),
                    postObj.getString("category"),
                    postObj.getString("description"),
                    postObj.getString("image"),
                    postObj.getString("email"),
                    postObj.getLong("phone"),
                    postObj.getInt("rating"),
                    postObj.getString("password")
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
