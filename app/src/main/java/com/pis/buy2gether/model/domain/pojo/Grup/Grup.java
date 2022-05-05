package com.pis.buy2gether.model.domain.pojo.Grup;

import android.graphics.Picture;

import java.util.List;

public class Grup {

    private Category cat;
    private String id;
    private String name;
    private String description;
    private String image;
    private float price;
    private int visibility;
    private String owner;
    private String date;
    private int proces;
    private List<String> membersIds;

    public Grup() {
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public List<String> getMembersIds() {
        return membersIds;
    }

    public void addMember(String memberId) {
        this.membersIds.add(memberId);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProces() {
        return proces;
    }

    public void setProces(int proces) {
        this.proces = proces;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setMembersIds(List<String> membersIds) {
        this.membersIds = membersIds;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

}
