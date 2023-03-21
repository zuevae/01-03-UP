package com.example.layout;

public class MaskaFeeling {
    private int id;
    private String title;
    private int position;
    private String image;

   public MaskaFeeling(int id, String title, String image, int position) {
        this.id = id;
        this.title = title;
        this.position=position;
        this.image = image;}

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
