package com.pis.buy2gether.model.domain.pojo;

import android.graphics.Bitmap;
import com.pis.buy2gether.model.domain.data.ImageData;

import java.util.List;

public class User {
    private String username;
    private String id;
    private String email;
    private String provider;
    private Bitmap profileImage;
    private List<String> addressIds;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String path) {
        if(ImageData.INSTANCE.getProfilePhoto(path) != null) {
            profileImage = ImageData.INSTANCE.getProfilePhoto(path);
        }else{
            profileImage = ImageData.INSTANCE.getProfilePhoto("unknown.jpg");
        }
    }

    public List<String> getAddressIds() {
        return addressIds;
    }

    public void setAddressIds(List<String> addressIds) {
        this.addressIds = addressIds;
    }
}
