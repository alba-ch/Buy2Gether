package com.pis.buy2gether.model.domain.pojo;

import com.pis.buy2gether.provider.ProviderType;

import java.util.ArrayList;

public class Profile {

    private String email;
    private String username;
    private ProviderType provider;

    public Profile(){
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ProviderType getProvider() {
        return provider;
    }

    public void setProvider(ProviderType provider) {
        this.provider = provider;
    }
}
