package com.atguigu.crowd.mvc.handler;

import java.util.List;

class ParamData{

    private List<Integer> array;

    @Override
    public String toString() {
        return "ParamData{" +
                "array=" + array +
                '}';
    }

    public List<Integer> getArray() {
        return array;
    }

    public void setArray(List<Integer> array) {
        this.array = array;
    }

    public ParamData(List<Integer> array) {
        this.array = array;
    }

    public ParamData() {
    }
}