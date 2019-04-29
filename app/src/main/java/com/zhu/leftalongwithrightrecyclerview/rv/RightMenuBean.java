package com.zhu.leftalongwithrightrecyclerview.rv;

/**
 *  本地右侧每个item对象.
 *  没有直接用服务器返回的是因为， 本地包装了 titleName 字段.
 */
public class RightMenuBean {
    private String name;
    private String titleName;
    private boolean isTitle;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

}
