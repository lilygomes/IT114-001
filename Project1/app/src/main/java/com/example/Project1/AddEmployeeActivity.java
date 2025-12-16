package com.example.Project1;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Scanner;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddEmployeeActivity extends AppCompatActivity {
    @Inject
    ItemList the_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.AddEmployeeLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addFromFile(View view) {
        EditText inputFilename = findViewById(R.id.add_employee);

        // Get file from assets/
        AssetManager inFileManager = getAssets();
        Scanner inFileScan;
        int imported = 0;
        try {
            inFileScan= new Scanner(inFileManager.open(inputFilename.getText().toString()));
            while (inFileScan.hasNextLine()) {
                String name = inFileScan.nextLine();
                if (!inFileScan.hasNextLine())
                    break;
                String id = inFileScan.nextLine();
                if (!inFileScan.hasNextLine())
                    break;
                double salary = Double.parseDouble(inFileScan.nextLine());
                if (!inFileScan.hasNextLine())
                    break;
                String office = inFileScan.nextLine();
                if (!inFileScan.hasNextLine())
                    break;
                String extension = inFileScan.nextLine();
                if (!inFileScan.hasNextLine())
                    break;
                double performance = Double.parseDouble(inFileScan.nextLine());
                if (!inFileScan.hasNextLine())
                    break;
                int startYear = Integer.parseInt(inFileScan.nextLine());
                imported++;
                the_list.add(new Employee(name, id, salary, office, extension, performance, startYear));
            }
            Snackbar.make(findViewById(R.id.AddEmployeeLayout),
                    "Successfully imported " + imported + " employees from list.",
                    Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.println(Log.ERROR,
                    "AddEmployeeActivity",
                    "Error opening file: " + e.getMessage());
            Snackbar.make(findViewById(R.id.AddEmployeeLayout),
                    "Error opening file: " + e.getMessage(),
                    Snackbar.LENGTH_SHORT).show();
        }
    }
}