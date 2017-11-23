package com.ltz.edittextlistview.bean;

/**
 * Created by 1 on 2017/11/22.
 */
public class Bean {
    private String title;
    private String edit_string;
    private String unitprice;//单价
    private String company;//单位
    private boolean focus;

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public void setEdit_string(String edit_string) {
        this.edit_string = edit_string;
    }

    public String getCompany() {
        return company;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdit_string() {
        return edit_string;
    }

    public String getTitle() {
        return title;
    }
}
