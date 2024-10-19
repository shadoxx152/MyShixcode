package com.shadoxx.ResManagement.sevice;

import com.shadoxx.ResManagement.dao.BillDAO;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BillServer {
    private BillDAO billDAO = new BillDAO();

    public int updateAmount(BigInteger tableId) {
        String sql = "update bill set amount = (select SUM(menu.price * orders.quantity) from orders JOIN menu ON orders.menu_id = menu.id where orders.bill_id = bill.table_id) where table_id = ?";
        return billDAO.update(sql, tableId);
    }

    public void createNewBill(BigInteger tableId) {
        String sql = "insert into bill (table_id, amount, status) values (?, 0.00, 'not paid')";
        billDAO.update(sql, tableId);
    }

    public Object getNotPaidBillByTableId(BigInteger tableId) {
        String sql = "select table_id from bill where table_id = ? and status = 'not paid'";
        return billDAO.queryScalar(sql, tableId);
    }

    public BigDecimal getAmount(BigInteger tableId) {
        String sql = "select amount from bill where table_id = ?";
        return (BigDecimal)billDAO.queryScalar(sql, tableId);
    }

    public void checkout(String tableId) {
        String sql = "update bill set status = 'paid' where table_id = ? and status = 'not paid'";
        billDAO.update(sql, tableId);
    }
}
