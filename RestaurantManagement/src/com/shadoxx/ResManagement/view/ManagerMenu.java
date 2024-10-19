package com.shadoxx.ResManagement.view;

import com.shadoxx.ResManagement.domain.DiningTable;
import com.shadoxx.ResManagement.domain.Employee;
import com.shadoxx.ResManagement.domain.Menu;
import com.shadoxx.ResManagement.domain.Orders;
import com.shadoxx.ResManagement.sevice.*;
import com.shadoxx.ResManagement.utils.Utility;

import java.math.BigInteger;
import java.util.List;

public class ManagerMenu {
    private boolean mainMenuIsDisplayed = true;
    private boolean initialMenuIsDisplayed = true;
    private EmployeeService employeeService = new EmployeeService();
    private DiningTableService diningTableService = new DiningTableService();
    private MenuService menuService = new MenuService();
    private OrdersServer ordersServer = new OrdersServer();
    private BillServer billServer = new BillServer();

    public void initialMenu() {
        initialMenuIsDisplayed = true;
        String key;
        while (initialMenuIsDisplayed) {
            System.out.println("--------Welcome to the restaurant management---------");
            System.out.println("\t\t\t\t\t 1.Login");
            System.out.println("\t\t\t\t\t 2.Exit");
            System.out.println("Please Input your choice:");

            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.println("Please Input your Employee ID:");
                    String id = Utility.readString(50);
                    System.out.println("Please Input your password:");
                    String pwd = Utility.readString(50);

                    Employee employee = employeeService.getEmployeeByIdAndPassword(id, pwd);
                    if (employee != null) {
                        System.out.println("--------Login Successful--------\n");
                        initialMenuIsDisplayed = false;
                        mainMenu();
                    } else {
                        System.out.println("\n--------Login fail, please re-login--------\n");
                    }
                    break;
                case "2":
                    initialMenuIsDisplayed = false;
                    System.out.println("Looking forward the next visit");
                    break;
                default:
                    System.out.println("Input error, please re-enter:\n");
            }
        }
    }

    private void mainMenu() {
        mainMenuIsDisplayed = true;
        String key;
        while (mainMenuIsDisplayed) {
            System.out.println("-------- Restaurant Management System --------");
            System.out.println("\t\t\t1. Table Status");
            System.out.println("\t\t\t2. Reserve a table");
            System.out.println("\t\t\t3. Menu");
            System.out.println("\t\t\t4. Ordering");
            System.out.println("\t\t\t5. View purchased dishes");
            System.out.println("\t\t\t6. Checkout");
            System.out.println("\t\t\t9. Exit");
            System.out.println("\nPlease input your choice");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    getWholeTableInfoOnView();
                    break;
                case "2":
                    reserveDiningTable();
                    break;
                case "3":
                    showMenu();
                    break;
                case "4":
                    ordering();
                    break;
                case "5":
                    printOrder();
                    break;
                case "6":
                    checkOut();
                    break;
                case "9":
                    mainMenuIsDisplayed = false;
                    initialMenu();
                    break;
                default:
                    System.out.println("Input error, please re-enter:\n");
            }
        }
    }

    private void getWholeTableInfoOnView() {
        List<DiningTable> diningTables = diningTableService.getTableInfo();
        System.out.println("id\t\tstate\t\torder_name\torder_tel");
        for (DiningTable table : diningTables) {
            System.out.println(table);
        }
        Utility.waitToInputEnter();
    }

    private void reserveDiningTable() {
        System.out.println("Input the table number that you want to reserve:");
        String id = Utility.readString(3);
        DiningTable diningTable =diningTableService.getTableById(id);
        if (diningTable == null) {
            System.out.println("There is no table with that number");
        } else if("Full".equals(diningTable.getState())) {
            System.out.println("This table is full");
        } else {
            System.out.println("Please confirm your reservation again(Y/N)");
            char c = Utility.readConfirmSelection();
            if (c == 'Y') {
                System.out.println("Please input your name:");
                String name = Utility.readString(50);
                System.out.println("Please input your phone number");
                String tel = Utility.readString(15);
                int affectRow = diningTableService.ReserveTable(id, name, tel);
                if (affectRow == 1) {
                    System.out.println("Successful");
                }
            }
        }
    }

    private void showMenu() {
        System.out.println("-----------------MENU------------------");
        List<Menu> dishesInMenu = menuService.getMenu();
        for (Menu dishes : dishesInMenu) {
            dishes.printMenu();
        }
        System.out.println("\n");
    }

    private void ordering() {
        System.out.println("Please input table number:");
        String tableNum = Utility.readString(3);
        DiningTable diningTable = diningTableService.getTableById(tableNum);
        if (diningTable == null) {
            System.out.println("There is no table with that number");
            return;
        }
        if ("Empty".equals(diningTable.getState())) {
            System.out.println("This table isn't reserved");
            return;
        }
        Object o = billServer.getNotPaidBillByTableId(new BigInteger(tableNum));
        if (o == null) {
            billServer.createNewBill(new BigInteger(tableNum));
        }
        System.out.println("Please input the number of dish id you want to order:");
        int idNum = Integer.parseInt(Utility.readString(1));
        for (int i = 0;i < idNum;i++) {
            System.out.println("id:");
            String id = Utility.readString(1);
            System.out.println("quantity:");
            String quantity = Utility.readString(1);
            ordersServer.addOrder(tableNum, id, quantity);
        }
        billServer.updateAmount(new BigInteger(tableNum));
    }

    private void printOrder() {
        System.out.println("Please input the number of table that you want to query:");
        String tableNum = Utility.readString(1);
        Object ob = billServer.getNotPaidBillByTableId(new BigInteger(tableNum));
        if (ob == null) {
            System.out.println("There is no bill with this table");
            return;
        }
        List<Orders> orders = ordersServer.printOrdersOfBillByTableNum(new BigInteger(tableNum));
        System.out.printf("%-25s    %6s%n", "Dishes", "quantity");
        for (Orders o : orders) {
            String dishesName = menuService.getDishesById(o.getMenuId().toString());
            System.out.printf("%-25s    %6d%n", dishesName, o.getQuantity());
        }
        System.out.println(billServer.getAmount(new BigInteger(tableNum)));
    }

    private void checkOut() {
        System.out.println("Table number for checkout:");
        String tableNum = Utility.readString(1);
        Object o = billServer.getNotPaidBillByTableId(new BigInteger(tableNum));
        if (o == null) {
            System.out.println("There is no bill with this table");
            return;
        }
        diningTableService.checkOut(tableNum);
        ordersServer.checkout(tableNum);
        billServer.checkout(tableNum);
        System.out.println("-------" + tableNum + "checkout successfully---------");
    }
}
