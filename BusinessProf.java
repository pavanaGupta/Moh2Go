package com.example.ashwanigupta.moh2go;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class BusinessProf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_prof);

        final Intent i= getIntent();

        ((TextView)findViewById(R.id.tvName)).setText(i.getStringExtra("name"));
        ((TextView)findViewById(R.id.tvDesc)).setText(i.getStringExtra("desp"));
        ((TextView)findViewById(R.id.tvNum)).setText(i.getStringExtra("phone"));
        ((TextView)findViewById(R.id.tvEmail)).setText(i.getStringExtra("email"));
        ((Button)findViewById(R.id.btnRate)).setText(i.getStringExtra("rating"));
        new ImageLoadTask(i.getStringExtra("image"), ((ImageView)findViewById(R.id.ivImage))).execute();

        final int r = Integer.valueOf(i.getStringExtra("rating"));
        ((Button)findViewById(R.id.btnRate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Button)v.findViewById(R.id.btnRate)).setText(r+1+"");

            }
        });

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        Log.d("nw", "getResizedBitmap: "+resizedBitmap);
        return resizedBitmap;
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Log.d("nw", "doInBackground: "+url);
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            result = getResizedBitmap(result,200,200);
            imageView.setImageBitmap(result);
        }

    }


}
