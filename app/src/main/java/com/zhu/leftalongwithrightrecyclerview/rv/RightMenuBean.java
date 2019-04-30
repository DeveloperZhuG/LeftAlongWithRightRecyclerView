package com.zhu.leftalongwithrightrecyclerview.rv;

/**
 *  本地右侧每个item对象.
 *  没有直接用服务器返回的是因为， 本地包装了 titleName 字段.
 */
public class RightMenuBean {
    private String name;
    private String titleName;
    private boolean isTitle;
    private String tag;

    public String getTag() {
        return tag;
    }

    /**
     *
     * @param tag 其宿主（左侧的 item） 在宿主集合中的 index;
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

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
