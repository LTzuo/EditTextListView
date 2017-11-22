package com.ltz.edittextlistview.bean;

/**
 * Created by luoxiaoke on 2016/1/16 12:13.
 * 实体类
 */
public class ItemBean {
    private String place;
    private String stime;
    private String etime;

    private boolean focus;

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
