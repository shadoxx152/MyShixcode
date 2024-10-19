package com.shadoxx.ResManagement.sevice;

import com.shadoxx.ResManagement.dao.DiningTableDAO;
import com.shadoxx.ResManagement.domain.DiningTable;

import java.util.List;

public class DiningTableService {
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    public List<DiningTable> getTableInfo() {
        String sql = "select id, state, order_name AS orderName, order_tel from diningTable";
        return diningTableDAO.query(sql, DiningTable.class);
    }

    public DiningTable getTableById(String id) {
        String sql = "select id, state, order_name AS orderName, order_tel from diningTable where id = ?";
        return diningTableDAO.querySingle(sql, DiningTable.class, id);
    }

    public int ReserveTable(String id, String name, String tel) {
        String sql = "update diningTable set order_name = ?, order_tel = ?, state = 'Full' where id = ?";
        return diningTableDAO.update(sql, name, tel, id);
    }

    public void checkOut(String id) {
        String sql = "update diningTable set order_name = 'Empty', order_tel = 'Empty', state = 'Empty' where id = ?";
        diningTableDAO.update(sql, id);
    }
}
