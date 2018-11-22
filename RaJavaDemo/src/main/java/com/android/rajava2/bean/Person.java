package com.android.rajava2.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Xiangshifu
 * on 2018/10/16 下午3:30
 */
public class Person {
    private String name;
    private List<Plan>planList = new ArrayList<>();

    public Person(String name, List<Plan> planList) {
        this.name = name;
        this.planList = planList;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Plan> getPlanList() {
        if (planList == null) {
            return new ArrayList<>();
        }
        return planList;
    }

    public void setPlanList(List<Plan> planList) {
        this.planList = planList;
    }
}
