package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "ButtonPressPrefs";
    private static final String BUTTON_PRESS_COUNT_KEY = "buttonPressCount";

    private TextView displayText;
    private int buttonPressCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);

        displayText = findViewById(R.id.display_text);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        buttonPressCount = sharedPreferences.getInt(BUTTON_PRESS_COUNT_KEY, 0);

        Toast.makeText(this, "Total button presses: " + buttonPressCount, Toast.LENGTH_LONG).show();

        setupButton(R.id.button_top_left, "Top Left");
        setupButton(R.id.button_top_right, "Top Right");
        setupButton(R.id.button_center, "Center");
        setupButton(R.id.button_bottom_left, "Bottom Left");
        setupButton(R.id.button_bottom_right, "Bottom Right");

        Button navigateButton = findViewById(R.id.navigate_button);
        navigateButton.setOnClickListener(v -> {
            Intent intent = new Intent(PracticalTest01Var05MainActivity.this, PracticalTest01VarSecondaryActivity.class);
            startActivity(intent);
        });
    }

    private void setupButton(int buttonId, String buttonText) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            String currentText = displayText.getText().toString();
            displayText.setText(currentText + buttonText + ", ");

            buttonPressCount++;
            saveButtonPressCount();
        });
    }

    private void saveButtonPressCount() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BUTTON_PRESS_COUNT_KEY, buttonPressCount);
        editor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUTTON_PRESS_COUNT_KEY, buttonPressCount);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            buttonPressCount = savedInstanceState.getInt(BUTTON_PRESS_COUNT_KEY);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveButtonPressCount();
    }
}
