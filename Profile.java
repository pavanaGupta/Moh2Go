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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    public static final String ADDRESS="408d8e64";

public static String u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
       final String un = i.getStringExtra("UN");
        ((TextView)findViewById(R.id.tvUN)).setText(un+"");
u=un;
        String[] str= un.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int j=0;j<str.length-1;j++){
            sb.append(str[j] + "%20");
        }
        sb.append(str[str.length-1]);
        final String usern = sb.toString();
        ((Button)findViewById(R.id.btnProfile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+ADDRESS+".ngrok.io/profile/"+usern));
                i.setPackage("com.android.chrome");
                try{
                    Profile.this.startActivity(i);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        ((Button)findViewById(R.id.btnFranchise)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this,FranchiseList.class);
                i.putExtra("name",usern);
                startActivity(i);
            }
        });

        ((Button)findViewById(R.id.btnAccount)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Profile.this, VideoAct.class);
                i.putExtra("name",un);
                startActivity(i);

            }
        });

        ((Button)findViewById(R.id.btnHelpDesk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(Profile.this, Help.class);
                startActivity(i);

            }
        });


    }

}
