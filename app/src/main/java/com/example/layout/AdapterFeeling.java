package com.example.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class AdapterFeeling extends RecyclerView.Adapter<AdapterFeeling.ViewHolder>{
    private List<MaskaFeeling> fList;
    private Context mcontext;

    public AdapterFeeling(List<MaskaFeeling> fList, Context mcontext) {
        this.fList = fList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public AdapterFeeling.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterFeeling.ViewHolder(LayoutInflater.from(mcontext).inflate(R.layout.maska_mood, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeeling.ViewHolder holder, int position) {
        final MaskaFeeling modal = fList.get(position);
        holder.title.setText(modal.getTitle());

        if(modal.getImage().equals("null"))
        {
            holder.image.setImageResource(R.drawable.zagl);
        }
        else
        {
            new AdapterFeeling.DownloadImageTask((ImageView) holder.image)
                    .execute(modal.getImage());
        }
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
    public int getItemCount() {
        return fList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.feeling);
            image = itemView.findViewById(R.id.picture);
        }
    }
}