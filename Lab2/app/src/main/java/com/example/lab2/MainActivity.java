package com.example.lab2;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Thread policy to allow networking
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void displayEmployees(View view) {
        // Get text elements
        EditText edit_file = findViewById(R.id.edit_file);
        TextView main_text = findViewById(R.id.text_main);

        // Clear main textbox
        main_text.setText("");

        // Try to parse file from URL
        String currentLine = "";
        int linesRead = 0;
        try {
            // Create Scanner to read file
            URL file_url = new URL(edit_file.getText().toString());
            Scanner file_scan = new Scanner(file_url.openStream());

            // Parse file into objects
            List<Employee> employeeList = new ArrayList<Employee>();
            while (file_scan.hasNextLine()) {
                linesRead++;
                String inptName = file_scan.nextLine();
                linesRead++;
                String inptId = file_scan.nextLine();
                linesRead++;
                currentLine = file_scan.nextLine();
                double inptSalary = Double.parseDouble(currentLine);
                linesRead++;
                String inptOffice = file_scan.nextLine();
                linesRead++;
                String inptExt = file_scan.nextLine();
                linesRead++;
                currentLine = file_scan.nextLine();
                int inptYears = Integer.parseInt(currentLine);
                employeeList.add(new Employee(inptName, inptId, inptOffice, inptExt, inptSalary, inptYears));
            }

            // Print list of employees to text view
            double salarySum = 0.0, yearsSum = 0.0;
            for (Employee e : employeeList) {
                main_text.append("\nName: " + e.getName() + " (" + e.getId() + ")\n" +
                        "Office: " + e.getOffice() + " (x" + e.getExtension() + ")\n" +
                        "Salary: " + e.getSalary() + " Years served: " + e.getYearsOfService() + "\n");
                yearsSum += e.getYearsOfService();
                salarySum += e.getSalary();
            }

            // Display average years of service and geometric mean of salaries
            main_text.append(String.format(
                    "Average years of service: %.1f\nGeometric mean of salaries: %.2f\n",
                    yearsSum / employeeList.size(),
                    Math.pow(salarySum, 1.0 / employeeList.size())));
        } catch (MalformedURLException e) {
            main_text.append("ERROR: Invalid URL form\n" + e.getMessage() + "\n");
        } catch (IOException e) {
            main_text.append("ERROR: Failed to retrieve file from URL\n" + e.getMessage() + "\n");
        } catch (NumberFormatException e) {
            main_text.append("ERROR: Invalid value on line " + linesRead + "\n" + e.getMessage() + "\n");
        } catch (Exception e) {
            main_text.append("ERROR: line " + linesRead + "\n" + e.getMessage() + "\n");
        }
    }

}