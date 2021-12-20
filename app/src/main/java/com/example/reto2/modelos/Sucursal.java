package com.example.reto2.modelos;

public class Sucursal {
    private int id;
    private String name;
    private String description;
    private String location;
    private byte[] image;
    private String btnFav;

    public Sucursal(int id, String name, String description, String location, byte[] image, String btnFav) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.image = image;
        this.btnFav = btnFav;
    }

    public Sucursal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBtnFav() {
        return btnFav;
    }

    public void setBtnFav(String btnFav) {
        this.btnFav = btnFav;
    }
}
