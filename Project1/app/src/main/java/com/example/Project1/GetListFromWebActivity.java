package com.example.Project1;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.Project1.databinding.ActivityGetListFromWebBinding;

import java.net.URL;
import java.util.Scanner;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GetListFromWebActivity extends AppCompatActivity {
    @Inject
    ItemList the_list;   // reference to singleton string list object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_list_from_web);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.GetListActivityLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    } // end onCreate

    public void loadFileFromURL(View view) {
        EditText inputFileURL = findViewById(R.id.input_ListURL);
        String currentLine = "0";
        try {
            // Create Scanner for remote file
            URL file_url = new URL(inputFileURL.getText().toString());
            Scanner inFileScan = new Scanner(file_url.openStream());
            // Read each item from list
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
                the_list.add(new Employee(name, id, salary, office, extension, performance, startYear));
            }
            Snackbar.make(findViewById(R.id.GetListActivityLayout),
                    "Successfully imported all employees from list.",
                    Snackbar.LENGTH_INDEFINITE).show();
        } catch (NumberFormatException e) {
            Snackbar.make(findViewById(R.id.GetListActivityLayout),
                    "Invalid value provided in list: " + currentLine,
                    Snackbar.LENGTH_INDEFINITE).show();
        } catch (NullPointerException e) {
            Log.println(Log.ERROR, "exceptionTrack", e.getMessage());
            Snackbar.make(findViewById(R.id.GetListActivityLayout),
                    "Could not retrieve file: " + e.getMessage(),
                    Snackbar.LENGTH_INDEFINITE).show();
        } catch (Exception e) {
            Log.println(Log.ERROR, "GetListFromWeb", "Unknown: " + currentLine);
            Snackbar.make(findViewById(R.id.GetListActivityLayout),
                    "Unknown error: " + e.getMessage(),
                    Snackbar.LENGTH_INDEFINITE).show();
        }
    }
}