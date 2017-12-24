package com.example.jek.omertextest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class ResultView extends AppCompatActivity {
    TextView tvTitle;
    TextView tvBody;
    ImageView ivFlickrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_view);
        tvTitle = findViewById(R.id.tvTitleView);
        tvBody = findViewById(R.id.tvBodyView);
        ivFlickrImage = findViewById(R.id.ivFlickrImage);
        tvBody.setText(getIntent().getStringExtra("BODY"));
        tvTitle.setText(getIntent().getStringExtra("TITLE"));
        new DownloadImageTask(ivFlickrImage)
                .execute(getIntent().getStringExtra("URL"));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
