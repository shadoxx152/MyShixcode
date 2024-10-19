package com.shadoxx.ResManagement.domain;

import java.math.BigInteger;

public class Employee {
    private BigInteger id;
    private String name;
    private String job;
    private String password;

    public Employee() {}

    public Employee(BigInteger id, String job, String name, String password) {
        this.id = id;
        this.job = job;
        this.name = name;
        this.password = password;
    }
}
