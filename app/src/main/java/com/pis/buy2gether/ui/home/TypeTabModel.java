package com.pis.buy2gether.ui.home;

public class TypeTabModel {

    private int product_image;
    private String product_name;

    public TypeTabModel(int image, String text) {
        this.product_image = image;
        this.product_name = text;
    }

    public int getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }


}
