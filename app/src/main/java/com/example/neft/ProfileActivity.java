package com.example.neft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton arrowicon, iconredact, threepoints;
    private TextView postsbtn, aboutbtn, idtext, statustext, hobbiestext,hearttext,locationtext, name, male;
    private DatabaseReference mydatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        arrowicon=(ImageButton)findViewById(R.id.arrowicon);
        iconredact=(ImageButton)findViewById(R.id.iconredact);
        threepoints=(ImageButton)findViewById(R.id.pointsicon);

        postsbtn=(TextView)findViewById(R.id.postsbtn);
        aboutbtn=(TextView)findViewById(R.id.aboutbtn);
        idtext=(TextView)findViewById(R.id.idtext);
        statustext=(TextView)findViewById(R.id.statustext);
        hobbiestext=(TextView)findViewById(R.id.hobbiestext);
        hearttext=(TextView)findViewById(R.id.hearttext);
        locationtext=(TextView)findViewById(R.id.locationtext);
        name=(TextView) findViewById(R.id.title);
        male=(TextView) findViewById(R.id.phone);
        name.setText(Buffer.curuser.getLogin());

        mAuth = FirebaseAuth.getInstance();
        mydatabase= FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        Buffer.user = new User(currentUser.getUid(),currentUser.getPhoneNumber(),currentUser.getDisplayName());

        arrowicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        iconredact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redact();

            }
        });

        threepoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        postsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aboutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        idtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        statustext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        hobbiestext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        hearttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        locationtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
    }

    private void redact() {
    }
}
