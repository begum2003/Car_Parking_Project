package com.example.park;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class vehicle_info extends AppCompatActivity {

    EditText veh_name, veh_model;
    Button book;

    FirebaseUser user;
    FirebaseAuth auth;
    DatabaseReference reference;

    DatabaseReference databaseReference; // No need to initialize with URL here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        veh_name = findViewById(R.id.editTextText);
        veh_model = findViewById(R.id.editTextText2);
        book = findViewById(R.id.addbtn2);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("User Data");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Vehicles");

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = veh_name.getText().toString();
                String e = veh_model.getText().toString();

                // Check if vehicle name and model are empty
                if (n.isEmpty() || e.isEmpty()) {
                    // Set error messages for empty fields
                    if (n.isEmpty()) {
                        veh_name.setError("Please enter your vehicle name");
                    }
                    if (e.isEmpty()) {
                        veh_model.setError("Please enter vehicle model");
                    }
                } else {
                    // Check if vehicle name exists in the database
                    databaseReference.child("vehicle").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (user != null) {
                                String userId = user.getUid();
                                // Store vehicle details under the user's node
                                DatabaseReference userVehicleRef = databaseReference.child(userId);
                                userVehicleRef.child("veh_name").setValue(n);
                                userVehicleRef.child("veh_model").setValue(e);

                                // Navigate to the next activity
                                Intent i = new Intent(getApplicationContext(), booking_details.class);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(vehicle_info.this, "User not logged in", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle cancelled event
                        }
                    });
                }
            }
        });

    }
}
