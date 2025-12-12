package com.example.project2.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SessionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Objects for server connection
    private Socket socket = null;
    private PrintWriter out = null;
    private Scanner in = null;
    private String operation;

    // Output field
    private TextView outputGenome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_session);
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

        // Define output field
        outputGenome = findViewById(R.id.output_genome);

        // Setup operation option UI element
        Spinner selectOperation = findViewById(R.id.select_operation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.select_options,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        selectOperation.setAdapter(adapter);
        selectOperation.setOnItemSelectedListener(this);

        // Get intent from MainActivity with connection details
        Intent intent = getIntent();
        String hostname = intent.getStringExtra(MainActivity.HOSTNAME);
        int port = intent.getIntExtra(MainActivity.PORT, 4000);

        // Connect to provided server
        try {
            socket = new Socket(hostname, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new Scanner(new InputStreamReader(socket.getInputStream()));
            outputGenome.setText(R.string.msg_connected);
        } catch (UnknownHostException e) {
            outputGenome.append(
                    "Could not connect to host \"" + hostname + ":" + port + "\": unknown host\n");
            socket = null;
        } catch (IOException e) {
            outputGenome.append(
                    "Could not connect to \"" + hostname + ":" + port + "\": " + e.getMessage() + "\n");
            socket = null;
        } catch (Exception e) {
            outputGenome.append("Failed to connect!\n" + e.getMessage() + "\n");
        }
    }

    public void go(View view) {
        EditText inputGenome = findViewById(R.id.input_genome);
        EditText inputSequence = findViewById(R.id.input_sequence);

        // If socket failed to initialise, show error:
        if (socket == null)
            Toast.makeText(this, "No connection to server!", Toast.LENGTH_SHORT).show();
        // Or if no operation was selected:
        else if (operation == null)
            Toast.makeText(this, "Select an operation first!", Toast.LENGTH_SHORT).show();
        // Otherwise, send requests to server
        else {
            out.println(operation);
            out.println(inputGenome.getText().toString());
            out.println(inputSequence.getText().toString());

            // Retrieve response from server
            outputGenome.append(in.nextLine() + "\n");
            // Align operation returns two lines
            if (operation.equals("align"))
                outputGenome.append(in.nextLine() + "\n");
        }
    }

    public void disconnect(View view) {
        try {
            // Close all streams and sockets
            out.close();
            in.close();
            socket.close();
            socket = null;
            // Close activity on successful disconnect
            finish();
        } catch (Exception e) {
            outputGenome.append("Failed to disconnect!\n" + e.getMessage());
        }
    }

    // Handlers for operation Spinner interaction
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                operation = "occur";
                break;
            case 1:
                operation = "align";
                break;
            default:
                operation = null;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        operation = null;
    }
}