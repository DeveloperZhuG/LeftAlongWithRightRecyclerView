package com.zhu.leftalongwithrightrecyclerview.httpbean;

import java.util.List;

public class HostMenuBean {
    private String name;
    private String cacode;
    private List<SubMenuBean> categoryTwoArray;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCacode() {
        return cacode;
    }

    public void setCacode(String cacode) {
        this.cacode = cacode;
    }

    public List<SubMenuBean> getCategoryTwoArray() {
        return categoryTwoArray;
    }

    public void setCategoryTwoArray(List<SubMenuBean> categoryTwoArray) {
        this.categoryTwoArray = categoryTwoArray;
    }
}
