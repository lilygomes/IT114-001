package com.example.Project1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GetEmployeeInfoActivity extends AppCompatActivity {
    @Inject
    ItemList the_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_employee_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.GetEmployeeInfoLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void getInfo(View view) {
        EditText inputID = findViewById(R.id.input_GetInfoID);
        TextView tv = findViewById(R.id.text_GetEmployeeInfo);
        boolean found = false;
        for (Employee e: the_list)
            if (e.getId().equals(inputID.getText().toString()))
                tv.append(String.format(
                        "Name: %s\nSalary: $%.2f\nOffice: %s\nExtension: %s\nPerformance Rating: %.2f\n%sYears of Service: %d\n",
                        e.getName(),
                        e.getSalary(),
                        e.getOffice(),
                        e.getExtension(),
                        e.getPerformance(),
                        (e.getBonus() > 0) ? "Bonus: $" + e.getBonus() + "\n" : "",
                        e.getYearsService()
                        ));
        if (!found)
            Snackbar.make(findViewById(R.id.GetEmployeeInfoLayout),
                    "Could not find employee with given ID",
                    Snackbar.LENGTH_SHORT).show();
    }
}