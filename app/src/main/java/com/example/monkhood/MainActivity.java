package com.example.monkhood;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button uploadProfilePic;
    Button submit;

    EditText name1;
    EditText emailID;
    EditText number;
    DatePicker DOB;
    ImageView displayPicture;
    private Uri imageUri;
    private DatabaseReference usersRef;

    private static final int REQUEST_IMAGE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name1 = findViewById(R.id.editTextTextPersonName);
        emailID = findViewById(R.id.editTextTextEmailAddress);
        number = findViewById(R.id.editTextNumber);
        displayPicture = findViewById(R.id.imageView);
        DOB = findViewById(R.id.datePicker);

    }

    public void onOpenGalleryButtonClick(View view) {
        openGallery();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
    }

    public void makeVisible(View view) {
        view.setVisibility(View.VISIBLE);
        view.animate();
    }

    public void submitDetails(View view) {

        // Save user data to SharedPreferences
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        try {
            editor.putString("name",name1.getText().toString());
            editor.putString("phoneNumber", number.getText().toString());
            editor.putString("email", emailID.getText().toString());
            editor.putString("dob", DOB.toString());

            Log.d("SharedPreferences", "Data saved: name=" + name1.getText().toString());
            editor.apply();
            Toast.makeText(MainActivity.this,"data saved in sharedPreferences",Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Intent i = new Intent(MainActivity.this,DisplayDetailsPage.class);
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void firebase(View view){
        Intent intent = new Intent(MainActivity.this, Firebase.class);

        // Create a new User object
        User user = new User(name1.getText().toString(), emailID.getText().toString(), number.getText().toString());
        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Generate a unique key for the user
        String userId = usersRef.push().getKey();

        // Save the user details to Firebase Realtime Database
        usersRef.child(userId).setValue(user);


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
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Start the Firebase activity
        startActivity(intent);
    }

            @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                // Use the imageUri to access the selected image
                // Display the selected image in the ImageView
                displayPicture.setVisibility(View.VISIBLE);
                displayPicture.setImageURI(imageUri);
            }
        }
    }


}
