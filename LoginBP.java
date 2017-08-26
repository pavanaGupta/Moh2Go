package com.example.ashwanigupta.moh2go;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginBP extends AppCompatActivity {
    ArrayList<Post> resPostList;
    EditText username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bp);

        username = (EditText) findViewById(R.id.etUN);
        password = (EditText) findViewById(R.id.etPass);

        if(! isConnected()) {
            Toast.makeText(LoginBP.this, "Internet not connected", Toast.LENGTH_SHORT).show();
        }
        final String myURL = "https://"+Profile.ADDRESS+".ngrok.io/secret-link";

        ((Button)findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadUrlDataTask().execute(myURL);
            }
        });

        ((Button) findViewById(R.id.btnReg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginBP.LoadUrlDataTask().execute(myURL);
                Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+Profile.ADDRESS+".ngrok.io/register"));
                i.setPackage("com.android.chrome");
                try{
                    LoginBP.this.startActivity(i);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
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
        protected void onPostExecute(ArrayList<Post> postList) {

            //show post list in list view
//            for(Post p:postList){
//                Log.d("nw", "onPostExecute: "+p.name+" " + p.password);
//            }

            String un= username.getText().toString();
            String pw= password.getText().toString();
            int flag=0;
            for(Post p:postList){
                if(p.getName().toLowerCase().equals(un.toLowerCase())){
                    if(p.getPassword().equals(pw)){
                        Intent i= new Intent(LoginBP.this,Profile.class);
                        i.putExtra("UN",un);
                        startActivity(i);
                    }else{
                        Toast.makeText(LoginBP.this,"Wrong Username or Password",Toast.LENGTH_SHORT).show();
                    }
                    flag=1;
                }
            }

            if(flag==0){
                Toast.makeText(LoginBP.this,"Wrong Username or Password",Toast.LENGTH_SHORT).show();
            }

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
