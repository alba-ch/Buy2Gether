package com.pis.buy2gether.usecases.home.home;

public class TypeTabModel {

    private int product_image;
    private String product_name;
    private String product_price;

    public TypeTabModel(int image, String text, String product_price) {
        this.product_image = image;
        this.product_name = text;
        this.product_price = product_price;
    }

    public int getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price(){
        return product_price;
    }

}
