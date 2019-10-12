package cn.com.waybill.model.common;

import java.util.List;

public class PermModel {

    private Integer id;

    private String icon;

    private String index;

    private String title;

    private List<PermModel> subs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PermModel> getSubs() {
        return subs;
    }

    public void setSubs(List<PermModel> subs) {
        this.subs = subs;
    }
}
