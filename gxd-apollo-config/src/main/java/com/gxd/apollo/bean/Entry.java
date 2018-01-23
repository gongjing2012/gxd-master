package com.gxd.apollo.bean;

/**
 * @Author:gxd
 * @Description:
 * @Date: 17:13 2018/1/18
 * @Modified By:
 */
public class Entry {
    public String title;
    public String text;

    public Entry() {

    }

    public Entry(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
