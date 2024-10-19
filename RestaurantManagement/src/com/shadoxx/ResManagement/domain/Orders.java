package com.shadoxx.ResManagement.domain;

import java.math.BigInteger;

public class Orders {
    private BigInteger id;
    private BigInteger billId;
    private BigInteger menuId;
    private int quantity;

    public Orders() {}

    public BigInteger getBillId() {
        return billId;
    }

    public void setBillId(BigInteger billId) {
        this.billId = billId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getMenuId() {
        return menuId;
    }

    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
