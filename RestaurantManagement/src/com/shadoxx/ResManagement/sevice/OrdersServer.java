package com.shadoxx.ResManagement.sevice;

import com.shadoxx.ResManagement.dao.OrderDAO;
import com.shadoxx.ResManagement.domain.Orders;

import java.math.BigInteger;
import java.util.List;

public class OrdersServer {
    private OrderDAO orderDAO = new OrderDAO();

    public int addOrder(String tableId, String menuId, String quantity) {
        String sql = "insert into orders values (null, ?, ?, ?)";
        return orderDAO.update(sql, tableId, menuId, quantity);
    }

    public List<Orders> printOrdersOfBillByTableNum(BigInteger tableNum) {
        String sql = "select menu_id AS menuId, quantity from orders where bill_id = ?";
        return orderDAO.query(sql, Orders.class, tableNum);
    }

    public void checkout(String tableId) {
        String sql = "delete from orders where bill_id = ?";
        orderDAO.update(sql, tableId);
    }
}
