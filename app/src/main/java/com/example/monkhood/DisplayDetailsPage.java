package com.example.monkhood;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

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
    private FirebaseDatabase db;
    private DatabaseReference usersCollection;
    private DatabaseReference usersRef;
    String name, phoneNumber, email;

    private static final String PREFS_NAME = "UserPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details_page);

        // Get a reference to the "users" node in the database
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Initialize UI elements
        nameEditText = findViewById(R.id.textView6);
        emailEditText = findViewById(R.id.textView7);
        phoneNumberEditText = findViewById(R.id.textView8);

        // Get a reference to the Firestore database
        db = FirebaseDatabase.getInstance();

        // Get a reference to the "users" collection in Firestore
        usersCollection = db.getReference("users");


        // Add a ValueEventListener to read data from the "users" node
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve user details from the DataSnapshot
                    name = userSnapshot.child("name").getValue(String.class);
                    email = userSnapshot.child("email").getValue(String.class);
                    phoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);

                    // Create a User object using the retrieved data
                    User user = new User(name, email, phoneNumber);

                    nameEditText.setText(name);
                    phoneNumberEditText.setText(phoneNumber);
                    emailEditText.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
            });

        nameEditText.setText(name);
        phoneNumberEditText.setText(phoneNumber);
        emailEditText.setText(email);
        }
    }