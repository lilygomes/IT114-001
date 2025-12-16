package com.example.a1141;

import static java.lang.Math.tan;

import android.content.res.AssetManager;
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

import java.io.File;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void processPress(View view)
    {
        long startTimestamp = System.currentTimeMillis();

        TextView tv;
        EditText et1;
        String infilename;

        tv = (TextView) findViewById(R.id.text_main);

        // Clear existing output in tv

        // get the values from the EditTexts and
        // display them

        et1 = (EditText) findViewById(R.id.edit_infile);

        infilename = et1.getText().toString();

        tv.append("Input file's name is: " + infilename + "\n");

        // Array full of doubles to tan
        double[] my_angles = new double[300];

        // Get file from assets/
        AssetManager inFileManager = getAssets();
        Scanner inFileScanner;
        try {
            inFileScanner = new Scanner(inFileManager.open(infilename));
            // Count how many numbers in file
            int anglesIndex = 0;
            tv.append("Read ");
            while (inFileScanner.hasNext()) {
                // Doesn't work when trying to use nextDouble() - huh?
                String nextToken = inFileScanner.next();
                tv.append(nextToken + " ");
                my_angles[anglesIndex++] = Double.parseDouble(nextToken);
            }
            tv.append("\n");
            double[] myTannedAngles = tan_it(my_angles, anglesIndex);
            for (double d : myTannedAngles)
                tv.append(d + "\n");
        } catch (Exception e) {
            tv.append("Opening file " + infilename + " failed with exception:\n" + e.getMessage());
        }

        tv.append("\nFinished in " + (System.currentTimeMillis() - startTimestamp) + " ms.\n");
    } // end processPress

    public double[] tan_it (double[] a, int num_vals) {
        double[] tanned = new double[num_vals];
        for (int i = 0; i < num_vals; i++)
            tanned[i] = tan(a[i]);
        return tanned;
    }
}