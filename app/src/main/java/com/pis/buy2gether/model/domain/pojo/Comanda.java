package com.pis.buy2gether.model.domain.pojo;

public class Comanda {
    private String comandaID;
    private String grupID;
    private String userID;
    private String data;
    private int status;
    private int stars;

    public Comanda() {
        this.comandaID = "";
        this.grupID = "";
        this.userID = "";
        this.data = "";
        this.status = 0;
        this.stars = -1;
    }

    public Comanda(String id, String userID, String grupID, String data, int status, int stars) {
        this.comandaID = id;
        this.grupID = grupID;
        this.userID = userID;
        this.data = data;
        this.status = status;
        this.stars = stars;
    }

    public String getId() {
        return comandaID;
    }

    public void setId(String id) {
        this.comandaID = id;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getGrupID() {
        return grupID;
    }
    public void setGrupID(String grupID) {
        this.grupID = grupID;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
