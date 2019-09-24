package com.example.neft;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfileAboutActivity extends AppCompatActivity {

    private ImageButton arrowicon, iconinvite, threepoints;
    private TextView postsbtn, aboutbtn, idtext, statustext, hobbiestext,hearttext,locationtext, name, male;
    private DatabaseReference mydatabase;
    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_about);

        arrowicon=(ImageButton)findViewById(R.id.arrowicon);
        iconinvite=(ImageButton)findViewById(R.id.iconredact);
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







        arrowicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        iconinvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invitefriend();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void invitefriend() {
        Invite invite = new Invite(Buffer.user, Buffer.curuser);
        Map<String, Object> neweventValues = invite.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/invites/" + Buffer.curuser.getUseruid()+"/"+Buffer.user.getUseruid(), neweventValues);

        mydatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
                Toast.makeText(ProfileAboutActivity.this, "Приглашение в друзья отправленно!",
                        Toast.LENGTH_SHORT).show();



            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(ProfileAboutActivity.this, "ошибка.Приглашение не отправленно",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
