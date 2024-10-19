package com.shadoxx.ResManagement.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Bill {
    private BigInteger id;
    private BigInteger tableId;
    private Date date;
    private BigDecimal amount;
    private String status;

    public Bill() {}

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigInteger getTableId() {
        return tableId;
    }

    public void setTableId(BigInteger tableId) {
        this.tableId = tableId;
    }
}
