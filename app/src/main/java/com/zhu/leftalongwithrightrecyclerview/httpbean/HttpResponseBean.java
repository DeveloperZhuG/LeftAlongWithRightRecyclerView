package com.zhu.leftalongwithrightrecyclerview.httpbean;

import java.util.List;

public class HttpResponseBean {

    private String msg;

    private List<HostMenuBean> categoryOneArray;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<HostMenuBean> getCategoryOneArray() {
        return categoryOneArray;
    }

    public void setCategoryOneArray(List<HostMenuBean> categoryOneArray) {
        this.categoryOneArray = categoryOneArray;
    }
}
