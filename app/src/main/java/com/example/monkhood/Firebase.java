package com.example.monkhood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase extends AppCompatActivity {

    private TextView nameEditText, emailEditText, phoneNumberEditText;
    private String name, email, phoneNumber;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        // Initialize UI elements
        nameEditText = findViewById(R.id.textView2);
        emailEditText = findViewById(R.id.textView3);
        phoneNumberEditText = findViewById(R.id.textView4);

        Intent intent = this.getIntent();
        this.name = intent.getStringExtra("name");
        this.email = intent.getStringExtra("email");
        this.phoneNumber = intent.getStringExtra("phone number");
        intent.getStringExtra("image");

        nameEditText.setText(name);
        emailEditText.setText(email);
        phoneNumberEditText.setText(phoneNumber);
    }

    public void deleteData(View view) {
            String userId = usersRef.push().getKey();
            DatabaseReference userRef = usersRef.child(userId);
            userRef.removeValue();
    }
}
