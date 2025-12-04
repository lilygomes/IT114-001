package com.example.lab6;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void calculate(View view) {
        EditText inputPrincipal, inputYears, inputPercent;
        TextView output;
        double original_principal, percent, result;
        int years;

        inputPrincipal = findViewById(R.id.input_principal);
        inputYears = findViewById(R.id.input_years);
        inputPercent = findViewById(R.id.input_interest);
        output = findViewById(R.id.output);

        original_principal = Double.parseDouble(inputPrincipal.getText().toString());
        percent = Double.parseDouble(inputPercent.getText().toString()) * 0.01;
        years = Integer.parseInt(inputYears.getText().toString());

        result = principal(years, original_principal, percent);
        output.setText(
                String.format("Principal after %d years is $%.2f\nAccrued interest: $%.2f",
                        years, result, (result - original_principal)));
    }

    public double principal(int y, double p_orig, double i_rate) {
        return (y == 0) ? p_orig : (principal(y - 1, p_orig, i_rate) * (1.0 + i_rate));
    }
}