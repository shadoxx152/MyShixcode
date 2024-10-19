package com.shadoxx.ResManagement.sevice;

import com.shadoxx.ResManagement.dao.EmployeeDAO;
import com.shadoxx.ResManagement.domain.Employee;

public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public Employee getEmployeeByIdAndPassword(String id, String password) {
        String sql = "select name from employee where id = ? and password = md5(?)";
        return employeeDAO.querySingle(sql, Employee.class, id, password);
    }
}
