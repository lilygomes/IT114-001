package com.example.project1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import javax.inject.Inject;


public class RemoveEmployeeActivity extends AppCompatActivity {

    @Inject
    List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void removeEmployee(View view) {
        boolean successfullyRemoved = false;

        EditText inputID = findViewById(R.id.input_ID);
        String id = inputID.getText().toString();

        for (int i = 0; i < employeeList.size(); i++)
            if (employeeList.get(i).getId().equals(id)) {
                employeeList.remove(i);
                successfullyRemoved = true;
            }
        if (!successfullyRemoved)
            Snackbar.make(findViewById(R.id.RemoveEmployeeActivity),
                    "Could not remove employee " + id,
                    Snackbar.LENGTH_SHORT).show();
    }
}