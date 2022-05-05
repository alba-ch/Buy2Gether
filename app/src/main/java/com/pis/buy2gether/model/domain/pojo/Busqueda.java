package com.pis.buy2gether.model.domain.pojo;

import java.util.Date;

public class Busqueda {
    private String searchContent;
    private Date data;

    public Busqueda(String searchContent, Date data) {
        this.searchContent = searchContent;
        this.data = data;
    }

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
