package com.junjianliu.library.bean;

/**
 * Created by junjianliu
 * on 16/9/1
 * email:spyhanfeng@qq.com
 */
public class DateModel {
    private int id;
    private String day;
    private String weekday;

    private boolean isClick;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }
}
