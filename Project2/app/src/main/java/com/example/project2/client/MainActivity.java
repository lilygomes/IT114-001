package com.example.project2.client;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    public static final String HOSTNAME = "com.example.project2.client.HOSTNAME";
    public static final String PORT = "com.example.project2.client.PORT";

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

        // Allow for network access on the main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);

        // Setup toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    public void startSession(View view) {
        // Get connection info from inputs
        EditText inputHost = findViewById(R.id.input_address);
        EditText inputPort = findViewById(R.id.input_port);
        String hostname = inputHost.getText().toString();
        int port = Integer.parseInt(inputPort.getText().toString());

        // Create intent for session activity
        Intent intent = new Intent(this, SessionActivity.class);
        intent.putExtra(HOSTNAME, hostname);
        intent.putExtra(PORT, port);
        startActivity(intent);
    }
}