package com.example.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterImage extends BaseAdapter {

    private Context mContext;
    List<MaskaImage> maskList;

    public AdapterImage(Context mContext, List<MaskaImage> maskList) {
        this.mContext = mContext;
        this.maskList = maskList;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext,R.layout.maska_image,null);

        ImageView Image = v.findViewById(R.id.ppz);
        TextView dateCreat = v.findViewById(R.id.time);



        MaskaImage maskProfileImage  = maskList.get(position);

        if(maskProfileImage.getImageProfile().exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(maskProfileImage.getImageProfile().getAbsolutePath());
            Image.setImageBitmap(myBitmap);
        }
        dateCreat.setText(maskProfileImage.getData());

        return v;
    }

}
