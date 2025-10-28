package com.example.lab4;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.snackbar.Snackbar;

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

        // Attach toolbar to activity
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void displayImage(View view) {
        // Get reference to network resource
        EditText imageLinkInput = findViewById(R.id.image_link);
        SimpleDraweeView draweeView = findViewById(R.id.image_view);
        try {
            Uri uri = Uri.parse(imageLinkInput.getText().toString());
            draweeView.setImageURI(uri);
        } catch (NullPointerException e) {
            Snackbar.make(findViewById(R.id.main),
                    "Could not retrieve image: " + e.getMessage(),
                    Snackbar.LENGTH_INDEFINITE).show();
        } catch (Exception e) {
            Snackbar.make(findViewById(R.id.main),
                    "Unknown error: " + e.getMessage(),
                    Snackbar.LENGTH_INDEFINITE).show();
        }
    }
}