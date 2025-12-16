package com.example.project1;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    // References to main activity components
    private TextView mainText;

    // Employee list
    @Inject
    ItemList employeeList;
    private final double LARGE_SALARY_THRESHOLD = 100000.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Create new EmployeeList
        employeeList = new ItemList();
        // Enable networking in main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

        // Setup toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Define reference to main text box
        mainText = findViewById(R.id.text_main);
    }

    // Add menu items to menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showList(MenuItem menuItem) {
        // Check if there are any employees to show
        if (employeeList.isEmpty())
            mainText.setText("No employees in current list.");
        // If there are, clear text and print each
        else {
            mainText.setText("");
            for (Employee e : employeeList) {
                mainText.append(e.getName() + ", " + e.getId() + ", " + e.getStartYear() + "\n");
            }
        }
    }

    public void getListFromWeb(MenuItem menuItem) {
        // TODO make new activity
        startActivity(new Intent(this, GetListFromWeb.class));
    }

    public void addEmployeeFromFile(MenuItem menuItem) {
        // TODO make new activity
        startActivity(new Intent(this, AddEmployee.class));
    }

    public void showEmployee(MenuItem menuItem) {
        // TODO make new activity
    }

    public void removeEmployee(MenuItem menuItem) {
        startActivity(new Intent(this, RemoveEmployeeActivity.class));
    }

    public void showAvgSalary(MenuItem menuItem) {
        // Check if there are any employees to show
        if (employeeList.isEmpty())
            mainText.setText("No employees in current list.");
        // If there are, sum salaries of each
        else {
            double salarySum = 0.0;
            for (Employee e : employeeList)
                salarySum += e.getSalary();
            // and print geometric mean
            mainText.setText("");
            mainText.append(String.format(
                    "Geometric average of salaries: %.2f\n",
                    Math.pow(salarySum, 1.0 / employeeList.size())
            ));
        }
    }

    public void showTopSalaries(MenuItem menuItem) {
        // Check if there are any employees to show
        if (employeeList.isEmpty())
            mainText.setText("No employees in current list.");
        // If there are, print big salaries
        else {
            mainText.setText(String.format("High paid ($%.2f or more) employees: \n", LARGE_SALARY_THRESHOLD));
            for (Employee e : employeeList) {
                if (e.getSalary() >= LARGE_SALARY_THRESHOLD)
                    mainText.append(String.format("%s (%s): $%.2f", e.getName(), e.getId(), e.getSalary()));
            }
        }
    }
}