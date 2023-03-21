package com.example.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class AdapterQuote extends BaseAdapter {

    private Context nContext;
    List<QuoteMask> maskaList;

    public AdapterQuote(Context nContext, List<QuoteMask> maskList) {
        this.nContext = nContext;
        this.maskaList = maskList;
    }
    private class ImageLoad extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageLoad(ImageView bmImage) {
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

    @Override
    public int getCount() {
        return maskaList.size();
    }

    @Override
    public Object getItem(int position) {
        return maskaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return maskaList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(nContext,R.layout.heading,null);
        TextView title = v.findViewById(R.id.name);
        ImageView picture = v.findViewById(R.id.title_splash_screen);
        TextView descript = v.findViewById(R.id.description);

        QuoteMask quoteMask = maskaList.get(position);
        title.setText(quoteMask.getTitle());

        picture.setImageURI(Uri.parse(quoteMask.getImage()));
        if (quoteMask.getImage().toString().equals("null")){
            picture.setImageResource(R.drawable.zagl);
        }
        else
        {
            new ImageLoad((ImageView) picture).execute(quoteMask.getImage());
        }
        descript.setText(quoteMask.getDescription());
        return v;
    }
}
