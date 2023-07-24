package com.example.monkhood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        // Get a reference to the "users" node in the database
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        // Add a ValueEventListener to read data from the "users" node
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Retrieve user details from the DataSnapshot
                    String name = userSnapshot.child("name").getValue(String.class);
                    String email = userSnapshot.child("email").getValue(String.class);
                    String phoneNumber = userSnapshot.child("phoneNumber").getValue(String.class);

                    // Create a User object using the retrieved data
                    User user = new User(name, email, phoneNumber);

                    // Do something with the user object, e.g., display it in the UI
                    nameEditText.setText(name);
                    emailEditText.setText(email);
                    phoneNumberEditText.setText(phoneNumber);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void deleteData(View view) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Firebase.this,"All data successfully deleted from Firebase Realtime Database",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Firebase.this,"Unable to delete data at the moment",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
