package com.pis.buy2gether.model.domain.pojo;

import com.pis.buy2gether.model.domain.pojo.Grup.Grup;

public class Favorite extends Grup {
    private boolean checked = false;


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
