package com.example.layout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Glavn extends AppCompatActivity {

    private TextView greeting;
    private ImageView Avatar;

    private AdapterQuote adapterQuote;
    private List<QuoteMask> qList = new ArrayList<>();

    private AdapterFeeling adapterFeeling;
    private List<MaskaFeeling> fList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavn);

        ListView Feel = findViewById(R.id.ListBlocks);
        adapterQuote = new AdapterQuote(Glavn.this, qList);
        Feel.setAdapter(adapterQuote);
        new GetQuote().execute();

        RecyclerView Mood = findViewById(R.id.ListFeeling);
        Mood.setHasFixedSize(true);
        Mood.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        adapterFeeling = new AdapterFeeling(fList,Glavn.this);
        Mood.setAdapter(adapterFeeling);


        greeting = findViewById(R.id.Greeting);
        greeting.setText("С возвращением, "+ LogIn.users.getNickName() + "!");
        Avatar=findViewById(R.id.Avatar);
        new DownloadImageTask((ImageView) Avatar).execute(LogIn.users.getAvatar());


        new GetQuote().execute();
        new GetFeeling().execute();
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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
                Log.e("Ошибка", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }




    private class GetQuote extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();

                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result=new StringBuilder();
                String line= "";
                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object=new JSONObject(s);
                JSONArray tempArray= object.getJSONArray("data") ;
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    QuoteMask tempProduct = new QuoteMask(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getString("description")
                    );

                    qList.add(tempProduct);
                    adapterQuote.notifyDataSetInvalidated();
                }
            }
            catch (Exception exception)
            {
                Toast.makeText(Glavn.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetFeeling extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    result.append(line);
                }
                return result.toString();
            }
            catch (Exception exception)
            {
                return null;
            }
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                fList.clear();
                adapterFeeling.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray  = object.getJSONArray("data");

                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    MaskaFeeling tempProduct = new MaskaFeeling(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getInt("position")
                    );
                    fList.add(tempProduct);
                    adapterFeeling.notifyDataSetChanged();
                }
                fList.sort(Comparator.comparing(MaskaFeeling::getPosition));
                adapterFeeling.notifyDataSetChanged();
            }
            catch (Exception exception)
            {
                Toast.makeText(Glavn.this, "При выводе данных возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void List(View v) {
        Intent intent = new Intent(Glavn.this, Listening.class);
        startActivity(intent);

    }

    public void Menu(View v) {
        Intent intent = new Intent(Glavn.this, Menu.class);
        startActivity(intent);
    }

    public void profile (View v) {
        Intent intent = new Intent( this, Profile.class);
        startActivity(intent);
    }
}