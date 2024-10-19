package com.shadoxx.ResManagement.domain;

import java.math.BigInteger;

public class DiningTable {
    private BigInteger id;
    private String state;
    private String orderName;
    private String order_tel;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrder_tel() {
        return order_tel;
    }

    public void setOrder_tel(String order_tel) {
        this.order_tel = order_tel;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public DiningTable() {}

    @Override
    public String toString() {
        return id + "\t\t" + state + "\t\t" + orderName + "\t\t" + order_tel;
    }
}
