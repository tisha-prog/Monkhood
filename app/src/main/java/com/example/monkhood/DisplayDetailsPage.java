package com.example.monkhood;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayDetailsPage extends AppCompatActivity {
    private TextView nameEditText, emailEditText, phoneNumberEditText;
    String name, phoneNumber, email;

    private static final String PREFS_NAME = "UserPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details_page);

        // Initialize UI elements
        nameEditText = findViewById(R.id.textView6);
        emailEditText = findViewById(R.id.textView7);
        phoneNumberEditText = findViewById(R.id.textView8);

        // default SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve from SharedPreferences
        String userName = sharedPreferences.getString("name", "Default if key not found");
        String email = sharedPreferences.getString("email", "Default if key not found");
        String number = sharedPreferences.getString("phoneNumber", "Default if key not found");

        nameEditText.setText(userName);
        emailEditText.setText(email);
        phoneNumberEditText.setText(number);

        Toast.makeText(DisplayDetailsPage.this,"data retrieved sharedPreferences",Toast.LENGTH_LONG).show();
    }

    public void deleteAll(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("name");
        editor.remove("email");
        editor.remove("phoneNumber");
        editor.apply();

        String userName = sharedPreferences.getString("name", "data deleted");
        String email = sharedPreferences.getString("email", "data deleted");
        String number = sharedPreferences.getString("phoneNumber", "data deleted");

        nameEditText.setText(userName);
        emailEditText.setText(email);
        phoneNumberEditText.setText(number);

    }
}