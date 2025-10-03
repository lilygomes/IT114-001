package com.example.lab2;

public class Employee {
    private String name, id, office, extension;
    private double salary;
    private int yearsOfService;

    public Employee(String name, String id, String office, String extension, double salary, int yearsOfService) {
        this.name = name;
        this.id = id;
        this.office = office;
        this.extension = extension;
        this.salary = salary;
        this.yearsOfService = yearsOfService;
    }

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(int yearsOfService) {
        this.yearsOfService = yearsOfService;
    }
}
