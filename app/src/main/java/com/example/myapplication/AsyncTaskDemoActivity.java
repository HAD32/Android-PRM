package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskDemoActivity extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_demo);
        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public void onClick(View view){
        DownloadImageAsyncTask asyncTask = new DownloadImageAsyncTask();
        asyncTask.execute(editText.getText().toString());

    }

    public class DownloadImageAsyncTask extends AsyncTask<String, String, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream is = connection.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig  = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
                return bitmap;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressBar.setVisibility(View.GONE);
            imageView.setImageBitmap(bitmap);
            if(bitmap == null){
                Toast.makeText(getApplicationContext(), "Cannot download image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
