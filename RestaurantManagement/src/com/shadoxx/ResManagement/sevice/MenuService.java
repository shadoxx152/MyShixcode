package com.shadoxx.ResManagement.sevice;

import com.shadoxx.ResManagement.dao.MenuDAO;
import com.shadoxx.ResManagement.domain.Menu;

import java.math.BigInteger;
import java.util.List;

public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();

    public List<Menu> getMenu() {
        String sql = "select id ,name as dishesName, price as dishesPrice from menu";
        return menuDAO.query(sql, Menu.class);
    }

    public String getDishesById(String menuId) {
        String sql = "select name from menu where id = ?";
        return menuDAO.queryScalar(sql, menuId).toString();
    }
}
