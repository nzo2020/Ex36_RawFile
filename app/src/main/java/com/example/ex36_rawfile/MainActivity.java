package com.example.ex36_rawfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * MainActivity reads and displays text from a raw resource file or user input.
 * It allows users to load a text file from the raw folder and display it,
 * as well as input their own text and view it in a TextView.
 * The activity also contains a menu option for viewing credits.
 *
 * @author Noa Zohar <nz2020@bs.amalnet.k12.il>
 * @version 1.0
 * @since 08/03/2025
 */
public class MainActivity extends AppCompatActivity {

    /**
     * TextView to display the content of the raw file or user input.
     */
    private TextView tvText;

    /**
     * EditText for user input.
     */
    private EditText etText;

    /**
     * The name of the raw file to be read.
     */
    private final String FILENAME = "rawtest.txt";

    /**
     * The resource ID of the raw file.
     */
    private int resourceId;

    /**
     * The file name extracted from {@link #FILENAME} without the file extension.
     */
    private String fileName;

    /**
     * Initializes the activity, sets up UI components, and determines the raw resource ID.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = findViewById(R.id.tvText);
        etText = findViewById(R.id.etText);

        // Extracts the raw resource ID based on the filename
        fileName = FILENAME.substring(0, FILENAME.length() - 4);
        resourceId = getResources().getIdentifier(fileName, "raw", getPackageName());
    }

    /**
     * Reads text from a raw file in the resources folder and displays it in tvText.
     *
     * @param view The button view that triggered the event.
     */
    public void raw_file_click(View view) {
        try {
            InputStream iS = this.getResources().openRawResource(resourceId);
            InputStreamReader iSR = new InputStreamReader(iS);
            BufferedReader bR = new BufferedReader(iSR);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();

            // Reads file content line by line
            while (line != null) {
                sB.append(line).append('\n');
                line = bR.readLine();
            }

            // Closes streams
            bR.close();
            iSR.close();
            iS.close();

            tvText.setText(sB.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the user-inputted text from etText in tvText.
     *
     * @param view The button view that triggered the event.
     */
    public void text_click(View view) {
        tvText.setText(etText.getText().toString());
    }

    /**
     * Inflates the menu and adds the option for credits.
     *
     * @param menu The menu to inflate.
     * @return True if the menu was successfully inflated.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.creditsmain, menu);
        return true;
    }

    /**
     * Handles menu item selections, navigating to the credits screen if selected.
     *
     * @param item The selected menu item.
     * @return True if the selection was handled.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuCredits) {
            Intent intent = new Intent(this, mainCredits.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
