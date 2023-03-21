package com.example.layout;

import android.os.Parcel;
import android.os.Parcelable;

public class QuoteMask implements Parcelable {

    private int id;
    private String title;
    private String image;
    private String description;


    public QuoteMask(int id, String title, String image, String description){
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
    }

    protected QuoteMask(Parcel in) {
        id = in.readInt();
        title = in.readString();
        image = in.readString();
        description = in.readString();
    }

    public static final Creator<QuoteMask> CREATOR = new Creator<QuoteMask>() {
        @Override
        public QuoteMask createFromParcel(Parcel in) {
            return new QuoteMask(in);
        }

        @Override
        public QuoteMask[] newArray(int size) {
            return new QuoteMask[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
