package com.android.rajava2.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2018/10/16 下午3:28
 */
public class Plan {
    private String time;
    private String content;
    private List<String> actionList = new ArrayList<>();

    public Plan(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getActionList() {
        if (actionList == null) {
            return new ArrayList<>();
        }
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }
}
