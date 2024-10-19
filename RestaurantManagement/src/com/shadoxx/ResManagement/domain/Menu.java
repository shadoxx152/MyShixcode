package com.shadoxx.ResManagement.domain;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Menu {
    private BigInteger id;
    private String dishesName;
    private BigDecimal dishesPrice;

    public Menu() {}

    @Override
    public String toString() {
        return  dishesName + "\t\t" + dishesPrice;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public BigDecimal getDishesPrice() {
        return dishesPrice;
    }

    public void setDishesPrice(BigDecimal dishesPrice) {
        this.dishesPrice = dishesPrice;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void printMenu() {
        System.out.printf("%-25s    %6.2f%n", dishesName, dishesPrice);
    }
}
