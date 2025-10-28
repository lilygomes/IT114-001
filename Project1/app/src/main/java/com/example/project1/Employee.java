package com.example.project1;

import java.util.Calendar;

public class Employee {
    private String name, id, office, extension;
    private double salary, performance;
    private int startYear;

    public Employee(String name, String id, double salary, String office, String extension, double performance, int startYear) {
        this.name = name;
        this.id = id;
        this.office = office;
        this.extension = extension;
        this.salary = salary;
        this.performance = performance;
        this.startYear = startYear;
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

    public double getPerformance() {
        return performance;
    }

    public void setPerformance(double performance) {
        this.performance = performance;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public double getBonus() {
        return (performance > 3.5)? (salary * 0.05) : ((performance >= 2.0)? (salary * 0.02) : 0.0);
    }

    public int getYearsService() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR) - startYear;
    }
}
