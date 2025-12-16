package com.example.Project1;

import android.os.Bundle;

import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

/////////////////////////////////////////////////////
//
// This app manipulates a list of strings.  Options
// are chosen from the main app bar.
//
// Some options just display results in the main
// TextView.  Others invoke new activities to carry
// out their tasks.
//
// Author: M. Halper
//
/////////////////////////////////////////////////////

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ItemList the_list;   // reference to singleton string list object

    private TextView tv;
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

        // Enable networking in main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // set the reference to the "main" TextView object so
        // we do not have to retrieve it in every method below

        tv = (TextView) findViewById(R.id.text_main);

    } // end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void onOption1(MenuItem i)
    {
        // show the list in order

        int j;

        tv.setText("");

        // display the list items

        for(j = 0; j < the_list.size(); j++)

            tv.append(the_list.get(j).getName() + '\n');

        tv.append("\n");
    } // end onOption1

    public void onOption2(MenuItem i)
    {
        Log.println(Log.INFO, "MainActivity", "opt 2");
        startActivity(new Intent(this, GetListFromWebActivity.class));
    } // end onOption2

    public void onOption3(MenuItem i)
    {
        startActivity(new Intent(this, AddEmployeeActivity.class));
    } // end onOption3

    public void onOption4(MenuItem i)
    {

        startActivity(new Intent(this, GetEmployeeInfoActivity.class));

    } // end onOption4

    public void onOption5(MenuItem i)
    {

        // YYY: Start the activity to remove an item from the list
        startActivity(new Intent(this, RemoveItemActivity.class));

    } // end onOption5

    public void onOption6(MenuItem i)
    {
        // Check if there are any employees to show
        if (the_list.isEmpty())
            tv.setText("No employees in current list.");
            // If there are, sum salaries of each
        else {
            double salarySum = 0.0;
            for (Employee e : the_list)
                salarySum += e.getSalary();
            // and print geometric mean
            tv.append(String.format(
                    "Geometric average of salaries: %.2f\n",
                    Math.pow(salarySum, 1.0 / the_list.size())
            ));
            tv.append("\n");
        }
    } // end onOption6

    public void onOption7(MenuItem i) {
        // Check if there are any employees to show
        if (the_list.isEmpty())
            tv.setText("No employees in current list.");
            // If there are, print big salaries
        else {
            tv.setText(String.format("High paid ($%.2f or more) employees: \n", LARGE_SALARY_THRESHOLD));
            for (Employee e : the_list) {
                if (e.getSalary() >= LARGE_SALARY_THRESHOLD)
                    tv.append(String.format("%s (%s): $%.2f\n", e.getName(), e.getId(), e.getSalary()));
            }
            tv.append("\n");
        }
    }

} // end MainActivity
