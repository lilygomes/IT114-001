package com.example.Project1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ConcurrentModificationException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RemoveItemActivity extends AppCompatActivity {
    // Inject list from caller activity
    @Inject
    ItemList the_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.RemoveItemLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void removeItem(View view) {
        EditText inputField = findViewById(R.id.remove_item);
        boolean removed = false;
        try {
            // Check for invalid input, throw exception
            String targetId = inputField.getText().toString();
            // Try to remove item at index from list
            for (Employee e: the_list) {
                if (e.getId().equals(targetId)) {
                    removed = true;
                    the_list.remove(e);
                }
            }
        } catch (NumberFormatException e) {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.RemoveItemLayout),
                    "Invalid input for item number",
                    Snackbar.LENGTH_SHORT).show();
        } catch (IndexOutOfBoundsException e) {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.RemoveItemLayout),
                    "Item # does not exist",
                    Snackbar.LENGTH_SHORT).show();
        } catch (ConcurrentModificationException e) {
            Log.println(Log.ERROR, "RemoveItemActivity", "Unknown exception: " + e.getClass());
        } catch (Exception e) {
            hideKeyboard();
            Log.println(Log.ERROR, "RemoveItemActivity", "Unknown exception: " + e.getClass());
            Snackbar.make(findViewById(R.id.RemoveItemLayout),
                    "ERROR: " + e.getMessage(),
                    Snackbar.LENGTH_SHORT).show();
        }
        if (!removed) {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.RemoveItemLayout),
                    "No employee found with given ID",
                    Snackbar.LENGTH_SHORT).show();
        } else {
            hideKeyboard();
            Snackbar.make(findViewById(R.id.RemoveItemLayout),
                    "Successfully removed employee.",
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard()
    {
        // This method dismisses the soft keyboard.
        // Code derived from developer.android.com and
        // StackOverflow

        Context context = getCurrentFocus().getContext();

        InputMethodManager inputMethodManager =
                (InputMethodManager)
                        context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

    } // end hideKeyboard
}